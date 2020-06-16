package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import java.math.BigInteger;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.AccountType;
import ru.philit.ufs.model.entity.account.AccountancyType;
import ru.philit.ufs.model.entity.account.Agreement;
import ru.philit.ufs.model.entity.account.CurrentBalance;
import ru.philit.ufs.model.entity.account.ServiceTariff;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRq.SrvAccountByCardNumRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs.SrvAccountByCardNumRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRq.SrvAccountByIdRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs.SrvAccountByIdMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRq.SrvAccountResiduesByIdRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs.SrvAccountResiduesByIdMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRq.SrvLEAccountListRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs.SrvLEAccountListRsMessage;

/**
 * Преобразователь между сущностью Account и соответствующим транспортным объектом.
 */
public class AccountAdapter extends EksAdapter {

  //******** Converters ********

  private static AccountType accountType(String accountTypeId) {
    return (accountTypeId != null) ? AccountType.getByCode(accountTypeId) : null;
  }

  private static AccountancyType accountancyType(BigInteger acountancyTypeId) {
    return (acountancyTypeId != null) ? AccountancyType.getByCode(acountancyTypeId.intValue())
        : null;
  }

  //******** Mappers *******

  private static void map(SrvAccountByCardNumRsMessage message, Account account) {
    account.setId(message.getAccountId());
    account.setStatus(message.getAccountStatus());
    account.setChangeStatusDescription(message.getAccountStatusDesc());
    account.setCurrencyType(message.getAccountCurrencyType());
    account.setCurrencyCode(message.getAccountCurrencyCode());
    account.setTitle(message.getAccountTitle());
    account.setLastTransactionDate(date(message.getLastTransactionDt()));
    account.setType(accountType(message.getAccountTypeId()));
    account.setAccountancyType(accountancyType(message.getAccountancyTypeId()));
    map(message.getAgreementInfo(), account);
    map(message.getBankInfo(), account);
    map(message.getAccountIdentity(), account);
  }

  private static void map(SrvAccountByCardNumRsMessage.AgreementInfo agreement, Account account) {
    account.getAgreement().setId(agreement.getAgreementId());
    account.getAgreement().setOpenDate(date(agreement.getDateFrom()));
    account.getAgreement().setCloseDate(date(agreement.getDateTo()));
  }

  private static void map(SrvAccountByCardNumRsMessage.BankInfo bank, Account account) {
    account.getSubbranch().setTbCode(bank.getTBCode());
    account.getSubbranch().setGosbCode(bank.getGOSBCode());
    account.getSubbranch().setOsbCode(bank.getOSBCode());
    account.getSubbranch().setVspCode(bank.getVSPCode());
    account.getSubbranch().setSubbranchCode(bank.getSubbranchCode());
    account.getSubbranch().setBic(bank.getBankBIC());
    account.getSubbranch().setBankName(bank.getBankName());
    account.getSubbranch().setCorrespondentAccount(bank.getCorrespondentAccount());
    account.getSubbranch().setLocationTitle(bank.getLocationTitle());
    account.getSubbranch().setLocationType(bank.getLocationType());
  }

  private static void map(SrvAccountByCardNumRsMessage.AccountIdentity identity, Account account) {
    account.getIdentity().setId(identity.getID());
    account.getIdentity().setType(identity.getIDtype());
  }

  private static void map(SrvAccountByIdMessage message, Account account) {
    account.setId(message.getAccountId());
    account.setStatus(message.getAccountStatus());
    account.setChangeStatusDescription(message.getAccountStatusDesc());
    account.setCurrencyType(message.getAccountCurrencyType());
    account.setCurrencyCode(message.getAccountCurrencyCode());
    account.setTitle(message.getAccountTitle());
    account.setLastTransactionDate(date(message.getLastTransactionDt()));
    account.setType(accountType(message.getAccountTypeId()));
    account.setAccountancyType(accountancyType(message.getAccountancyTypeId()));
    map(message.getAgreementInfo(), account);
    map(message.getBankInfo(), account);
    map(message.getAccountIdentity(), account);
  }

  private static void map(SrvAccountByIdMessage.AgreementInfo agreement, Account account) {
    account.getAgreement().setId(agreement.getAgreementId());
    account.getAgreement().setOpenDate(date(agreement.getDateFrom()));
    account.getAgreement().setCloseDate(date(agreement.getDateTo()));
  }

  private static void map(SrvAccountByIdMessage.BankInfo bank, Account account) {
    account.getSubbranch().setTbCode(bank.getTBCode());
    account.getSubbranch().setGosbCode(bank.getGOSBCode());
    account.getSubbranch().setOsbCode(bank.getOSBCode());
    account.getSubbranch().setVspCode(bank.getVSPCode());
    account.getSubbranch().setSubbranchCode(bank.getSubbranchCode());
    account.getSubbranch().setBic(bank.getBankBIC());
    account.getSubbranch().setBankName(bank.getBankName());
    account.getSubbranch().setCorrespondentAccount(bank.getCorrespondentAccount());
    account.getSubbranch().setLocationTitle(bank.getLocationTitle());
    account.getSubbranch().setLocationType(bank.getLocationType());
  }

