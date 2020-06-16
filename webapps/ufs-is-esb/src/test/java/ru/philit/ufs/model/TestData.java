package ru.philit.ufs.model;

import java.math.BigInteger;
import java.util.Date;
import lombok.Getter;
import ru.philit.ufs.model.converter.ConverterBaseTest;
import ru.philit.ufs.model.converter.esb.eks.EksAdapterBaseTest;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountType;
import ru.philit.ufs.model.entity.account.AccountancyType;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs.SrvAccountByCardNumRsMessage;
import ru.philit.ufs.model.entity.request.RequestType;

/**
 * Контейнер данных используемых в тестирвоании интеграции.
 */
public class TestData {

  public static final String SESSION_ID = "444";
  public static final String CARD_NUMBER = "4894123569871254";
  public static final String ACCOUNT_ID = "111111";
  public static final String LEGAL_ENTITY_ID = "112";
  public static final String AGREEMENT_ID = "45678";

  public static final String ACCOUNT_TYPE_ID = "01";
  public static final String ACCOUNT_STATUS = "status1";
  public static final int ACCOUNTANCY_TYPE_ID = 2;

  public static final String BANK_BIC = "04857512";
  public static final String BANK_NAME = "Отделение № 2";
  public static final String BANK_CORR_ACC = "30845214660004854009";
  public static final String BANK_INN = "07754854112";
  public static final String BANK_TB = "13";
  public static final String BANK_GOSB = "8456";
  public static final String BANK_OSB = "8463";
  public static final String BANK_VSP = "13587";
  public static final String BANK_SUBBRANCH_ID = "1234567";
  public static final String BANK_SUBBRANCH_CODE = "8463/13587";
  public static final Long BANK_SUBBRANCH_LEVEL = 4L;

  public static final String CURRENCY_TYPE = "RUB";
  public static final String CURRENCY_CODE = "642";

  public static final String ACCOUNT_REQUEST_FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";
  public static final String ACCOUNT_REQUEST_TIME = "2017-05-12T17:57:00.000+03:00";
  public static final String ACCOUNT_REQUEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" "
      + "standalone=\"yes\"?><SrvAccountByCardNumRq><HeaderInfo><rqUID>" + ACCOUNT_REQUEST_FIX_UUID
      + "</rqUID><rqTm>" + ACCOUNT_REQUEST_TIME + "</rqTm><spName>ufs</spName><systemId>eks"
      + "</systemId></HeaderInfo><SrvAccountByCardNumRqMessage><cardNumber>" + CARD_NUMBER
      + "</cardNumber></SrvAccountByCardNumRqMessage></SrvAccountByCardNumRq>";
  public static final String ACCOUNT_RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" "
      + "standalone=\"yes\"?><SrvAccountByCardNumRs><HeaderInfo><rqUID>" + ACCOUNT_REQUEST_FIX_UUID
      + "</rqUID><rqTm>" + ACCOUNT_REQUEST_TIME + "</rqTm><spName>ufs</spName><systemId>eks"
      + "</systemId></HeaderInfo><SrvAccountByCardNumRsMessage><accountId>" + ACCOUNT_ID
      + "</accountId><accountStatus>" + ACCOUNT_STATUS + "</accountStatus><accountStatusDesc>"
      + "Счет активен</accountStatusDesc><accountTypeId>" + ACCOUNT_TYPE_ID + "</accountTypeId>"
      + "<accountCurrencyCode>" + CURRENCY_CODE + "</accountCurrencyCode><accountCurrencyType>"
      + CURRENCY_TYPE + "</accountCurrencyType><bankBIC>" + BANK_BIC + "</bankBIC><agreementInfo>"
      + "<agreementId>" + AGREEMENT_ID + "</agreementId><dateFrom>2017-05-01+03:00</dateFrom>"
      + "<dateTo>2017-07-01+03:00</dateTo></agreementInfo><bankInfo><bankBIC>" + BANK_BIC
      + "</bankBIC><correspondentAccount>" + BANK_CORR_ACC + "</correspondentAccount><bankName>"
      + BANK_NAME + "</bankName><locationTitle>Москва, ул. Академика Янгеля, д. 13 к. 3"
      + "</locationTitle><locationType>type3</locationType><TBCode>" + BANK_TB + "</TBCode>"
      + "<GOSBCode>" + BANK_GOSB + "</GOSBCode><OSBCode>" + BANK_OSB + "</OSBCode><VSPCode>"
      + BANK_VSP + "</VSPCode><subbranchCode>" + BANK_SUBBRANCH_CODE + "</subbranchCode></bankInfo>"
      + "<accountIdentity><IDtype>Unknown identity</IDtype><ID>112255</ID></accountIdentity>"
      + "<accountTitle>Счет клиента</accountTitle><accountancyTypeId>" + ACCOUNTANCY_TYPE_ID
      + "</accountancyTypeId><lastTransactionDt>2017-05-04+03:00</lastTransactionDt>"
      + "</SrvAccountByCardNumRsMessage></SrvAccountByCardNumRs>";

  @Getter
  private ExternalEntityRequest accountByCardNumberRequest;
  @Getter
  private Account account;
  @Getter
  private SrvAccountByCardNumRs accountResponse;

