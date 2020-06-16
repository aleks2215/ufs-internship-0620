package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект записи об аресте.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class SeizureDto implements Serializable {

  /**
   * Тип ареста.
   */
  private Long type;
  /**
   * Основание ареста.
   */
  private String reason;
  /**
   * Дата начала наложения ареста.
   */
  private String fromDate;
  /**
   * Дата окончания наложения ареста.
   */
  private String toDate;
  /**
   * Сумма ареста.
   */
  private String amount;
  /**
   * ЮЛ / орган-инициатор.
   */
  private String initiatorShortName;

}
