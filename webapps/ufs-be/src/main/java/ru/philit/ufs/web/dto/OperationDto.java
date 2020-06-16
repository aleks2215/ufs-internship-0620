package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект операции.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperationDto implements Serializable {

  /**
   * Идентификатор операции.
   */
  private String id;
  /**
   * Тип операции.
   */
  private String type;
  /**
   * Статус.
   */
  private String status;
  /**
   * Идентификатор рабочего места.
   */
  private String workplaceId;
  /**
   * Дата создания.
   */
  private String createdDate;
  /**
   * Дата выполнения.
   */
  private String committedDate;
  /**
   * Причина возврата/отказа.
   */
  private String rollbackReason;

}