  /**
   * Конструктор контейнера данных.
   */
  public TestData() {
    accountByCardNumberRequest = new ExternalEntityRequest();
    accountByCardNumberRequest.setSessionId(SESSION_ID);
    accountByCardNumberRequest.setEntityType(RequestType.ACCOUNT_BY_CARD_NUMBER);
    accountByCardNumberRequest.setRequestData(CARD_NUMBER);

    account = new Account();
    account.setRequestUid(ACCOUNT_REQUEST_FIX_UUID);
    account.setReceiveDate(new Date());
    account.setId(ACCOUNT_ID);
    account.setStatus(ACCOUNT_STATUS);
    account.setChangeStatusDescription("Счет активен");
    account.setType(AccountType.getByCode(ACCOUNT_TYPE_ID));
    account.setAccountancyType(AccountancyType.getByCode(ACCOUNTANCY_TYPE_ID));
    account.setCurrencyType(CURRENCY_TYPE);
    account.setCurrencyCode(CURRENCY_CODE);
    account.setTitle("Счет клиента");
    account.setLastTransactionDate(ConverterBaseTest.date(2017, 5, 4, 0, 0));
    account.getAgreement().setId(AGREEMENT_ID);
    account.getAgreement().setOpenDate(ConverterBaseTest.date(2017, 5, 1, 0, 0));
    account.getAgreement().setCloseDate(ConverterBaseTest.date(2017, 7, 1, 0, 0));
    account.getSubbranch().setId(BANK_SUBBRANCH_ID);
    account.getSubbranch().setTbCode(BANK_TB);
    account.getSubbranch().setGosbCode(BANK_GOSB);
    account.getSubbranch().setOsbCode(BANK_OSB);
    account.getSubbranch().setVspCode(BANK_VSP);
    account.getSubbranch().setSubbranchCode(BANK_SUBBRANCH_CODE);
    account.getSubbranch().setLevel(BANK_SUBBRANCH_LEVEL);
    account.getSubbranch().setInn(BANK_INN);
    account.getSubbranch().setBic(BANK_BIC);
    account.getSubbranch().setBankName(BANK_NAME);
    account.getSubbranch().setCorrespondentAccount(BANK_CORR_ACC);
    account.getSubbranch().setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    account.getSubbranch().setLocationType("type3");
    account.getIdentity().setId("112255");
    account.getIdentity().setType("Unknown identity");

    accountResponse = new SrvAccountByCardNumRs();
    accountResponse.setHeaderInfo(EksAdapterBaseTest.headerInfo(ACCOUNT_REQUEST_FIX_UUID));
    accountResponse.getHeaderInfo().setRqTm(ConverterBaseTest.xmlCalendar(2017, 5, 12, 17, 57));
    accountResponse.setSrvAccountByCardNumRsMessage(new SrvAccountByCardNumRsMessage());
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountId(ACCOUNT_ID);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountStatus(ACCOUNT_STATUS);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountStatusDesc("Счет активен");
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountTypeId(ACCOUNT_TYPE_ID);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountCurrencyType(CURRENCY_TYPE);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountCurrencyCode(CURRENCY_CODE);
    accountResponse.getSrvAccountByCardNumRsMessage().setBankBIC(BANK_BIC);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountTitle("Счет клиента");
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountancyTypeId(
        BigInteger.valueOf(ACCOUNTANCY_TYPE_ID));
    accountResponse.getSrvAccountByCardNumRsMessage().setLastTransactionDt(
        ConverterBaseTest.xmlCalendar(2017, 5, 4, 0, 0));
    SrvAccountByCardNumRsMessage.AgreementInfo agreementInfo =
        new SrvAccountByCardNumRsMessage.AgreementInfo();
    agreementInfo.setAgreementId(AGREEMENT_ID);
    agreementInfo.setDateFrom(ConverterBaseTest.xmlCalendar(2017, 5, 1, 0, 0));
    agreementInfo.setDateTo(ConverterBaseTest.xmlCalendar(2017, 7, 1, 0, 0));
    accountResponse.getSrvAccountByCardNumRsMessage().setAgreementInfo(agreementInfo);
    SrvAccountByCardNumRsMessage.BankInfo bankInfo = new SrvAccountByCardNumRsMessage.BankInfo();
    bankInfo.setBankBIC(BANK_BIC);
    bankInfo.setCorrespondentAccount(BANK_CORR_ACC);
    bankInfo.setBankName(BANK_NAME);
    bankInfo.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo.setLocationType("type3");
    bankInfo.setTBCode(BANK_TB);
    bankInfo.setGOSBCode(BANK_GOSB);
    bankInfo.setOSBCode(BANK_OSB);
    bankInfo.setVSPCode(BANK_VSP);
    bankInfo.setSubbranchCode(BANK_SUBBRANCH_CODE);
    accountResponse.getSrvAccountByCardNumRsMessage().setBankInfo(bankInfo);
    SrvAccountByCardNumRsMessage.AccountIdentity accountIdentity =
        new SrvAccountByCardNumRsMessage.AccountIdentity();
    accountIdentity.setID("112255");
    accountIdentity.setIDtype("Unknown identity");
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountIdentity(accountIdentity);
  }
}
