package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект задачи операций.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperationTaskDto implements Serializable {

  /**
   * Идентификатор задачи.
   */
  private String id;
  /**
   * Статус задачи.
   */
  private String status;
  /**
   * Дата создания.
   */
  private String createdDate;
  /**
   * Дата изменения.
   */
  private String changedDate;
  /**
   * Дата изменения статуса.
   */
  private String statusChangedDate;

}
