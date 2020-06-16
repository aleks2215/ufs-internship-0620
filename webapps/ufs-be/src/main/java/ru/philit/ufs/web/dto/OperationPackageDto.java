package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект пакета операций.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperationPackageDto implements Serializable {

  /**
   * Идентификатор пакета задач.
   */
  private String id;
  /**
   * Статус пакета задач.
   */
  private String status;
  /**
   * Задачи взноса наличных.
   */
  private List<OperationTaskDto> toCardDeposits;
  /**
   * Задачи выдачи наличных.
   */
  private List<OperationTaskDto> fromCardWithdraw;

}
