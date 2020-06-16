package ru.philit.ufs.esb.service;

import ru.philit.ufs.model.entity.common.ExternalEntityRequest;

/**
 * Сервис на маршрутизацию сообщений между Hazelcast и КСШ.
 */
public interface EsbService {

  /**
   * Отправляет запрос данных к КСШ.
   */
  void sendRequest(ExternalEntityRequest entityRequest);

}
