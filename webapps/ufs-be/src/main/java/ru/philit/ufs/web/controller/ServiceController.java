package ru.philit.ufs.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.ServiceMapper;
import ru.philit.ufs.web.provider.ServiceProvider;
import ru.philit.ufs.web.view.GetAuditHistoryResp;
import ru.philit.ufs.web.view.GetLogHistoryResp;
import ru.philit.ufs.web.view.LogEventReq;
import ru.philit.ufs.web.view.LogEventResp;

/**
 * Контроллер вспомогательных действий.
 */
@RestController
@RequestMapping("/")
public class ServiceController {

  private final ServiceProvider provider;
  private final ServiceMapper mapper;

  @Autowired
  public ServiceController(ServiceProvider provider, ServiceMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Журналирование события, совершённого пользователем.
   *
   * @param request событие пользователя
   * @param clientInfo информация о клиенте
   * @return результат добавления нового события
   */
  @RequestMapping(value = "/logger", method = RequestMethod.POST)
  public LogEventResp logEvent(
      @RequestBody LogEventReq request, ClientInfo clientInfo
  ) {
    boolean added = provider.addLogEvent(clientInfo, request.getEvent(), request.getDate());
    return new LogEventResp().withSuccess(added);
  }

  /**
   * Получение истории аудита.
   *
   * @return список записей аудитов запросов
   */
  @RequestMapping(value = "/auditHistory", method = RequestMethod.POST)
  public GetAuditHistoryResp getAuditHistory() {
    List<AuditEntity> auditRequests = provider.getAuditRequests();
    return new GetAuditHistoryResp().withSuccess(mapper.asAuditDto(auditRequests));
  }

  /**
   * Получение истории журналирования.
   *
   * @return список записей журналирования событий
   */
  @RequestMapping(value = "/logHistory", method = RequestMethod.POST)
  public GetLogHistoryResp getLogHistory() {
    List<LogEntity> logEvents = provider.getLogEvents();
    return new GetLogHistoryResp().withSuccess(mapper.asLogDto(logEvents));
  }

}
