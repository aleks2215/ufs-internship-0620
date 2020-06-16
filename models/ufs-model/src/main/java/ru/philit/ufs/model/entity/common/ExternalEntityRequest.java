package ru.philit.ufs.model.entity.common;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Контейнер для запроса данных из Мастер-системы.
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ExternalEntityRequest
    implements Serializable {

  private String sessionId;
  private Serializable requestData;
  private String entityType;
  private Long uid;

  public ExternalEntityRequest() {
    this.uid = (long) (Math.random() * 1000000);
  }
}
