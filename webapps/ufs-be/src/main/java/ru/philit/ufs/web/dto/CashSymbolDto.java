package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект кассового символа операции.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class CashSymbolDto implements Serializable {

  /**
   * Код кассового символа.
   */
  private String code;
  /**
   * Описание кассового символа.
   */
  private String desc;
  /**
   * Сумма, соответствующая кассовому символу.
   */
  private String amount;

}
