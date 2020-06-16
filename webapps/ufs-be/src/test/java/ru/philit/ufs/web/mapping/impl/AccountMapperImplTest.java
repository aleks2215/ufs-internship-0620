package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.AccountType;
import ru.philit.ufs.model.entity.account.AccountancyType;
import ru.philit.ufs.model.entity.account.Agreement;
import ru.philit.ufs.model.entity.account.CurrentBalance;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.PaymentPriority;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2.PaidPart;
import ru.philit.ufs.web.dto.AccountDto;
import ru.philit.ufs.web.dto.AccountResiduesDto;
import ru.philit.ufs.web.dto.AgreementDto;
import ru.philit.ufs.web.dto.LegalEntityDto;
import ru.philit.ufs.web.dto.PaidDto;
import ru.philit.ufs.web.dto.PaymentOrderCardIndex1Dto;
import ru.philit.ufs.web.dto.PaymentOrderCardIndex2Dto;
import ru.philit.ufs.web.dto.SeizureDto;
import ru.philit.ufs.web.mapping.AccountMapper;

public class AccountMapperImplTest {

  private static final String ACCOUNT_ID = "628726651431";
  private static final String CURRENCY_TYPE = "RUB";
  private static final String LAST_TRANSACTION_DATE = "03.05.2017";
  private static final String STATUS = "Active";
  private static final String CHANGE_STATUS_DESCRIPTION = "Some text";
  private static final String LEGAL_ENTITY_ID = "12917284";
  private static final String SHORT_NAME = "Short Name";
  private static final String FULL_NAME = "Full Name";
  private static final String INN = "ИНН";
  private static final String OGRN = "ОГРН";
  private static final String KPP = "КПП";
  private static final String LEGAL_ADDRESS = "Legal Address";
  private static final String FACT_ADDRESS = "Fact Address";
  private static final Long INDEX = 374127L;
  private static final String DOC_ID = "DocId";
  private static final String DOC_TYPE = "DocType";
  private static final String RECIPIENT_SHORT_NAME = "RecipientShortName";
  private static final String PAYMENT_AMOUNT = "2354152.23";
  private static final String COME_IN_DATE = "01.04.2017";
  private static final String PAID_AMOUNT = "364.22";
  private static final String PAID_DATE = "10.05.2017";
  private static final String TOTAL_AMOUNT = "261241.21";
  private static final boolean PAID_PARTLY = true;
  private static final String PART_AMOUNT = "24131.89";
  private static final String PRIORITY = "III";
  private static final Long SEIZURE_TYPE = 0L;
  private static final String REASON = "Reason";
  private static final String FROM_DATE = "01.04.2017";
  private static final String TO_DATE = "01.05.2017";
  private static final String SEIZURE_AMOUNT = "1625162.24";
  private static final String INITIATOR_SHORT_NAME = "InitiatorShortName";
  private static final String BALANCE = "101.1";
  private static final AccountType ACCOUNT_TYPE = AccountType.BUDGET;
  private static final AccountancyType ACCOUNTANCY_TYPE = AccountancyType.ACTIVE;
  private static final String CURRENCY_CODE = "CurrencyCode";
  private static final String AGREEMENT_ID = "174812";
  private static final String OPEN_DATE = "03.06.2017";
  private static final String CLOSE_DATE = "04.06.2017";

  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  private final AccountMapper mapper = new AccountMapperImpl();

