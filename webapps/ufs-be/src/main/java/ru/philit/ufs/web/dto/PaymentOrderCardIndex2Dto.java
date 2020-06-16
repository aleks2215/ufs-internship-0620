package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект картотеки №2.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class PaymentOrderCardIndex2Dto implements Serializable {

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
   * Сумма по п/п.
   */
  private String totalAmount;
  /**
   * Флаг наличия частичной оплаты.
   */
  private boolean paidPartly;
  /**
   * Сумма частичной оплаты.
   */
  private String partAmount;
  /**
   * Дата поступления.
   */
  private String comeInDate;
  /**
   * Порядок очереди.
   */
  private String priority;
  /**
   * Списания суммы в разрезе дат.
   */
  private List<PaidDto> paids;

}
