package ru.philit.ufs.model.cache;

import java.util.List;
import ru.philit.ufs.model.entity.oper.OperationType;

/**
 * Интерфейс доступа к справочникам, закешированным локально.
 */
public interface DictionaryCache {

  List<OperationType> getOperationTypes();

  OperationType getOperationType(String operationTypeId);

}
