package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.TestData;
import ru.philit.ufs.model.converter.ConverterBaseTest;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs.SrvAccountByIdMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs.SrvAccountResiduesByIdMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs.SrvLEAccountListRsMessage;

public class AccountAdapterTest extends EksAdapterBaseTest {

  private TestData testData;
  private SrvAccountByIdRs response1;
  private SrvLEAccountListRs response2;
  private SrvAccountResiduesByIdRs response3;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    testData = new TestData();

    response1 = new SrvAccountByIdRs();
    response1.setHeaderInfo(EksAdapterBaseTest.headerInfo(TestData.ACCOUNT_REQUEST_FIX_UUID));
    response1.getHeaderInfo().setRqTm(ConverterBaseTest.xmlCalendar(2017, 5, 12, 17, 57));
    response1.setSrvAccountByIdMessage(new SrvAccountByIdMessage());
    response1.getSrvAccountByIdMessage().setAccountId(TestData.ACCOUNT_ID);
    response1.getSrvAccountByIdMessage().setAccountStatus(TestData.ACCOUNT_STATUS);
    response1.getSrvAccountByIdMessage().setAccountStatusDesc("Счет активен");
    response1.getSrvAccountByIdMessage().setAccountTypeId(TestData.ACCOUNT_TYPE_ID);
    response1.getSrvAccountByIdMessage().setAccountCurrencyType(TestData.CURRENCY_TYPE);
    response1.getSrvAccountByIdMessage().setAccountCurrencyCode(TestData.CURRENCY_CODE);
    response1.getSrvAccountByIdMessage().setBankBIC(TestData.BANK_BIC);
    response1.getSrvAccountByIdMessage().setAccountTitle("Счет клиента");
    response1.getSrvAccountByIdMessage().setAccountancyTypeId(
        BigInteger.valueOf(TestData.ACCOUNTANCY_TYPE_ID));
    response1.getSrvAccountByIdMessage().setLastTransactionDt(
        ConverterBaseTest.xmlCalendar(2017, 5, 4, 0, 0));
    SrvAccountByIdMessage.AgreementInfo agreementInfo =
        new SrvAccountByIdMessage.AgreementInfo();
    agreementInfo.setAgreementId(TestData.AGREEMENT_ID);
    agreementInfo.setDateFrom(ConverterBaseTest.xmlCalendar(2017, 5, 1, 0, 0));
    agreementInfo.setDateTo(ConverterBaseTest.xmlCalendar(2017, 7, 1, 0, 0));
    response1.getSrvAccountByIdMessage().setAgreementInfo(agreementInfo);
    SrvAccountByIdMessage.BankInfo bankInfo = new SrvAccountByIdMessage.BankInfo();
    bankInfo.setBankBIC(TestData.BANK_BIC);
    bankInfo.setCorrespondentAccount(TestData.BANK_CORR_ACC);
    bankInfo.setBankName(TestData.BANK_NAME);
    bankInfo.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo.setLocationType("type3");
    bankInfo.setTBCode(TestData.BANK_TB);
    bankInfo.setGOSBCode(TestData.BANK_GOSB);
    bankInfo.setOSBCode(TestData.BANK_OSB);
    bankInfo.setVSPCode(TestData.BANK_VSP);
    bankInfo.setSubbranchCode(TestData.BANK_SUBBRANCH_CODE);
    response1.getSrvAccountByIdMessage().setBankInfo(bankInfo);
    SrvAccountByIdMessage.AccountIdentity accountIdentity =
        new SrvAccountByIdMessage.AccountIdentity();
    accountIdentity.setID("112255");
    accountIdentity.setIDtype("Unknown identity");
    response1.getSrvAccountByIdMessage().setAccountIdentity(accountIdentity);

