package ru.philit.ufs.model.entity.common;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Контейнер для списка обхектов из Мастер-системы.
 */
@ToString
@Getter
@Setter
public class ExternalEntityList<T extends ExternalEntity> extends ExternalEntity {

  private List<T> items;

  public ExternalEntityList() {
    items = new ArrayList<>();
  }
}
