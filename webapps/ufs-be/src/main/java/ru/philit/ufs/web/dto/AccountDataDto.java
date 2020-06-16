package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

/**
 * Объект данных о счёте.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AccountDataDto implements Serializable {

  /**
   * Реквизиты счета и информация об остатке.
   */
  private AccountDto account;
  /**
   * Данные о ЮЛ.
   */
  private LegalEntityDto legalEntity;
  /**
   * Данные об остатках и параметрах счета.
   */
  private AccountResiduesDto accountResidues;
  /**
   * Данные о картотеке №1.
   */
  private List<PaymentOrderCardIndex1Dto> cardIndexes1;
  /**
   * Данные о картотеке №2.
   */
  private List<PaymentOrderCardIndex2Dto> cardIndexes2;
  /**
   * Данные об аресте/приостановлении обслуживания.
   */
  private List<SeizureDto> seizures;

  /**
   * Добавление информации о счёте.
   */
  public AccountDataDto withAccount(AccountDto account) {
    setAccount(account);
    return this;
  }

  /**
   * Добавление информации о юр. лице.
   */
  public AccountDataDto withLegalEntity(LegalEntityDto legalEntity) {
    setLegalEntity(legalEntity);
    return this;
  }

  /**
   * Добавление информации об остатках и параметрах счета.
   */
  public AccountDataDto withAccountResidues(AccountResiduesDto accountResidues) {
    setAccountResidues(accountResidues);
    return this;
  }

  /**
   * Добавление информации о расходных ордерах картотеки 1.
   */
  public AccountDataDto withCardIndexes1(List<PaymentOrderCardIndex1Dto> cardIndexes1) {
    if (getCardIndexes1() == null) {
      setCardIndexes1(new ArrayList<PaymentOrderCardIndex1Dto>());
    }
    if (!CollectionUtils.isEmpty(cardIndexes1)) {
      getCardIndexes1().addAll(cardIndexes1);
    }
    return this;
  }

  /**
   * Добавление информации о расходных ордерах картотеки 2.
   */
  public AccountDataDto withCardIndexes2(List<PaymentOrderCardIndex2Dto> cardIndexes2) {
    if (getCardIndexes2() == null) {
      setCardIndexes2(new ArrayList<PaymentOrderCardIndex2Dto>());
    }
    if (!CollectionUtils.isEmpty(cardIndexes2)) {
      getCardIndexes2().addAll(cardIndexes2);
    }
    return this;
  }

  /**
   * Добавление информации об арестованных суммах.
   */
  public AccountDataDto withSeizures(List<SeizureDto> seizures) {
    if (getSeizures() == null) {
      setSeizures(new ArrayList<SeizureDto>());
    }
    if (!CollectionUtils.isEmpty(seizures)) {
      getSeizures().addAll(seizures);
    }
    return this;
  }

}
