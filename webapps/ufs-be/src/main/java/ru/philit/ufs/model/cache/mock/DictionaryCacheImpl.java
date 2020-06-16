package ru.philit.ufs.model.cache.mock;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.cache.DictionaryCache;
import ru.philit.ufs.model.entity.oper.OperationType;

/**
 * Модуль данных, обеспечивающий доступ к локально закешированным справочникам.
 */
@Service
public class DictionaryCacheImpl implements DictionaryCache {

  @Override
  public List<OperationType> getOperationTypes() {
    return null;
  }

  @Override
  public OperationType getOperationType(String operationTypeId) {
    return null;
  }
}
