package ru.philit.ufs.web.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.AnnouncementCache;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.OperationCache;
import ru.philit.ufs.model.cache.UserCache;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskStatus;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с данными для отчётов.
 */
@Service
public class ReportProvider {

  private final OperationCache operationCache;
  private final AnnouncementCache announcementCache;
  private final UserCache userCache;
  private final MockCache mockCache;

  /**
   * Конструктор бина.
   */
  @Autowired
  public ReportProvider(OperationCache operationCache, AnnouncementCache announcementCache,
      UserCache userCache, MockCache mockCache) {
    this.operationCache = operationCache;
    this.announcementCache = announcementCache;
    this.userCache = userCache;
    this.mockCache = mockCache;
  }

  /**
   * Получение списка задач пакетов операций для отчёта.
   *
   * @param fromDate отбирать пакеты с даты
   * @param toDate отбирать пакеты по дату
   * @param clientInfo информация о клиенте
   * @return список пакетов со списками выполненных задач
   */
  public List<OperationPackage> getOperationPackages(Date fromDate, Date toDate,
      ClientInfo clientInfo) {
    if (fromDate == null || toDate == null) {
      throw new InvalidDataException("Не указаны даты периода журнала");
    }

    OperationTasksRequest tasksRequest1 = new OperationTasksRequest();
    tasksRequest1.setTaskStatusGlobal(OperationTaskStatus.COMPLETED);
    tasksRequest1.setFromDate(fromDate);
    tasksRequest1.setToDate(toDate);
    final List<OperationPackage> operationPackages1 =
        operationCache.getTasksInPackages(tasksRequest1, clientInfo);

    OperationTasksRequest tasksRequest2 = new OperationTasksRequest();
    tasksRequest2.setTaskStatusGlobal(OperationTaskStatus.DECLINED);
    tasksRequest2.setFromDate(fromDate);
    tasksRequest2.setToDate(toDate);
    final List<OperationPackage> operationPackages2 =
        operationCache.getTasksInPackages(tasksRequest2, clientInfo);

    List<OperationPackage> operationPackages = new ArrayList<>();
    if (!CollectionUtils.isEmpty(operationPackages1)) {
      operationPackages.addAll(operationPackages1);
    }
    if (!CollectionUtils.isEmpty(operationPackages2)) {
      operationPackages.addAll(operationPackages2);
    }

    return operationPackages;
  }

  /**
   * Получение операции клиента.
   *
   * @param task задача операции
   * @return операция клиента
   */
  public Operation getOperation(OperationTask task) {
    if (task == null) {
      throw new InvalidDataException("Не указана задача операции");
    }
    return operationCache.getOperation(task.getId());
  }

  /**
   * Получение объекта пользователя.
   *
   * @param userLogin логин пользователя
   * @return объект пользователя
   */
  public User getUser(String userLogin) {
    return mockCache.getUser(userLogin, null);
  }

  /**
   * Получение объекта оператора.
   *
   * @param userLogin логин пользователя
   * @param clientInfo информация о клиенте
   * @return объект оператора
   */
  public Operator getOperator(String userLogin, ClientInfo clientInfo) {
    return !StringUtils.isEmpty(userLogin) ? userCache.getOperator(userLogin, clientInfo) : null;
  }

  /**
   * Расчёт комиссии по операции.
   *
   * @param accountId номер счёта
   * @param amount сумма операции
   * @param operation операция клиента
   * @param clientInfo информация о клиенте
   * @return значение комиссии
   */
  public BigDecimal getCommission(String accountId, BigDecimal amount,
      Operation operation, ClientInfo clientInfo) {
    return (accountId != null && amount != null && operation != null)
        ? announcementCache.getCommission(
            new AccountOperationRequest(accountId, amount, operation.getTypeCode()), clientInfo)
        : null;
  }
}
