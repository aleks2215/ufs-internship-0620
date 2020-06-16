package ru.philit.ufs.web.provider;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.service.AuditService;
import ru.philit.ufs.service.LogService;

/**
 * Бизнес-логика работы с сервисами аудита и журналирования.
 */
@Service
public class ServiceProvider {

  private final AuditService auditService;
  private final LogService logService;

  @Autowired
  public ServiceProvider(AuditService auditService, LogService logService) {
    this.auditService = auditService;
    this.logService = logService;
  }

  /**
   * Получение истории аудита.
   *
   * @return список записей аудитов запросов
   */
  public List<AuditEntity> getAuditRequests() {
    List<AuditEntity> auditRequests = auditService.getAuditRequests();

    if (CollectionUtils.isEmpty(auditRequests)) {
      auditRequests = Collections.emptyList();
    }
    return auditRequests;
  }

  /**
   * Получение истории журналирования.
   *
   * @return список записей журналирования событий
   */
  public List<LogEntity> getLogEvents() {
    List<LogEntity> logEvents = logService.getLogEvents();

    if (CollectionUtils.isEmpty(logEvents)) {
      logEvents = Collections.emptyList();
    }
    return logEvents;
  }

  /**
   * Журналирование события, совершённого пользователем.
   *
   * @param clientInfo информация о клиенте
   * @param event событие пользователя
   * @param date дата и время события
   * @return результат добавления события
   */
  public boolean addLogEvent(ClientInfo clientInfo, String event, String date) {
    return logService.logEvent(clientInfo, event, date);
  }
}
