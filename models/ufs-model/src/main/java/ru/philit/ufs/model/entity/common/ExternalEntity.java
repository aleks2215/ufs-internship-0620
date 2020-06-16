package ru.philit.ufs.model.entity.common;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Базовый класс для сущностей, приходящих из Мастер-систем.
 */
@Getter
@Setter
public class ExternalEntity implements Serializable {

  /**
   * Уникальный идентификатор запроса сущности в Мастер-системе.
   */
  private String requestUid;
  /**
   * Дата получения сущности из Мастер-системы.
   */
  private Date receiveDate;

}
