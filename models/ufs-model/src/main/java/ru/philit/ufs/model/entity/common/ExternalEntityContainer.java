package ru.philit.ufs.model.entity.common;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Контейнер для данных произвольного типа, полученных из Мастер-систем.
 */
@EqualsAndHashCode(of = {"data"}, callSuper = false)
@ToString
@Getter
@Setter
public class ExternalEntityContainer<T extends Serializable> extends ExternalEntity {

  private T data;
  private String responseCode;

  public ExternalEntityContainer() {}

  public ExternalEntityContainer(T data) {
    this.data = data;
  }
}
