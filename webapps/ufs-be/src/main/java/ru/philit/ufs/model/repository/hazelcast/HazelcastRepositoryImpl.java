package ru.philit.ufs.model.repository.hazelcast;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.philit.ufs.model.cache.hazelcast.HazelcastBeClient;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.repository.AuditRepository;
import ru.philit.ufs.model.repository.LogRepository;

/**
 * Модуль репозиториев сервисов, обеспечивающий доступ к кешу.
 */
@Repository
public class HazelcastRepositoryImpl implements AuditRepository, LogRepository {

  private final HazelcastBeClient client;

  @Autowired
  public HazelcastRepositoryImpl(HazelcastBeClient client) {
    this.client = client;
  }

  @Override
  public boolean saveAuditEntity(AuditEntity entity) {
    return client.getAuditedRequests().add(entity);
  }

  @Override
  public boolean saveLogEntity(LogEntity entity) {
    return client.getLoggedEvents().add(entity);
  }

  @Override
  public List<AuditEntity> getAuditEntities() {
    return client.getAuditedRequests();
  }

  @Override
  public List<LogEntity> getLogEntities() {
    return client.getLoggedEvents();
  }
}
