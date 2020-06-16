package ru.philit.ufs.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект взноса наличных на кредитную карту.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class CardDepositDto extends AnnouncementDto {

  /**
   * Id задачи.
   */
  private String taskId;
  /**
   * Id пакета задач.
   */
  private String packageId;
  /**
   * Статус задачи.
   */
  private String taskStatus;
  /**
   * Кредитная карта.
   */
  private CreditCardDto card;
  /**
   * Идентификатор представителя.
   */
  private String representativeId;

}
