package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Данные текущего баланса счёта.
 */
@ToString
@Getter
@Setter
public class CurrentBalance
    implements Serializable {

  private BigDecimal openingBalanceRub;
  private BigDecimal closingBalanceRub;
  private BigDecimal openingBalanceFcurr;
  private BigDecimal closingBalanceFcurr;
  private BigDecimal currentBalance;

}