  private static void map(SrvAccountByIdMessage.AccountIdentity identity, Account account) {
    account.getIdentity().setId(identity.getID());
    account.getIdentity().setType(identity.getIDtype());
  }

  private static void map(SrvLEAccountListRsMessage.AccountItem message, Account account) {
    account.setId(message.getAccountId());
    account.setStatus(message.getAccountStatus());
    account.setChangeStatusDescription(message.getAccountStatusDesc());
    account.setCurrencyType(message.getAccountCurrencyType());
    account.setCurrencyCode(message.getAccountCurrencyCode());
    account.setTitle(message.getAccountTitle());
    account.setLastTransactionDate(date(message.getLastTransactionDt()));
    account.setType(accountType(message.getAccountTypeId()));
    account.setAccountancyType(accountancyType(message.getAccountancyTypeId()));
    map(message.getAgreementInfo(), account);
    map(message.getBankInfo(), account);
    map(message.getAccountIdentity(), account);
  }

  private static void map(SrvLEAccountListRsMessage.AccountItem.AgreementInfo agreement,
      Account account) {
    account.getAgreement().setId(agreement.getAgreementId());
    account.getAgreement().setOpenDate(date(agreement.getDateFrom()));
    account.getAgreement().setCloseDate(date(agreement.getDateTo()));
  }

  private static void map(SrvLEAccountListRsMessage.AccountItem.BankInfo bank, Account account) {
    account.getSubbranch().setTbCode(bank.getTBCode());
    account.getSubbranch().setGosbCode(bank.getGOSBCode());
    account.getSubbranch().setOsbCode(bank.getOSBCode());
    account.getSubbranch().setVspCode(bank.getVSPCode());
    account.getSubbranch().setSubbranchCode(bank.getSubbranchCode());
    account.getSubbranch().setBic(bank.getBankBIC());
    account.getSubbranch().setBankName(bank.getBankName());
    account.getSubbranch().setCorrespondentAccount(bank.getCorrespondentAccount());
    account.getSubbranch().setLocationTitle(bank.getLocationTitle());
    account.getSubbranch().setLocationType(bank.getLocationType());
  }

  private static void map(SrvLEAccountListRsMessage.AccountItem.AccountIdentity identity,
      Account account) {
    account.getIdentity().setId(identity.getID());
    account.getIdentity().setType(identity.getIDtype());
  }

  private static void map(SrvAccountResiduesByIdMessage message, AccountResidues residues) {
    residues.setAccountId(message.getAccountId());
    residues.setOperationalDate(date(message.getOperationalDate()));
    residues.setResiduesDate(date(message.getResiduesDttm()));
    residues.setActive(message.getOpenStatusInfo().isActiveFlg());
    residues.setOpenDate(date(message.getOpenStatusInfo().getDateOpen()));
    residues.setCloseDate(date(message.getOpenStatusInfo().getDateClose()));
    residues.setCurrencyType(message.getAccountCurrencyType());
    residues.setCurrencyCode(message.getAccountCurrencyCode());
    map(message.getCurrentAvailBalance(), residues.getCurrentBalance());
    residues.setFixedBalance(message.getFixedBalance());
    residues.setExpectedBalance(message.getExpectedBalanceInfo().getExpectedBalance());
    residues.setExpectedBalanceDif(message.getExpectedBalanceInfo().getBalanceDif());
    residues.setAvailOverdraftLimit(message.getAvailOverdraftLimit());
    residues.setOverdraftLimit(message.getOverdraftLimit());
    residues.setAvailDebetLimit(message.getAvailDebetLimit());
    residues.setSeizureFlag(message.getSeizureInfo().isSeizureFlg());
    residues.setSeizureCount(longValue(message.getSeizureInfo().getSeizNum()));
    residues.setCardIndex1Flag(message.getCardIndexesInfo().isCardindex1Flg());
    residues.setCardIndex1Count(longValue(message.getCardIndexesInfo().getCardindex1DocNum()));
    residues.setCardIndex1Amount(BigDecimal.valueOf(longValue(message.getCardindexesAmount()
        .getCardindex1Total())));
    residues.setCardIndex2Flag(message.getCardIndexesInfo().isCardindex2Flg());
    residues.setCardIndex2Count(longValue(message.getCardIndexesInfo().getCardindex2DocNum()));
    residues.setCardIndex2Amount(BigDecimal.valueOf(longValue(message.getCardindexesAmount()
        .getCardindex2Total())));
    residues.setTargetLoanAmount(message.getTargetLoanAmount());
    residues.setLoanRepaymentAmount(message.getLoanRepaymentAmount());
    map(message.getAdditionalAgreements(), residues.getAdditionalAgreement());
    for (SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service service
        : message.getServiceTarificationInfo().getService()) {
      ServiceTariff serviceTariff = new ServiceTariff();
      map(service, serviceTariff);
      residues.getServiceTariffs().add(serviceTariff);
    }
    residues.setCompProdMemberFlg(message.isCompProdMemberFlg());
  }

