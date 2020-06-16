package ru.philit.ufs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.repository.LogRepository;
import ru.philit.ufs.util.UuidUtils;

/**
 * Сервис журналирования.
 */
@Service
@Slf4j
public class LogService {

  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  private final LogRepository repository;

  @Autowired
  public LogService(LogRepository repository) {
    this.repository = repository;
  }

  /**
   * Журналирование события пользователя.
   *
   * @param clientInfo информация о клиенте
   * @param eventMessage сообщение события
   * @param eventDate дата события
   * @return результат добавления события
   */
  public boolean logEvent(ClientInfo clientInfo, String eventMessage, String eventDate) {
    LogEntity entity = new LogEntity();

    entity.setId(UuidUtils.getRandomUuid());
    entity.setUserLogin(clientInfo.getUser().getLogin());
    entity.setEventMessage(eventMessage);
    try {
      entity.setEventDate(longDateFormat.parse(eventDate));
    } catch (ParseException e) {
      entity.setEventDate(new Date());
    }

    log.debug("User {} logged event: {} - at {}", entity.getUserLogin(), eventMessage, eventDate);
    return repository.saveLogEntity(entity);
  }

  /**
   * Получение записей журналирования событий пользователя.
   */
  public List<LogEntity> getLogEvents() {
    return repository.getLogEntities();
  }

}