    response2 = new SrvLEAccountListRs();
    response2.setHeaderInfo(EksAdapterBaseTest.headerInfo(TestData.ACCOUNT_REQUEST_FIX_UUID));
    response2.setSrvLEAccountListRsMessage(new SrvLEAccountListRsMessage());
    SrvLEAccountListRsMessage.AccountItem accountItem = new SrvLEAccountListRsMessage.AccountItem();
    accountItem.setAccountId(TestData.ACCOUNT_ID);
    accountItem.setAccountStatus(TestData.ACCOUNT_STATUS);
    accountItem.setAccountStatusDesc("Счет активен");
    accountItem.setAccountTypeId(TestData.ACCOUNT_TYPE_ID);
    accountItem.setAccountCurrencyType(TestData.CURRENCY_TYPE);
    accountItem.setAccountCurrencyCode(TestData.CURRENCY_CODE);
    accountItem.setBankBIC(TestData.BANK_BIC);
    accountItem.setAccountTitle("Счет клиента");
    accountItem.setAccountancyTypeId(BigInteger.valueOf(TestData.ACCOUNTANCY_TYPE_ID));
    accountItem.setLastTransactionDt(ConverterBaseTest.xmlCalendar(2017, 5, 4, 0, 0));
    SrvLEAccountListRsMessage.AccountItem.AgreementInfo agreementInfo2 =
        new SrvLEAccountListRsMessage.AccountItem.AgreementInfo();
    agreementInfo2.setAgreementId(TestData.AGREEMENT_ID);
    agreementInfo2.setDateFrom(ConverterBaseTest.xmlCalendar(2017, 5, 1, 0, 0));
    agreementInfo2.setDateTo(ConverterBaseTest.xmlCalendar(2017, 7, 1, 0, 0));
    accountItem.setAgreementInfo(agreementInfo2);
    SrvLEAccountListRsMessage.AccountItem.BankInfo bankInfo2 =
        new SrvLEAccountListRsMessage.AccountItem.BankInfo();
    bankInfo2.setBankBIC(TestData.BANK_BIC);
    bankInfo2.setCorrespondentAccount(TestData.BANK_CORR_ACC);
    bankInfo2.setBankName(TestData.BANK_NAME);
    bankInfo2.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo2.setLocationType("type3");
    bankInfo2.setTBCode(TestData.BANK_TB);
    bankInfo2.setGOSBCode(TestData.BANK_GOSB);
    bankInfo2.setOSBCode(TestData.BANK_OSB);
    bankInfo2.setVSPCode(TestData.BANK_VSP);
    bankInfo2.setSubbranchCode(TestData.BANK_SUBBRANCH_CODE);
    accountItem.setBankInfo(bankInfo2);
    SrvLEAccountListRsMessage.AccountItem.AccountIdentity accountIdentity2 =
        new SrvLEAccountListRsMessage.AccountItem.AccountIdentity();
    accountIdentity2.setID("112255");
    accountIdentity2.setIDtype("Unknown identity");
    accountItem.setAccountIdentity(accountIdentity2);
    response2.getSrvLEAccountListRsMessage().getAccountItem().add(accountItem);

