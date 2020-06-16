package ru.philit.ufs.model.cache;

import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;

/**
 * Интерфейс доступа к кешу данных ЕФС для модуля интеграции с КСШ.
 */
public interface IsEsbCache {

  void putRequest(String requestId, ExternalEntityRequest request);

  void pushResponse(ExternalEntity entity);

}
