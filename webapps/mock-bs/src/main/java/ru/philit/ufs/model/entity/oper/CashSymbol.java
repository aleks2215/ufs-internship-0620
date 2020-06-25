package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Кассовый символ операции.
 */
@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Setter
public class CashSymbol {

  private String code;
  private BigDecimal amount;
  private String description;

}
