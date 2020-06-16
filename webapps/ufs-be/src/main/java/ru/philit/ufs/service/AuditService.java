package ru.philit.ufs.service;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.repository.AuditRepository;
import ru.philit.ufs.util.UuidUtils;

/**
 * Сервис аудита.
 */
@Service
@Slf4j
public class AuditService {

  private final AuditRepository repository;

  @Autowired
  public AuditService(AuditRepository repository) {
    this.repository = repository;
  }

  /**
   * Аудит запроса данных.
   *
   * @param clientInfo информация о клиенте
   * @param requestType тип запроса
   * @param initialObject данные до запроса
   * @param requestObject данные, передаваемые в запрос
   * @param responseObject данные, полученные после выполнения запроса
   */
  public void auditRequest(ClientInfo clientInfo, String requestType, Object initialObject,
      Object requestObject, Object responseObject) {
    AuditEntity entity = new AuditEntity();

    entity.setId(UuidUtils.getRandomUuid());
    entity.setUserLogin(clientInfo.getUser().getLogin());
    entity.setActionType(requestType);
    entity.setInitialObject(SerializationUtils.clone((Serializable) initialObject));
    entity.setRequestObject(SerializationUtils.clone((Serializable) requestObject));
    entity.setResponseObject(SerializationUtils.clone((Serializable) responseObject));
    entity.setClientHost(clientInfo.getHost());
    entity.setServerHost(getServerHost());

    log.debug("User {} initiated request: {} - at {}", entity.getUserLogin(), requestType,
        entity.getActionDate());
    repository.saveAuditEntity(entity);
  }

  /**
   * Аудит запроса данных.
   *
   * @param clientInfo информация о клиенте
   * @param requestType тип запроса
   * @param requestObject данные, передаваемые в запрос
   * @param responseObject данные, полученные после выполнения запроса
   */
  public void auditRequest(ClientInfo clientInfo, String requestType, Object requestObject,
      Object responseObject) {
    auditRequest(clientInfo, requestType, null, requestObject, responseObject);
  }

  /**
   * Получение записей аудита запросов данных.
   */
  public List<AuditEntity> getAuditRequests() {
    return repository.getAuditEntities();
  }

  private String getServerHost() {
    try {
      return InetAddress.getLocalHost().getHostAddress();

    } catch (UnknownHostException e) {
      log.error("An error occurred while getting host address", e);
      return "localhost";
    }
  }

}
