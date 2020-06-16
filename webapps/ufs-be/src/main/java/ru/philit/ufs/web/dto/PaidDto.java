package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект списания суммы за дату.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class PaidDto implements Serializable {

  /**
   * Дата списания.
   */
  private String date;
  /**
   * Сумма списания.
   */
  private String amount;

}
