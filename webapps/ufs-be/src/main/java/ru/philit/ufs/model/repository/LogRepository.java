package ru.philit.ufs.model.repository;

import java.util.List;
import ru.philit.ufs.model.entity.service.LogEntity;

/**
 * Интерфейс действий по журналированию.
 */
public interface LogRepository {

  boolean saveLogEntity(LogEntity entity);

  List<LogEntity> getLogEntities();

}
