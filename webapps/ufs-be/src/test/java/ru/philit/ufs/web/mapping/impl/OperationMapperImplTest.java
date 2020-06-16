package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import org.junit.Test;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.model.entity.account.CardNetworkCode;
import ru.philit.ufs.model.entity.account.CardType;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageStatus;
import ru.philit.ufs.model.entity.oper.OperationStatus;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.oper.OperationTaskStatus;
import ru.philit.ufs.model.entity.oper.OvnStatus;
import ru.philit.ufs.web.dto.CardDepositDto;
import ru.philit.ufs.web.dto.CashSymbolDto;
import ru.philit.ufs.web.dto.CreditCardDto;
import ru.philit.ufs.web.dto.OperationDto;
import ru.philit.ufs.web.dto.OperationPackageDto;
import ru.philit.ufs.web.dto.OperationTaskDto;
import ru.philit.ufs.web.mapping.OperationMapper;

public class OperationMapperImplTest {

  private static final String INN = "inn";
  private static final Long PACKAGE_ID = 42642735L;
  private static final OperationPackageStatus PACKAGE_STATUS = OperationPackageStatus.NEW;
  private static final Long RESPONSE_CODE = 0L;
  private static final Long TASK_ID = 56327473L;
  private static final OperationTaskStatus TASK_STATUS = OperationTaskStatus.ACTIVE;
  private static final String CREATED_DATE = "01.05.2017 12:00:00";
  private static final String CHANGED_DATE = "01.05.2017 12:01:00";
  private static final String STATUS_CHANGED_DATE = "01.05.2017 12:02:00";
  private static final String OPERATION_ID = "23746820";
  private static final OperationStatus OPERATION_STATUS = OperationStatus.NEW;
  private static final String COMMITTED_DATE = "01.05.2017 12:03:00";
  private static final String UID = "UID";
  private static final String NUM = "236";
  private static final OvnStatus STATUS = OvnStatus.PENDING;
  private static final String REP_FIO = "RepFio";
  private static final String LEGAL_ENTITY_SHORT_NAME = "LegalEntityShortName";
  private static final String AMOUNT = "23671212.32";
  private static final String KPP = "Kpp";
  private static final String ACCOUNT_ID = "AccountId";
  private static final String SENDER_ACCOUNT_ID = "SenderAccountId";
  private static final String SENDER_BANK = "SenderBank";
  private static final String SENDER_BANK_BIC = "SenderBankBic";
  private static final String RECIPIENT_ACCOUNT_ID = "RecipientAccountId";
  private static final String RECIPIENT_BANK = "RecipientBank";
  private static final String RECIPIENT_BANK_BIC = "RecipientBankBic";
  private static final String SOURCE = "Source";
  private static final boolean CLIENT_TYPE_FK = false;
  private static final String ORGANISATION_NAME_FK = "OrganisationNameFk";
  private static final String PERSONAL_ACCOUNT_ID = "PersonalAccountId";
  private static final String CURRENCY_TYPE = "CurrencyType";
  private static final String NUMBER = "Number";
  private static final String EXPIRY_DATE = "31.12.2017";
  private static final CardNetworkCode NETWORK_CODE = CardNetworkCode.MAESTRO;
  private static final CardType CARD_TYPE = CardType.CREDIT;
  private static final String FIRST_NAME = "FirstName";
  private static final String LAST_NAME = "LastName";
  private static final String SYMBOL_CODE = "Code";
  private static final String SYMBOL_AMOUNT = "126362.83";
  private static final String REPRESENTATIVE_ID = "RepresentativeId";

  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  private final OperationMapper mapper = new OperationMapperImpl();