  @Test
  public void testAsDto_Account() throws Exception {
    // given
    Account entity = getAccount();

    // when
    AccountDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), ACCOUNT_ID);
    assertEquals(dto.getStatus(), STATUS);
    assertEquals(dto.getChangeStatusDesc(), CHANGE_STATUS_DESCRIPTION);
    assertEquals(dto.getType(), ACCOUNT_TYPE.value());
    assertEquals(dto.getAccountancyType(), ACCOUNTANCY_TYPE.value());
    assertEquals(dto.getCurrencyType(), CURRENCY_TYPE);
    assertEquals(dto.getCurrencyCode(), CURRENCY_CODE);
    assertEquals(dto.getLastTransactionDate(), LAST_TRANSACTION_DATE);

    AgreementDto agreementDto = dto.getAgreement();
    assertNotNull(agreementDto);
    assertEquals(agreementDto.getId(), AGREEMENT_ID);
    assertEquals(agreementDto.getOpenDate(), OPEN_DATE);
    assertEquals(agreementDto.getCloseDate(), CLOSE_DATE);
  }

  @Test
  public void testAsDto_Account_NullEntity() throws Exception {
    // when
    AccountDto dto = mapper.asDto((Account) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Account_NullAgreement() throws Exception {
    // given
    Account entity = new Account();
    entity.setAgreement(null);

    // when
    AccountDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertNull(dto.getAgreement());
  }

  @Test
  public void testAsDto_LegalEntity() throws Exception {
    // given
    LegalEntity entity = getLegalEntity();

    // when
    LegalEntityDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), LEGAL_ENTITY_ID);
    assertEquals(dto.getShortName(), SHORT_NAME);
    assertEquals(dto.getFullName(), FULL_NAME);
    assertEquals(dto.getInn(), INN);
    assertEquals(dto.getOgrn(), OGRN);
    assertEquals(dto.getKpp(), KPP);
    assertEquals(dto.getLegalAddress(), LEGAL_ADDRESS);
    assertEquals(dto.getFactAddress(), FACT_ADDRESS);
  }

  @Test
  public void testAsDto_LegalEntity_NullEntity() throws Exception {
    // when
    LegalEntityDto dto = mapper.asDto((LegalEntity) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_AccountResidues() throws Exception {
    // given
    AccountResidues entity = getAccountResidues();

    // when
    AccountResiduesDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getCurrentBalance(), BALANCE);
    assertEquals(dto.getExpectedBalance(), BALANCE);
    assertEquals(dto.getFixedBalance(), BALANCE);
  }

  @Test
  public void testAsDto_AccountResidues_NullEntity() throws Exception {
    // when
    AccountResiduesDto dto = mapper.asDto((AccountResidues) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_AccountResidues_NullCurrentBalance() throws Exception {
    // given
    AccountResidues entity = getAccountResidues();
    entity.setCurrentBalance(null);

    // when
    AccountResiduesDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertNull(dto.getCurrentBalance());
  }

  @Test
  public void testAsDto_PaymentOrderCardIndex1() throws Exception {
    // given
    PaymentOrderCardIndex1 entity = getPaymentOrderCardIndex1();

    // when
    PaymentOrderCardIndex1Dto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getDocId(), DOC_ID);
    assertEquals(dto.getDocType(), DOC_TYPE);
    assertEquals(dto.getRecipientShortName(), RECIPIENT_SHORT_NAME);
    assertEquals(dto.getAmount(), PAYMENT_AMOUNT);
    assertEquals(dto.getComeInDate(), COME_IN_DATE);
  }

  @Test
  public void testAsDto_PaymentOrderCardIndex1_NullEntity() throws Exception {
    // when
    PaymentOrderCardIndex1Dto dto = mapper.asDto((PaymentOrderCardIndex1) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_PaymentOrderCardIndex2() throws Exception {
    // given
    PaymentOrderCardIndex2 entity = getPaymentOrderCardIndex2();

    // when
    PaymentOrderCardIndex2Dto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getDocId(), DOC_ID);
    assertEquals(dto.getDocType(), DOC_TYPE);
    assertEquals(dto.getRecipientShortName(), RECIPIENT_SHORT_NAME);
    assertEquals(dto.getTotalAmount(), TOTAL_AMOUNT);
    assertEquals(dto.isPaidPartly(), PAID_PARTLY);
    assertEquals(dto.getPartAmount(), PART_AMOUNT);
    assertEquals(dto.getComeInDate(), COME_IN_DATE);
    assertNotNull(dto.getPaids());
    assertFalse(dto.getPaids().isEmpty());
    assertEquals(dto.getPriority(), PRIORITY);

    PaidDto paidDto = dto.getPaids().get(0);
    assertEquals(paidDto.getAmount(), PAID_AMOUNT);
    assertEquals(paidDto.getDate(), PAID_DATE);
  }

  @Test
  public void testAsDto_PaymentOrderCardIndex2_NullEntity() throws Exception {
    // when
    PaymentOrderCardIndex2Dto dto = mapper.asDto((PaymentOrderCardIndex2) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Seizure() throws Exception {
    // given
    Seizure entity = getSeizure();

    // when
    SeizureDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getType(), SEIZURE_TYPE);
    assertEquals(dto.getReason(), REASON);
    assertEquals(dto.getFromDate(), FROM_DATE);
    assertEquals(dto.getToDate(), TO_DATE);
    assertEquals(dto.getAmount(), SEIZURE_AMOUNT);
    assertEquals(dto.getInitiatorShortName(), INITIATOR_SHORT_NAME);
  }

  @Test
  public void testAsDto_Seizure_NullEntity() throws Exception {
    // when
    SeizureDto dto = mapper.asDto((Seizure) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsIndex1Dto() throws Exception {
    // given
    List<PaymentOrderCardIndex1> entities = getPaymentOrderCardIndexes1();

    // when
    List<PaymentOrderCardIndex1Dto> dtos = mapper.asIndex1Dto(entities);

    // then
    assertNotNull(dtos);
    assertEquals(dtos.size(), entities.size());
  }

  @Test
  public void testAsIndex1Dto_NullEntity() throws Exception {
    // when
    List<PaymentOrderCardIndex1Dto> dtos = mapper.asIndex1Dto(null);

    // then
    assertNotNull(dtos);
    assertTrue(dtos.isEmpty());
  }

  @Test
  public void testAsIndex2Dto() throws Exception {
    // given
    List<PaymentOrderCardIndex2> entities = getPaymentOrderCardIndexes2();

    // when
    List<PaymentOrderCardIndex2Dto> dtos = mapper.asIndex2Dto(entities);

    // then
    assertNotNull(dtos);
    assertEquals(dtos.size(), entities.size());
  }

  @Test
  public void testAsIndex2Dto_NullEntity() throws Exception {
    // when
    List<PaymentOrderCardIndex2Dto> dtos = mapper.asIndex2Dto(null);

    // then
    assertNotNull(dtos);
    assertTrue(dtos.isEmpty());
  }

  @Test
  public void testAsSeizureDto() throws Exception {
    // given
    List<Seizure> entities = getSeizures();

    // when
    List<SeizureDto> dtos = mapper.asSeizureDto(entities);

    // then
    assertNotNull(dtos);
    assertEquals(dtos.size(), entities.size());
  }

  @Test
  public void testAsSeizureDto_NullEntity() throws Exception {
    // when
    List<SeizureDto> dtos = mapper.asSeizureDto(null);

    // then
    assertNotNull(dtos);
    assertTrue(dtos.isEmpty());
  }

  private Account getAccount() throws Exception {
    Account entity = new Account();

    entity.setId(ACCOUNT_ID);
    entity.setStatus(STATUS);
    entity.setChangeStatusDescription(CHANGE_STATUS_DESCRIPTION);
    entity.setType(ACCOUNT_TYPE);
    entity.setAccountancyType(ACCOUNTANCY_TYPE);
    entity.setCurrencyType(CURRENCY_TYPE);
    entity.setCurrencyCode(CURRENCY_CODE);
    entity.setAgreement(getAgreement());
    entity.setLastTransactionDate(shortDateFormat.parse(LAST_TRANSACTION_DATE));

    return entity;
  }

  private Agreement getAgreement() throws Exception {
    Agreement entity = new Agreement();

    entity.setId(AGREEMENT_ID);
    entity.setOpenDate(shortDateFormat.parse(OPEN_DATE));
    entity.setCloseDate(shortDateFormat.parse(CLOSE_DATE));

    return entity;
  }

  private LegalEntity getLegalEntity() {
    LegalEntity entity = new LegalEntity();

    entity.setId(LEGAL_ENTITY_ID);
    entity.setShortName(SHORT_NAME);
    entity.setFullName(FULL_NAME);
    entity.setInn(INN);
    entity.setOgrn(OGRN);
    entity.setKpp(KPP);
    entity.setLegalAddress(LEGAL_ADDRESS);
    entity.setFactAddress(FACT_ADDRESS);

    return entity;
  }

  private AccountResidues getAccountResidues() {
    AccountResidues entity = new AccountResidues();

    entity.setCurrentBalance(new CurrentBalance());
    entity.getCurrentBalance().setCurrentBalance(new BigDecimal(BALANCE));
    entity.setFixedBalance(new BigDecimal(BALANCE));
    entity.setExpectedBalance(new BigDecimal(BALANCE));

    return entity;
  }

  private PaymentOrderCardIndex1 getPaymentOrderCardIndex1() throws Exception {
    PaymentOrderCardIndex1 entity = new PaymentOrderCardIndex1();

    entity.setNum(INDEX);
    entity.setDocId(DOC_ID);
    entity.setDocType(DOC_TYPE);
    entity.setRecipientShortName(RECIPIENT_SHORT_NAME);
    entity.setAmount(new BigDecimal(PAYMENT_AMOUNT));
    entity.setComeInDate(shortDateFormat.parse(COME_IN_DATE));

    return entity;
  }

  private PaymentOrderCardIndex2 getPaymentOrderCardIndex2() throws Exception {
    PaymentOrderCardIndex2 entity = new PaymentOrderCardIndex2();

    entity.setNum(INDEX);
    entity.setDocId(DOC_ID);
    entity.setDocType(DOC_TYPE);
    entity.setRecipientShortName(RECIPIENT_SHORT_NAME);
    entity.setTotalAmount(new BigDecimal(TOTAL_AMOUNT));
    entity.setPaidPartly(PAID_PARTLY);
    entity.setPartAmount(new BigDecimal(PART_AMOUNT));
    entity.setComeInDate(shortDateFormat.parse(COME_IN_DATE));
    entity.setPaidParts(Collections.singletonList(getPaidPart()));
    entity.setPaymentPriority(PaymentPriority.getByCode(PRIORITY));

    return entity;
  }

  private Seizure getSeizure() throws Exception {
    Seizure entity = new Seizure();

    entity.setId(INDEX);
    entity.setType(SEIZURE_TYPE);
    entity.setReason(REASON);
    entity.setFromDate(shortDateFormat.parse(FROM_DATE));
    entity.setToDate(shortDateFormat.parse(TO_DATE));
    entity.setAmount(new BigDecimal(SEIZURE_AMOUNT));
    entity.setInitiatorShortName(INITIATOR_SHORT_NAME);

    return entity;
  }

  private PaidPart getPaidPart() throws Exception {
    PaidPart entity = new PaidPart();

    entity.setPaidAmount(new BigDecimal(PAID_AMOUNT));
    entity.setPaidDate(shortDateFormat.parse(PAID_DATE));

    return entity;
  }

  private List<PaymentOrderCardIndex1> getPaymentOrderCardIndexes1() {
    List<PaymentOrderCardIndex1> entities = new ArrayList<>();

    for (long index = 0; index < 3; index++) {
      PaymentOrderCardIndex1 item = new PaymentOrderCardIndex1();
      item.setNum(index);
      entities.add(item);
    }
    return entities;
  }

  private List<PaymentOrderCardIndex2> getPaymentOrderCardIndexes2() {
    List<PaymentOrderCardIndex2> entities = new ArrayList<>();

    for (long index = 0; index < 3; index++) {
      PaymentOrderCardIndex2 item = new PaymentOrderCardIndex2();
      item.setNum(index);
      entities.add(item);
    }
    return entities;
  }

  private List<Seizure> getSeizures() {
    List<Seizure> entities = new ArrayList<>();

    for (long index = 0; index < 3; index++) {
      Seizure item = new Seizure();
      item.setId(index);
      entities.add(item);
    }
    return entities;
  }
}
