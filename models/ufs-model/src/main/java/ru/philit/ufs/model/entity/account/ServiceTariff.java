package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Информация о тарифах на услуги Банка.
 */
@ToString
@Getter
@Setter
public class ServiceTariff
    implements Serializable {

  private String serviceCode;
  private String serviceDescription;
  private Long percentageTariffValue;
  private BigDecimal fixedTariffAmount;

}