  @Test
  public void testAsDto_OperationPackage() throws Exception {
    // given
    OperationPackage entity = getOperationPackage();

    // when
    OperationPackageDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), PACKAGE_ID.toString());
    assertEquals(dto.getStatus(), PACKAGE_STATUS.value());
    assertNotNull(dto.getToCardDeposits());
    assertFalse(dto.getToCardDeposits().isEmpty());
  }

  @Test
  public void testAsDto_OperationPackage_NullEntity() throws Exception {
    // when
    OperationPackageDto dto = mapper.asDto((OperationPackage) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_OperationTask() throws Exception {
    // given
    OperationTask entity = getOperationTask();

    // when
    OperationTaskDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), TASK_ID.toString());
    assertEquals(dto.getStatus(), TASK_STATUS.value());
    assertEquals(dto.getCreatedDate(), CREATED_DATE);
    assertEquals(dto.getChangedDate(), CHANGED_DATE);
    assertEquals(dto.getStatusChangedDate(), STATUS_CHANGED_DATE);
  }

  @Test
  public void testAsDto_OperationTask_NullEntity() throws Exception {
    // when
    OperationTaskDto dto = mapper.asDto((OperationTask) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_OperationTask_EmptyEntity() throws Exception {
    // when
    OperationTaskDto dto = mapper.asDto(new OperationTask());

    // then
    assertNotNull(dto);
    assertNull(dto.getStatus());
  }

  @Test
  public void testAsDto_Operation() throws Exception {
    // given
    Operation entity = getOperation();

    // when
    OperationDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), OPERATION_ID);
    assertEquals(dto.getStatus(), OPERATION_STATUS.value());
    assertEquals(dto.getCreatedDate(), CREATED_DATE);
    assertEquals(dto.getCommittedDate(), COMMITTED_DATE);
  }

  @Test
  public void testAsDto_Operation_NullEntity() throws Exception {
    // when
    OperationDto dto = mapper.asDto((Operation) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsEntity_CardDeposit() throws Exception {
    // given
    CardDepositDto dto = getCardDepositDto();

    // when
    OperationTaskCardDeposit entity = mapper.asEntity(dto);

    // then
    assertNotNull(entity);
    assertEquals(entity.getOvnUid(), UID);
    assertEquals(entity.getOvnNum(), Long.valueOf(NUM));
    assertEquals(entity.getOvnStatus(), STATUS);
    assertEquals(entity.getRepresentativeId(), REPRESENTATIVE_ID);
    assertEquals(entity.getRepFio(), REP_FIO);
    assertEquals(entity.getLegalEntityShortName(), LEGAL_ENTITY_SHORT_NAME);
    assertEquals(entity.getAmount(), new BigDecimal(AMOUNT));
    assertEquals(entity.getInn(), INN);
    assertEquals(entity.getKpp(), KPP);
    assertEquals(entity.getAccountId(), ACCOUNT_ID);
    assertEquals(entity.getSenderAccountId(), SENDER_ACCOUNT_ID);
    assertEquals(entity.getSenderBank(), SENDER_BANK);
    assertEquals(entity.getSenderBankBic(), SENDER_BANK_BIC);
    assertEquals(entity.getRecipientAccountId(), RECIPIENT_ACCOUNT_ID);
    assertEquals(entity.getRecipientBank(), RECIPIENT_BANK);
    assertEquals(entity.getRecipientBankBic(), RECIPIENT_BANK_BIC);
    assertEquals(entity.getSource(), SOURCE);
    assertEquals(entity.isClientTypeFk(), CLIENT_TYPE_FK);
    assertEquals(entity.getOrganisationNameFk(), ORGANISATION_NAME_FK);
    assertEquals(entity.getPersonalAccountId(), PERSONAL_ACCOUNT_ID);
    assertEquals(entity.getCurrencyType(), CURRENCY_TYPE);
    assertEquals(entity.getCreatedDate(), longDateFormat.parse(CREATED_DATE));
    assertNotNull(entity.getCard());
    assertNotNull(entity.getCashSymbols());
    assertFalse(entity.getCashSymbols().isEmpty());

    Card cardEntity = entity.getCard();
    assertEquals(cardEntity.getNumber(), NUMBER);
    assertEquals(cardEntity.getExpiryDate(), shortDateFormat.parse(EXPIRY_DATE));
    assertEquals(cardEntity.getIssuingNetworkCode(), NETWORK_CODE);
    assertEquals(cardEntity.getType(), CARD_TYPE);
    assertEquals(cardEntity.getOwnerFirstName(), FIRST_NAME);
    assertEquals(cardEntity.getOwnerLastName(), LAST_NAME);

    CashSymbol cashSymbolEntity = entity.getCashSymbols().get(0);
    assertEquals(cashSymbolEntity.getCode(), SYMBOL_CODE);
    assertEquals(cashSymbolEntity.getAmount(), new BigDecimal(SYMBOL_AMOUNT));
  }

  @Test
  public void testAsEntity_CardDeposit_WrongCreatedDate() throws Exception {
    // given
    CardDepositDto dto = getCardDepositDto();
    dto.setCreatedDate("Wrong format");

    // when
    OperationTaskCardDeposit entity = mapper.asEntity(dto);

    // then
    assertNotNull(entity);
    assertNull(entity.getCreatedDate());
  }

  @Test
  public void testAsEntity_CardDeposit_NullDto() throws Exception {
    // when
    OperationTaskCardDeposit entity = mapper.asEntity((CardDepositDto) null);

    // then
    assertNull(entity);
  }

  @Test
  public void testAsEntity_CardDeposit_NullCardDto() throws Exception {
    // when
    OperationTaskCardDeposit entity = mapper.asEntity(getCardDepositDto());
    entity.setCard(null);

    // then
    assertNotNull(entity);
    assertNull(entity.getCard());
  }

  @Test
  public void testAsEntity_CardDeposit_EntityCardDto() throws Exception {
    // when
    OperationTaskCardDeposit entity = mapper.asEntity(getCardDepositDto());
    entity.setCard(new Card());

    // then
    assertNotNull(entity);

    Card card = entity.getCard();
    assertNotNull(card);
    assertNull(card.getIssuingNetworkCode());
    assertNull(card.getType());
  }

  @Test
  public void testAsEntity_CardDeposit_Card_WrongCreatedDate() throws Exception {
    // given
    CardDepositDto dto = getCardDepositDto();
    dto.getCard().setExpiryDate("Wrong format");

    // when
    OperationTaskCardDeposit entity = mapper.asEntity(dto);

    // then
    assertNotNull(entity);
    assertNull(entity.getCard().getExpiryDate());
  }

  @Test
  public void testAsEntity_Long() throws Exception {
    // when
    Long entity = mapper.asEntity("123456789");

    // then
    assertNotNull(entity);
    assertEquals(entity, Long.valueOf(123456789L));
  }

  private OperationPackage getOperationPackage() throws Exception {
    OperationPackage entity = new OperationPackage();

    entity.setId(PACKAGE_ID);
    entity.setStatus(PACKAGE_STATUS);
    entity.setResponseCode(RESPONSE_CODE);
    entity.getToCardDeposits().add(getOperationTask());
    entity.setFromCardWithdraws(null);

    return entity;
  }

  private OperationTask getOperationTask() throws Exception {
    OperationTask entity = new OperationTask();

    entity.setId(TASK_ID);
    entity.setStatus(TASK_STATUS);
    entity.setResponseCode(RESPONSE_CODE);
    entity.setCreatedDate(longDateFormat.parse(CREATED_DATE));
    entity.setChangedDate(longDateFormat.parse(CHANGED_DATE));
    entity.setStatusChangedDate(longDateFormat.parse(STATUS_CHANGED_DATE));

    return entity;
  }

  private Operation getOperation() throws Exception {
    Operation entity = new Operation();

    entity.setId(OPERATION_ID);
    entity.setStatus(OPERATION_STATUS);
    entity.setCreatedDate(longDateFormat.parse(CREATED_DATE));
    entity.setCommittedDate(longDateFormat.parse(COMMITTED_DATE));

    return entity;
  }

  private CardDepositDto getCardDepositDto() {
    CardDepositDto dto = new CardDepositDto();

    dto.setId(UID);
    dto.setNum(NUM);
    dto.setStatus(STATUS.value());
    dto.setRepresentativeId(REPRESENTATIVE_ID);
    dto.setRepFio(REP_FIO);
    dto.setLegalEntityShortName(LEGAL_ENTITY_SHORT_NAME);
    dto.setAmount(AMOUNT);
    dto.setCreatedDate(CREATED_DATE);
    dto.setInn(INN);
    dto.setKpp(KPP);
    dto.setAccountId(ACCOUNT_ID);
    dto.setSenderAccountId(SENDER_ACCOUNT_ID);
    dto.setSenderBank(SENDER_BANK);
    dto.setSenderBankBic(SENDER_BANK_BIC);
    dto.setRecipientAccountId(RECIPIENT_ACCOUNT_ID);
    dto.setRecipientBank(RECIPIENT_BANK);
    dto.setRecipientBankBic(RECIPIENT_BANK_BIC);
    dto.setSource(SOURCE);
    dto.setClientTypeFk(CLIENT_TYPE_FK);
    dto.setOrganisationNameFk(ORGANISATION_NAME_FK);
    dto.setPersonalAccountId(PERSONAL_ACCOUNT_ID);
    dto.setCurrencyType(CURRENCY_TYPE);
    dto.setCard(getCreditCardDto());
    dto.setSymbols(Collections.singletonList(getCashSymbolDto()));

    return dto;
  }

  private CreditCardDto getCreditCardDto() {
    CreditCardDto dto = new CreditCardDto();

    dto.setNumber(NUMBER);
    dto.setExpiryDate(EXPIRY_DATE);
    dto.setIssuingNetworkCode(NETWORK_CODE.value());
    dto.setType(CARD_TYPE.value());
    dto.setOwnerFirstName(FIRST_NAME);
    dto.setOwnerLastName(LAST_NAME);

    return dto;
  }

  private CashSymbolDto getCashSymbolDto() {
    CashSymbolDto dto = new CashSymbolDto();

    dto.setCode(SYMBOL_CODE);
    dto.setAmount(SYMBOL_AMOUNT);

    return dto;
  }
}
