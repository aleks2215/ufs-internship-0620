package ru.philit.ufs.model.entity.common;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс идентификации объектов в кеше в рамках отдельно сессии.
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class LocalKey<T extends Serializable>
    implements Serializable {

  private String sessionId;
  private T key;

  public LocalKey() {}

  public LocalKey(String sessionId, T key) {
    this.sessionId = sessionId;
    this.key = key;
  }
}
