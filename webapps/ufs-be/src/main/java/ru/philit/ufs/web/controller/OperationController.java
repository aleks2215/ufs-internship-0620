package ru.philit.ufs.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.OperationMapper;
import ru.philit.ufs.web.provider.OperationProvider;
import ru.philit.ufs.web.view.AddTaskToPackageReq;
import ru.philit.ufs.web.view.AddTaskToPackageResp;
import ru.philit.ufs.web.view.FinishOperationReq;
import ru.philit.ufs.web.view.FinishOperationResp;
import ru.philit.ufs.web.view.GetTasksForwardedResp;

/**
 * Контроллер действий с операциями.
 */
@RestController
@RequestMapping("/operation")
public class OperationController {

  private final OperationProvider provider;
  private final OperationMapper mapper;

  @Autowired
  public OperationController(OperationProvider provider, OperationMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Оформление операции на УРМ пользователя.
   *
   * @param request идентификатор УРМ, ОВН для добавления
   * @param clientInfo информация о клиенте
   * @return пакет с новой активной задачей
   */
  @RequestMapping(value = "/continueUrm", method = RequestMethod.POST)
  public AddTaskToPackageResp continueWithUrm(
      @RequestBody AddTaskToPackageReq request, ClientInfo clientInfo
  ) {
    OperationPackage packageWithTask = provider.addActiveDepositTask(
        request.getWorkplaceId(), mapper.asEntity(request.getDeposit()), clientInfo
    );
    return new AddTaskToPackageResp().withSuccess(mapper.asDto(packageWithTask));
  }

  /**
   * Оформление операции на кассе.
   *
   * @param request идентификатор кассы, ОВН для добавления
   * @param clientInfo информация о клиенте
   * @return пакет с новой перенаправленной задачей
   */
  @RequestMapping(value = "/continueCash", method = RequestMethod.POST)
  public AddTaskToPackageResp continueWithCash(
      @RequestBody AddTaskToPackageReq request, ClientInfo clientInfo
  ) {
    OperationPackage packageWithTask = provider.addForwardedDepositTask(
        request.getWorkplaceId(), mapper.asEntity(request.getDeposit()), clientInfo
    );
    return new AddTaskToPackageResp().withSuccess(mapper.asDto(packageWithTask));
  }

  /**
   * Подтверждение операции.
   *
   * @param request базовые параметры завершения операции
   * @param clientInfo информация о клиенте
   * @return результат подтверждения операции
   */
  @RequestMapping(value = "/confirm", method = RequestMethod.POST)
  public FinishOperationResp confirmOperation(
      @RequestBody FinishOperationReq request, ClientInfo clientInfo
  ) {
    Operation operation = provider.confirmOperation(
        mapper.asEntity(request.getPackageId()), mapper.asEntity(request.getTaskId()),
        request.getWorkplaceId(), request.getOperationTypeCode(), clientInfo
    );
    return new FinishOperationResp().withSuccess(mapper.asDto(operation));
  }

  /**
   * Отмена операции.
   *
   * @param request базовые параметры завершения операции
   * @return результат отмены операции
   */
  @RequestMapping(value = "/cancel", method = RequestMethod.POST)
  public FinishOperationResp cancelOperation(
      @RequestBody FinishOperationReq request, ClientInfo clientInfo
  ) {
    Operation operation = provider.cancelOperation(
        mapper.asEntity(request.getPackageId()), mapper.asEntity(request.getTaskId()),
        request.getWorkplaceId(), request.getOperationTypeCode(), clientInfo
    );
    return new FinishOperationResp().withSuccess(mapper.asDto(operation));
  }

  /**
   * Получение списка операция в статусе FORWARDED.
   *
   * @return список операций
   */
  @RequestMapping(value = "/tasksForwarded", method = RequestMethod.POST)
  public GetTasksForwardedResp getTasksForwarded(
      ClientInfo clientInfo
  ) {
    List<OperationTaskCardDeposit> tasks = provider.getForwardedDepositTasks(clientInfo);
    return new GetTasksForwardedResp().withSuccess(mapper.asDto(tasks));
  }
}
