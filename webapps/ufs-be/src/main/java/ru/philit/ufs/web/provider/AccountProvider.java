package ru.philit.ufs.web.provider;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.philit.ufs.model.cache.AccountCache;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с данными по карте.
 */
@Service
public class AccountProvider {

  private final AccountCache cache;

  @Autowired
  public AccountProvider(AccountCache cache) {
    this.cache = cache;
  }

  /**
   * Получение информации о реквизитах счета и об остатке.
   *
   * @param cardNumber номер карты
   * @param clientInfo информация о клиенте
   * @return информация о реквизитах счета и об остатке
   */
  public Account getAccount(String cardNumber, ClientInfo clientInfo) {
    Account account = cache.getAccount(cardNumber, clientInfo);
    if (account == null) {
      throw new InvalidDataException(
          "Счет по банковской карте не найден, или произошла ошибка при получении");
    }
    return account;
  }

  /**
   * Получение информации о юридическом лице.
   *
   * @param accountId идентификатор счета
   * @param clientInfo информация о клиенте
   * @return информация о юридическом лице
   */
  public LegalEntity getLegalEntity(String accountId, ClientInfo clientInfo) {
    LegalEntity legalEntity = cache.getLegalEntity(accountId, clientInfo);
    if (legalEntity == null) {
      throw new InvalidDataException(
          "Данные о юридическом лице не найдены, или произошла ошибка при получении");
    }
    return legalEntity;
  }

  /**
   * Получение информации об остатках и параметрах счета.
   *
   * @param accountId идентификатор счета
   * @param clientInfo информация о клиенте
   * @return информация об остатках и параметрах счета
   */
  public AccountResidues getAccountResidues(String accountId, ClientInfo clientInfo) {
    AccountResidues accountResidues = cache.getAccountResidues(accountId, clientInfo);
    if (accountResidues == null) {
      throw new InvalidDataException(
          "Данные об остатках не найдены, или произошла ошибка при получении");
    }
    return accountResidues;
  }

  /**
   * Получение информации о картотеке 1.
   *
   * @param accountId идентификатор счета
   * @param clientInfo информация о клиенте
   * @return информация о картотеке 1
   */
  public List<PaymentOrderCardIndex1> getCardIndexes1(String accountId, ClientInfo clientInfo) {
    List<PaymentOrderCardIndex1> cardIndexes1 = cache.getCardIndexes1(accountId, clientInfo);

    if (CollectionUtils.isEmpty(cardIndexes1)) {
      cardIndexes1 = Collections.emptyList();
    }
    return cardIndexes1;
  }

  /**
   * Получение информации о картотеке 2.
   *
   * @param accountId идентификатор счета
   * @param clientInfo информация о клиенте
   * @return информация о картотеке 2
   */
  public List<PaymentOrderCardIndex2> getCardIndexes2(String accountId, ClientInfo clientInfo) {
    List<PaymentOrderCardIndex2> cardIndexes2 = cache.getCardIndexes2(accountId, clientInfo);

    if (CollectionUtils.isEmpty(cardIndexes2)) {
      cardIndexes2 = Collections.emptyList();
    }
    return cardIndexes2;
  }

  /**
   * Получение информации об аресте/приостановлении обслуживания.
   *
   * @param accountId идентификатор счета
   * @param clientInfo информация о клиенте
   * @return информация об аресте/приостановлении обслуживания
   */
  public List<Seizure> getSeizures(String accountId, ClientInfo clientInfo) {
    List<Seizure> seizures = cache.getSeizures(accountId, clientInfo);

    if (CollectionUtils.isEmpty(seizures)) {
      seizures = Collections.emptyList();
    }
    return seizures;
  }
}
