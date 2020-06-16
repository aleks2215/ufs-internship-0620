package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект данных об остатках и параметрах счета.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AccountResiduesDto implements Serializable {

  /**
   * Текущий остаток.
   */
  private String currentBalance;
  /**
   * Неснижаемый остаток.
   */
  private String fixedBalance;
  /**
   * Текущий остаток с учетом дебетовых и кредитовых сумм платежных документов,
   * исполнение которых начато в АБС, но еще не закончено.
   */
  private String expectedBalance;

}
