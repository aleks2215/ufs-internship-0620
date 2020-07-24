package ru.philit.ufs.web.provider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.AsfsCache;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.OperationCache;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.oper.OperationTaskStatus;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с операциями.
 */
@Service
public class OperationProvider {

  private final RepresentativeProvider representativeProvider;
  private final OperationCache operationCache;
  private final MockCache mockCache;
  private final AsfsCache asfsCache;

  /**
   * Конструктор бина.
   */
  @Autowired
  public OperationProvider(RepresentativeProvider representativeProvider, OperationCache cache,
      MockCache mockCache, AsfsCache asfsCache) {
    this.representativeProvider = representativeProvider;
    this.operationCache = cache;
    this.mockCache = mockCache;
    this.asfsCache = asfsCache;
  }

  /**
   * Добавление новой активной задачи.
   *
   * @param workplaceId уникальный номер УРМ/кассы
   * @param depositTask данные для взноса на корпоративную карту
   * @param clientInfo  информация о клиенте
   * @return пакет с новой активной задачей
   */
  public OperationPackage addActiveDepositTask(String workplaceId,
      OperationTaskCardDeposit depositTask, ClientInfo clientInfo) {
    return addDepositTask(workplaceId, depositTask, OperationTaskStatus.ACTIVE, clientInfo);
  }

  /**
   * Добавление новой перенаправленной задачи.
   *
   * @param workplaceId уникальный номер УРМ/кассы
   * @param depositTask данные для взноса на корпоративную карту
   * @param clientInfo  информация о клиенте
   * @return пакет с новой перенаправленной задачей
   */
  public OperationPackage addForwardedDepositTask(String workplaceId,
      OperationTaskCardDeposit depositTask, ClientInfo clientInfo) {
    return addDepositTask(workplaceId, depositTask, OperationTaskStatus.FORWARDED, clientInfo);
  }

  private OperationPackage addDepositTask(String workplaceId, OperationTaskCardDeposit depositTask,
      OperationTaskStatus taskStatus, ClientInfo clientInfo) {
    if (StringUtils.isEmpty(workplaceId)) {
      throw new InvalidDataException("Отсутствует запрашиваемый номер УРМ/кассы");
    }
    if (depositTask == null) {
      throw new InvalidDataException("Отсутствуют данные для взноса на корпоративную карту");
    }
    OperationPackageRequest packageRequest = new OperationPackageRequest();

    packageRequest.setWorkPlaceUid(workplaceId);
    packageRequest.setInn(depositTask.getInn());
    packageRequest.setUserLogin(clientInfo.getUser().getLogin());

    /*OperationPackage opPackage = cache.getPackage(packageRequest, clientInfo);
    if ((opPackage == null) || (opPackage.getId() == null)) {
      opPackage = cache.createPackage(packageRequest, clientInfo);
    }*/
    OperationPackage opPackage = operationCache.createPackage(packageRequest, clientInfo);

    depositTask.setStatus(taskStatus);

    OperationPackage addTasksPackage = new OperationPackage();
    addTasksPackage.setId(opPackage.getId());
    addTasksPackage.getToCardDeposits().add(depositTask);

    return operationCache.addTasksInPackage(addTasksPackage, clientInfo);
  }

  /**
   * Получение списка операция в статусе FORWARDED.
   *
   * @param clientInfo информация о клиенте
   * @return список операций
   */
  public List<OperationTaskCardDeposit> getForwardedDepositTasks(ClientInfo clientInfo) {
    OperationTasksRequest tasksRequest = new OperationTasksRequest();
    tasksRequest.setTaskStatusGlobal(OperationTaskStatus.FORWARDED);

    List<OperationPackage> operationPackages = operationCache.getTasksInPackages(tasksRequest,
        clientInfo);

    List<OperationTaskCardDeposit> resultList = new ArrayList<>();
    for (OperationPackage opPackage : operationPackages) {
      for (OperationTask task : opPackage.getToCardDeposits()) {
        if (task instanceof OperationTaskCardDeposit) {
          OperationTaskCardDeposit taskCardDeposit = (OperationTaskCardDeposit) task;
          Representative representative = representativeProvider.getRepresentativeById(
              taskCardDeposit.getRepresentativeId(), clientInfo);
          if (representative != null) {
            StringBuilder fullName = new StringBuilder();
            if (!StringUtils.isEmpty(representative.getLastName())) {
              fullName.append(representative.getLastName()).append(" ");
            }
            if (!StringUtils.isEmpty(representative.getFirstName())) {
              fullName.append(representative.getFirstName()).append(" ");
            }
            if (!StringUtils.isEmpty(representative.getPatronymic())) {
              fullName.append(representative.getPatronymic()).append(" ");
            }
            taskCardDeposit.setRepFio(fullName.toString().trim());
          }
          taskCardDeposit.setPackageId(opPackage.getId());
          resultList.add(taskCardDeposit);
        }
      }
    }

    return resultList;
  }