    response3 = new SrvAccountResiduesByIdRs();
    response3.setHeaderInfo(EksAdapterBaseTest.headerInfo(TestData.ACCOUNT_REQUEST_FIX_UUID));
    response3.setSrvAccountResiduesByIdMessage(new SrvAccountResiduesByIdMessage());
    response3.getSrvAccountResiduesByIdMessage().setAccountId(TestData.ACCOUNT_ID);
    response3.getSrvAccountResiduesByIdMessage().setBankBIC(TestData.BANK_BIC);
    response3.getSrvAccountResiduesByIdMessage().setOperationalDate(xmlCalendar(2017, 5, 29, 0, 0));
    response3.getSrvAccountResiduesByIdMessage().setCurrentAvailBalance(
        new SrvAccountResiduesByIdMessage.CurrentAvailBalance());
    response3.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setOpeningBalanceRub(BigDecimal.valueOf(1150));
    response3.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setOpeningBalanceFcurr(BigDecimal.valueOf(1160));
    response3.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setClosingBalanceRub(BigDecimal.valueOf(1250));
    response3.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setClosingBalanceFcurr(BigDecimal.valueOf(1260));
    response3.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setCurrentBalance(BigDecimal.valueOf(1200));
    response3.getSrvAccountResiduesByIdMessage().setFixedBalance(BigDecimal.valueOf(1000));
    response3.getSrvAccountResiduesByIdMessage().setExpectedBalanceInfo(
        new SrvAccountResiduesByIdMessage.ExpectedBalanceInfo());
    response3.getSrvAccountResiduesByIdMessage().getExpectedBalanceInfo()
        .setExpectedBalance(BigDecimal.valueOf(1100));
    response3.getSrvAccountResiduesByIdMessage().getExpectedBalanceInfo()
        .setBalanceDif(BigDecimal.valueOf(100));
    response3.getSrvAccountResiduesByIdMessage().setAvailOverdraftLimit(BigDecimal.valueOf(5000));
    response3.getSrvAccountResiduesByIdMessage().setOverdraftLimit(BigDecimal.valueOf(2000));
    response3.getSrvAccountResiduesByIdMessage().setAvailDebetLimit(BigDecimal.valueOf(3000));
    response3.getSrvAccountResiduesByIdMessage().setSeizureInfo(
        new SrvAccountResiduesByIdMessage.SeizureInfo());
    response3.getSrvAccountResiduesByIdMessage().getSeizureInfo().setSeizureFlg(true);
    response3.getSrvAccountResiduesByIdMessage().getSeizureInfo().setSeizNum(BigInteger.valueOf(2));
    response3.getSrvAccountResiduesByIdMessage().setCardIndexesInfo(
        new SrvAccountResiduesByIdMessage.CardIndexesInfo());
    response3.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex1Flg(true);
    response3.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex1DocNum(
        BigInteger.valueOf(1));
    response3.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex2Flg(true);
    response3.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex2DocNum(
        BigInteger.valueOf(2));
    response3.getSrvAccountResiduesByIdMessage().setCardindexesAmount(
        new SrvAccountResiduesByIdMessage.CardindexesAmount());
    response3.getSrvAccountResiduesByIdMessage().getCardindexesAmount().setCardindex1Total(
        BigInteger.valueOf(500));
    response3.getSrvAccountResiduesByIdMessage().getCardindexesAmount().setCardindex2Total(
        BigInteger.valueOf(800));
    response3.getSrvAccountResiduesByIdMessage().setOpenStatusInfo(
        new SrvAccountResiduesByIdMessage.OpenStatusInfo());
    response3.getSrvAccountResiduesByIdMessage().getOpenStatusInfo().setActiveFlg(true);
    response3.getSrvAccountResiduesByIdMessage().getOpenStatusInfo().setDateOpen(
        xmlCalendar(2017, 5, 1, 0, 0));
    response3.getSrvAccountResiduesByIdMessage().getOpenStatusInfo().setDateClose(
        xmlCalendar(2017, 7, 1, 0, 0));
    response3.getSrvAccountResiduesByIdMessage().setTargetLoanAmount(BigDecimal.valueOf(4000));
    response3.getSrvAccountResiduesByIdMessage().setLoanRepaymentAmount(BigDecimal.valueOf(6000));
    response3.getSrvAccountResiduesByIdMessage().setAdditionalAgreements(
        new SrvAccountResiduesByIdMessage.AdditionalAgreements());
    response3.getSrvAccountResiduesByIdMessage().getAdditionalAgreements().setAgreementId("55687");
    response3.getSrvAccountResiduesByIdMessage().getAdditionalAgreements().setDateFrom(
        xmlCalendar(2017, 5, 1, 0, 0));
    response3.getSrvAccountResiduesByIdMessage().getAdditionalAgreements().setDateTo(
        xmlCalendar(2017, 6, 1, 0, 0));
    response3.getSrvAccountResiduesByIdMessage().setServiceTarificationInfo(
        new SrvAccountResiduesByIdMessage.ServiceTarificationInfo());
    SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service service1 =
        new SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service();
    service1.setServiceCode("0853");
    service1.setServiceDesc("Service1");
    service1.setPercentage(4);
    response3.getSrvAccountResiduesByIdMessage().getServiceTarificationInfo().getService()
        .add(service1);
    SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service service2 =
        new SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service();
    service2.setServiceCode("0824");
    service2.setServiceDesc("Service2");
    service2.setFixed(BigDecimal.valueOf(250));
    response3.getSrvAccountResiduesByIdMessage().getServiceTarificationInfo().getService()
        .add(service2);
    response3.getSrvAccountResiduesByIdMessage().setCompProdMemberFlg(false);
    response3.getSrvAccountResiduesByIdMessage().setAccountCurrencyType(TestData.CURRENCY_TYPE);
    response3.getSrvAccountResiduesByIdMessage().setAccountCurrencyCode(TestData.CURRENCY_CODE);
    response3.getSrvAccountResiduesByIdMessage().setResiduesDttm(xmlCalendar(2017, 5, 29, 15, 50));
  }


  @Test
  public void testRequestById() {
    SrvAccountByIdRq request = AccountAdapter.requestById(TestData.ACCOUNT_ID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvAccountByIdRqMessage());
    Assert.assertEquals(request.getSrvAccountByIdRqMessage().getAccountId(), TestData.ACCOUNT_ID);
  }

  @Test
  public void testRequestByCardNumber() {
    SrvAccountByCardNumRq request = AccountAdapter.requestByCardNumber(TestData.CARD_NUMBER);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvAccountByCardNumRqMessage());
    Assert.assertEquals(request.getSrvAccountByCardNumRqMessage().getCardNumber(),
        TestData.CARD_NUMBER);
  }

  @Test
  public void testRequestByLegalEntity() {
    SrvLEAccountListRq request = AccountAdapter.requestByLegalEntity(TestData.LEGAL_ENTITY_ID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvLEAccountListRqMessage());
    Assert.assertEquals(request.getSrvLEAccountListRqMessage().getLegalEntityId(),
        TestData.LEGAL_ENTITY_ID);
  }

  @Test
  public void testRequestResiduesById() {
    SrvAccountResiduesByIdRq request = AccountAdapter.requestResiduesById(TestData.ACCOUNT_ID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvAccountResiduesByIdRqMessage());
    Assert.assertEquals(request.getSrvAccountResiduesByIdRqMessage().getAccountId(),
        TestData.ACCOUNT_ID);
    Assert.assertNull(request.getSrvAccountResiduesByIdRqMessage().getBankBIC());
  }

  private void testAccount(Account account) {
    assertHeaderInfo(account);
    Assert.assertEquals(account.getId(), TestData.ACCOUNT_ID);
    Assert.assertEquals(account.getStatus(), TestData.ACCOUNT_STATUS);
    Assert.assertEquals(account.getChangeStatusDescription(), "Счет активен");
    Assert.assertEquals(account.getType().code(), TestData.ACCOUNT_TYPE_ID);
    Assert.assertEquals(account.getAccountancyType().code(), TestData.ACCOUNTANCY_TYPE_ID);
    Assert.assertEquals(account.getCurrencyType(), TestData.CURRENCY_TYPE);
    Assert.assertEquals(account.getCurrencyCode(), TestData.CURRENCY_CODE);
    Assert.assertEquals(account.getTitle(), "Счет клиента");
    Assert.assertEquals(account.getLastTransactionDate(), date(2017, 5, 4, 0, 0));
    Assert.assertEquals(account.getAgreement().getId(), TestData.AGREEMENT_ID);
    Assert.assertEquals(account.getAgreement().getOpenDate(), date(2017, 5, 1, 0, 0));
    Assert.assertEquals(account.getAgreement().getCloseDate(), date(2017, 7, 1, 0, 0));
    Assert.assertNull(account.getSubbranch().getId());
    Assert.assertEquals(account.getSubbranch().getTbCode(), TestData.BANK_TB);
    Assert.assertEquals(account.getSubbranch().getGosbCode(), TestData.BANK_GOSB);
    Assert.assertEquals(account.getSubbranch().getOsbCode(), TestData.BANK_OSB);
    Assert.assertEquals(account.getSubbranch().getVspCode(), TestData.BANK_VSP);
    Assert.assertEquals(account.getSubbranch().getSubbranchCode(), TestData.BANK_SUBBRANCH_CODE);
    Assert.assertNull(account.getSubbranch().getLevel());
    Assert.assertNull(account.getSubbranch().getInn());
    Assert.assertEquals(account.getSubbranch().getBic(), TestData.BANK_BIC);
    Assert.assertEquals(account.getSubbranch().getBankName(), TestData.BANK_NAME);
    Assert.assertEquals(account.getSubbranch().getCorrespondentAccount(), TestData.BANK_CORR_ACC);
    Assert.assertEquals(account.getSubbranch().getLocationTitle(),
        "Москва, ул. Академика Янгеля, д. 13 к. 3");
    Assert.assertEquals(account.getSubbranch().getLocationType(), "type3");
    Assert.assertEquals(account.getIdentity().getId(), "112255");
    Assert.assertEquals(account.getIdentity().getType(), "Unknown identity");
  }

  @Test
  public void testConvertSrvAccountByIdRs() {
    Account account = AccountAdapter.convert(response1);
    testAccount(account);
  }

  @Test
  public void testConvertSrvAccountByCardNumRs() {
    Account account = AccountAdapter.convert(testData.getAccountResponse());
    testAccount(account);
  }

  @Test
  public void testConvertSrvLeAccountListRs() {
    ExternalEntityList<Account> accountList = AccountAdapter.convert(response2);
    assertHeaderInfo(accountList);
    Assert.assertEquals(accountList.getItems().size(), 1);
    testAccount(accountList.getItems().get(0));
  }

  @Test
  public void testConvertSrvAccountResiduesByIdRs() {
    AccountResidues accountResidues = AccountAdapter.convert(response3);
    assertHeaderInfo(accountResidues);
    Assert.assertEquals(accountResidues.getAccountId(), TestData.ACCOUNT_ID);
    Assert.assertEquals(accountResidues.getOperationalDate(), date(2017, 5, 29, 0, 0));
    Assert.assertEquals(accountResidues.getResiduesDate(), date(2017, 5, 29, 15, 50));
    Assert.assertTrue(accountResidues.isActive());
    Assert.assertEquals(accountResidues.getOpenDate(), date(2017, 5, 1, 0, 0));
    Assert.assertEquals(accountResidues.getCloseDate(), date(2017, 7, 1, 0, 0));
    Assert.assertEquals(accountResidues.getCurrencyType(), TestData.CURRENCY_TYPE);
    Assert.assertEquals(accountResidues.getCurrencyCode(), TestData.CURRENCY_CODE);
    Assert.assertEquals(accountResidues.getCurrentBalance().getOpeningBalanceRub(),
        BigDecimal.valueOf(1150));
    Assert.assertEquals(accountResidues.getCurrentBalance().getOpeningBalanceFcurr(),
        BigDecimal.valueOf(1160));
    Assert.assertEquals(accountResidues.getCurrentBalance().getClosingBalanceRub(),
        BigDecimal.valueOf(1250));
    Assert.assertEquals(accountResidues.getCurrentBalance().getClosingBalanceFcurr(),
        BigDecimal.valueOf(1260));
    Assert.assertEquals(accountResidues.getCurrentBalance().getCurrentBalance(),
        BigDecimal.valueOf(1200));
    Assert.assertEquals(accountResidues.getFixedBalance(), BigDecimal.valueOf(1000));
    Assert.assertEquals(accountResidues.getExpectedBalance(), BigDecimal.valueOf(1100));
    Assert.assertEquals(accountResidues.getExpectedBalanceDif(), BigDecimal.valueOf(100));
    Assert.assertEquals(accountResidues.getAvailOverdraftLimit(), BigDecimal.valueOf(5000));
    Assert.assertEquals(accountResidues.getOverdraftLimit(), BigDecimal.valueOf(2000));
    Assert.assertEquals(accountResidues.getAvailDebetLimit(), BigDecimal.valueOf(3000));
    Assert.assertTrue(accountResidues.isSeizureFlag());
    Assert.assertEquals(accountResidues.getSeizureCount(), Long.valueOf(2));
    Assert.assertTrue(accountResidues.isCardIndex1Flag());
    Assert.assertEquals(accountResidues.getCardIndex1Count(), Long.valueOf(1));
    Assert.assertEquals(accountResidues.getCardIndex1Amount(), BigDecimal.valueOf(500));
    Assert.assertTrue(accountResidues.isCardIndex2Flag());
    Assert.assertEquals(accountResidues.getCardIndex2Count(), Long.valueOf(2));
    Assert.assertEquals(accountResidues.getCardIndex2Amount(), BigDecimal.valueOf(800));
    Assert.assertEquals(accountResidues.getTargetLoanAmount(), BigDecimal.valueOf(4000));
    Assert.assertEquals(accountResidues.getLoanRepaymentAmount(), BigDecimal.valueOf(6000));
    Assert.assertEquals(accountResidues.getAdditionalAgreement().getId(), "55687");
    Assert.assertEquals(accountResidues.getAdditionalAgreement().getOpenDate(),
        date(2017, 5, 1, 0, 0));
    Assert.assertEquals(accountResidues.getAdditionalAgreement().getCloseDate(),
        date(2017, 6, 1, 0, 0));
    Assert.assertEquals(accountResidues.getServiceTariffs().size(), 2);
    Assert.assertEquals(accountResidues.getServiceTariffs().get(0).getServiceCode(), "0853");
    Assert.assertEquals(accountResidues.getServiceTariffs().get(0).getServiceDescription(),
        "Service1");
    Assert.assertEquals(accountResidues.getServiceTariffs().get(0).getPercentageTariffValue(),
        Long.valueOf(4));
    Assert.assertNull(accountResidues.getServiceTariffs().get(0).getFixedTariffAmount());
    Assert.assertEquals(accountResidues.getServiceTariffs().get(1).getFixedTariffAmount(),
        BigDecimal.valueOf(250));
    Assert.assertFalse(accountResidues.isCompProdMemberFlg());
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity1 = MultiAdapter.convert(response1);
    Assert.assertNotNull(externalEntity1);
    Assert.assertEquals(externalEntity1.getClass(), Account.class);

    ExternalEntity externalEntity0 = MultiAdapter.convert(testData.getAccountResponse());
    Assert.assertNotNull(externalEntity0);
    Assert.assertEquals(externalEntity0.getClass(), Account.class);

    ExternalEntity externalEntity2 = MultiAdapter.convert(response2);
    Assert.assertNotNull(externalEntity2);
    Assert.assertEquals(externalEntity2.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity2).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntity2).getItems().get(0).getClass(),
        Account.class);

    ExternalEntity externalEntity3 = MultiAdapter.convert(response3);
    Assert.assertNotNull(externalEntity3);
    Assert.assertEquals(externalEntity3.getClass(), AccountResidues.class);
  }
}
