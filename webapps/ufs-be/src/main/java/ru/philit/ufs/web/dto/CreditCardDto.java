package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект кредитной карты.
 */
@EqualsAndHashCode(of = {"number"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class CreditCardDto implements Serializable {

  /**
   * Номер карты.
   */
  private String number;
  /**
   * Дата истечения срока действия карты.
   */
  private String expiryDate;
  /**
   * Сетевой код выдачи карты.
   */
  private String issuingNetworkCode;
  /**
   * Тип карты.
   */
  private String type;
  /**
   * Имя владельца карты.
   */
  private String ownerFirstName;
  /**
   * Фамилия владельца карты.
   */
  private String ownerLastName;

}