  /**
   * Подтверждение операции.
   *
   * @param packageId         идентификатор пакета задач
   * @param taskId            идентификатор задачи
   * @param workplaceId       номер УРМ/кассы
   * @param operationTypeCode код типа операции
   * @param clientInfo        информация о клиенте
   * @return информация об операции
   */
  public Operation confirmOperation(Long packageId, Long taskId, String workplaceId,
      String operationTypeCode, ClientInfo clientInfo) {
    if (packageId == null) {
      throw new InvalidDataException("Отсутствует запрашиваемый идентификатор пакета задач");
    }
    if (taskId == null) {
      throw new InvalidDataException("Отсутствует запрашиваемый идентификатор задачи");
    }
    if (StringUtils.isEmpty(workplaceId)) {
      throw new InvalidDataException("Отсутствует запрашиваемый номер УРМ/кассы");
    }
    if (StringUtils.isEmpty(operationTypeCode)) {
      throw new InvalidDataException("Отсутствует запрашиваемый код типа операции");
    }

    OperationTasksRequest getTasksRequest = new OperationTasksRequest();
    getTasksRequest.setPackageId(packageId);

    OperationPackage opPackage = operationCache.getTasksInPackage(getTasksRequest, clientInfo);
    if (opPackage == null) {
      throw new InvalidDataException("Запрашиваемый пакет задач не найден");
    }

    CashOrder cashOrderFromCardDeposit = new CashOrder();
    List<OperationTask> depositTasks = opPackage.getToCardDeposits();
    for (OperationTask operationTask : depositTasks) {
      if (operationTask.getId().equals(taskId)) {
        operationTask.setStatus(OperationTaskStatus.COMPLETED);

        if (operationTask instanceof OperationTaskCardDeposit) {
          OperationTaskCardDeposit taskCardDeposit = (OperationTaskCardDeposit) operationTask;
          cashOrderFromCardDeposit = createCashOrderFromCardDeposit(taskCardDeposit, clientInfo);
        }

        break;
      }
    }

    OperationPackage updateTasksPackage = new OperationPackage();
    updateTasksPackage.setId(packageId);
    updateTasksPackage.setToCardDeposits(depositTasks);

    operationCache.updateTasksInPackage(updateTasksPackage, clientInfo);

    CashOrder cashOrder = asfsCache.createCashOrder(cashOrderFromCardDeposit, clientInfo);
    cashOrder = asfsCache.updateStatusCashOrder(cashOrder, clientInfo);
    asfsCache.addConfirmedCashOrder(cashOrder);

    Operation operation = mockCache.createOperation(workplaceId, operationTypeCode);
    operation.setCashOrderId(cashOrder);
    operation = mockCache.commitOperation(operation);

    operationCache.addOperation(taskId, operation);

    return operation;
  }

