package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект картотеки №1.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class PaymentOrderCardIndex1Dto implements Serializable {

  /**
   * Номер документа.
   */
  private String docId;
  /**
   * Вид документа.
   */
  private String docType;
  /**
   * Получатель.
   */
  private String recipientShortName;
  /**
   * Сумма.
   */
  private String amount;
  /**
   * Дата поступления.
   */
  private String comeInDate;

}
