package ru.philit.ufs.model.entity.common;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Контейнер для двух списков обхектов из Мастер-системы.
 */
@Getter
@Setter
public class ExternalEntityList2<T1 extends ExternalEntity, T2 extends ExternalEntity>
    extends ExternalEntityList<T1> {

  private List<T2> items2;

  public ExternalEntityList2() {
    items2 = new ArrayList<>();
  }
}
