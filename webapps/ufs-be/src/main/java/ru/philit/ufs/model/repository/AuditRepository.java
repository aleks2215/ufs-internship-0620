package ru.philit.ufs.model.repository;

import java.util.List;
import ru.philit.ufs.model.entity.service.AuditEntity;

/**
 * Интерфейс действий по аудиту.
 */
public interface AuditRepository {

  boolean saveAuditEntity(AuditEntity entity);

  List<AuditEntity> getAuditEntities();

}