  private static void map(SrvAccountResiduesByIdMessage.CurrentAvailBalance messageBalance,
      CurrentBalance balance) {
    balance.setOpeningBalanceRub(messageBalance.getOpeningBalanceRub());
    balance.setClosingBalanceRub(messageBalance.getClosingBalanceRub());
    balance.setOpeningBalanceFcurr(messageBalance.getOpeningBalanceFcurr());
    balance.setClosingBalanceFcurr(messageBalance.getClosingBalanceFcurr());
    balance.setCurrentBalance(messageBalance.getCurrentBalance());
  }

  private static void map(SrvAccountResiduesByIdMessage.AdditionalAgreements messageAgreement,
      Agreement agreement) {
    agreement.setId(messageAgreement.getAgreementId());
    agreement.setOpenDate(date(messageAgreement.getDateFrom()));
    agreement.setCloseDate(date(messageAgreement.getDateTo()));
  }

  private static void map(SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service service,
      ServiceTariff serviceTariff) {
    serviceTariff.setServiceCode(service.getServiceCode());
    serviceTariff.setServiceDescription(service.getServiceDesc());
    serviceTariff.setPercentageTariffValue(longValue(service.getPercentage()));
    serviceTariff.setFixedTariffAmount(service.getFixed());
  }

  //******** Methods *******

  /**
   * Возвращает объект запроса счета по id.
   */
  public static SrvAccountByIdRq requestById(String accountId) {
    SrvAccountByIdRq request = new SrvAccountByIdRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvAccountByIdRqMessage(new SrvAccountByIdRqMessage());
    request.getSrvAccountByIdRqMessage().setAccountId(accountId);
    return request;
  }

  /**
   * Возвращает объект запроса счета по момеру карты.
   */
  public static SrvAccountByCardNumRq requestByCardNumber(String cardNumber) {
    SrvAccountByCardNumRq request = new SrvAccountByCardNumRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvAccountByCardNumRqMessage(new SrvAccountByCardNumRqMessage());
    request.getSrvAccountByCardNumRqMessage().setCardNumber(cardNumber);
    return request;
  }

  /**
   * Возвращает объект запроса счетов по Юр.лицу.
   */
  public static SrvLEAccountListRq requestByLegalEntity(String legalEntityId) {
    SrvLEAccountListRq request = new SrvLEAccountListRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvLEAccountListRqMessage(new SrvLEAccountListRqMessage());
    request.getSrvLEAccountListRqMessage().setLegalEntityId(legalEntityId);
    return request;
  }

  /**
   * Возвращает объект запроса остатков и параметров счета по id.
   */
  public static SrvAccountResiduesByIdRq requestResiduesById(String accountId) {
    SrvAccountResiduesByIdRq request = new SrvAccountResiduesByIdRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvAccountResiduesByIdRqMessage(new SrvAccountResiduesByIdRqMessage());
    request.getSrvAccountResiduesByIdRqMessage().setAccountId(accountId);
    request.getSrvAccountResiduesByIdRqMessage().setGetAdditionalAgreements(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetAvailDebetLimit(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetAvailOverdraftLimit(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetCardindexAmount(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetCardIndexInfo(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetCurrentAvailBalance(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetExpectedBalance(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetFixedBalance(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetLoanRepaymentAmount(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetOpenStatus(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetOverdraftLimit(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetSeizureStatus(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetServiceTarification(true);
    request.getSrvAccountResiduesByIdRqMessage().setGetTargetLoanAmount(true);
    return request;
  }

  /**
   * Преобразует транспортный объект счета во внутреннюю сущность.
   */
  public static Account convert(SrvAccountByIdRs response) {
    Account account = new Account();
    map(response.getHeaderInfo(), account);
    map(response.getSrvAccountByIdMessage(), account);
    return account;
  }

  /**
   * Преобразует транспортный объект счета во внутреннюю сущность.
   */
  public static Account convert(SrvAccountByCardNumRs response) {
    Account account = new Account();
    map(response.getHeaderInfo(), account);
    map(response.getSrvAccountByCardNumRsMessage(), account);
    return account;
  }

  /**
   * Преобразует транспортный объект счета во внутреннюю сущность.
   */
  public static ExternalEntityList<Account> convert(SrvLEAccountListRs response) {
    ExternalEntityList<Account> accountList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), accountList);
    for (SrvLEAccountListRsMessage.AccountItem accountItem
        : response.getSrvLEAccountListRsMessage().getAccountItem()) {
      Account account = new Account();
      map(response.getHeaderInfo(), account);
      map(accountItem, account);
      accountList.getItems().add(account);
    }
    return accountList;
  }

  /**
   * Преобразует транспортный объект остатков счета во внутреннюю сущность.
   */
  public static AccountResidues convert(SrvAccountResiduesByIdRs response) {
    AccountResidues residues = new AccountResidues();
    map(response.getHeaderInfo(), residues);
    map(response.getSrvAccountResiduesByIdMessage(), residues);
    return residues;
  }
}