  /**
   * Отмена операции.
   *
   * @param packageId         идентификатор пакета задач
   * @param taskId            идентификатор задачи
   * @param workplaceId       номер УРМ/кассы
   * @param operationTypeCode код типа операции
   * @return информация об операции
   */
  public Operation cancelOperation(Long packageId, Long taskId, String workplaceId,
      String operationTypeCode, ClientInfo clientInfo) {
    if (packageId == null) {
      throw new InvalidDataException("Отсутствует запрашиваемый идентификатор пакета задач");
    }
    if (taskId == null) {
      throw new InvalidDataException("Отсутствует запрашиваемый идентификатор задачи");
    }
    if (StringUtils.isEmpty(workplaceId)) {
      throw new InvalidDataException("Отсутствует запрашиваемый номер УРМ/кассы");
    }
    if (StringUtils.isEmpty(operationTypeCode)) {
      throw new InvalidDataException("Отсутствует запрашиваемый код типа операции");
    }

    OperationTasksRequest getTasksRequest = new OperationTasksRequest();
    getTasksRequest.setPackageId(packageId);
    OperationPackage opPackage = operationCache.getTasksInPackage(getTasksRequest, clientInfo);
    if (opPackage == null) {
      throw new InvalidDataException("Запрашиваемый пакет задач не найден");
    }

    List<OperationTask> depositTasks = opPackage.getToCardDeposits();
    for (OperationTask operationTask : depositTasks) {
      if (operationTask.getId().equals(taskId)) {
        operationTask.setStatus(OperationTaskStatus.DECLINED);
        break;
      }
    }
    OperationPackage updateTasksPackage = new OperationPackage();
    updateTasksPackage.setId(packageId);
    updateTasksPackage.setToCardDeposits(depositTasks);
    operationCache.updateTasksInPackage(updateTasksPackage, clientInfo);

    Operation operation = mockCache.createOperation(workplaceId, operationTypeCode);
    operation = mockCache.cancelOperation(operation);

    operationCache.addOperation(taskId, operation);

    return operation;
  }

  private CashOrder createCashOrderFromCardDeposit(OperationTaskCardDeposit taskCardDeposit,
      ClientInfo clientInfo) {
    CashOrder cashOrder = new CashOrder();

    cashOrder.setCashOrderId(String.valueOf((int) (Math.random() * 100000)));
    cashOrder.setOperationType(OperationTypeCode.TO_CARD_DEPOSIT);
    cashOrder.setCashOrderINum("50");
    cashOrder.setAccountId(taskCardDeposit.getAccountId());
    cashOrder.setAmount(taskCardDeposit.getAmount());
    cashOrder.setAmountInWords("Сто тысяч");
    cashOrder.setCurrencyType("RUB");
    cashOrder.setCashOrderStatus(CashOrderStatus.CREATED);
    cashOrder.setWorkPlaceUId("12");
    cashOrder.setRepId(taskCardDeposit.getRepresentativeId());
    cashOrder.setRepFio(taskCardDeposit.getRepFio());
    cashOrder.setAddress("Ул. Титова");
    cashOrder.setDateOfBirth(new GregorianCalendar(2020, Calendar.JUNE, 30)
        .getTime());

    cashOrder.setIdentityDocuments(new ArrayList<IdentityDocument>());
    IdentityDocument identityDocument = new IdentityDocument();
    identityDocument.setType(IdentityDocumentType.PASSPORT);
    identityDocument.setSeries("44 33");
    identityDocument.setNumber("444666");
    identityDocument.setIssuedDate(
        new GregorianCalendar(1980, Calendar.JANUARY, 30).getTime());
    identityDocument.setIssuedBy("МВД");
    cashOrder.getIdentityDocuments().add(identityDocument);

    cashOrder.setPlaceOfBirth("Москва");
    cashOrder.setResident(true);
    cashOrder.setInn(taskCardDeposit.getInn());
    cashOrder.setCashSymbols(taskCardDeposit.getCashSymbols());

    cashOrder.setSubbranch(new Subbranch());
    cashOrder.getSubbranch().setSubbranchCode("1385930102");
    cashOrder.getSubbranch().setVspCode("0102");
    cashOrder.getSubbranch().setOsbCode(null);
    cashOrder.getSubbranch().setGosbCode("8593");
    cashOrder.getSubbranch().setTbCode("13");

    cashOrder.setComment("Коммент");
    cashOrder.setAccount20202Num("20202897357400146292");
    cashOrder.setUserLogin(clientInfo.getUser().getLogin());

    return cashOrder;
  }
}
