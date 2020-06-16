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
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OvnStatus;
import ru.philit.ufs.web.dto.AnnouncementDto;
import ru.philit.ufs.web.dto.CashSymbolDto;
import ru.philit.ufs.web.mapping.AnnouncementMapper;

public class AnnouncementMapperImplTest {

  private static final String UID = "UID";
  private static final Long NUM = 123L;
  private static final OvnStatus STATUS = OvnStatus.PENDING;
  private static final String REP_FIO = "RepFio";
  private static final String LEGAL_ENTITY_SHORT_NAME = "LegalEntityShortName";
  private static final String AMOUNT = "23671212.32";
  private static final String INN = "Inn";
  private static final String ACCOUNT_ID = "AccountId";
  private static final String CARD_NUMBER = "CardNumber";
  private static final String SENDER_BANK = "SenderBank";
  private static final String SENDER_BANK_BIC = "SenderBankBic";
  private static final String RECIPIENT_BANK = "RecipientBank";
  private static final String RECIPIENT_BANK_BIC = "RecipientBankBic";
  private static final String SOURCE = "Source";
  private static final boolean CLIENT_TYPE_FK = false;
  private static final String ORGANISATION_NAME_FK = "OrganisationNameFk";
  private static final String PERSONAL_ACCOUNT_ID = "PersonalAccountId";
  private static final String CURRENCY_TYPE = "CurrencyType";
  private static final String CREATED_DATE = "01.05.2017 12:34:56";
  private static final String RESPONSE_CODE = "0";
  private static final String SYMBOL_CODE = "SymbolCode";
  private static final String SYMBOL_AMOUNT = "253";

  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  private final AnnouncementMapper mapper = new AnnouncementMapperImpl();

  @Test
  public void testAsDto_Announcement() throws Exception {
    // given
    CashDepositAnnouncement entity = getCashDepositAnnouncement();

    // when
    AnnouncementDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), UID);
    assertEquals(dto.getStatus(), STATUS.value());
    assertEquals(dto.getRepFio(), REP_FIO);
    assertEquals(dto.getLegalEntityShortName(), LEGAL_ENTITY_SHORT_NAME);
    assertEquals(dto.getAmount(), AMOUNT);
    assertEquals(dto.getCreatedDate(), CREATED_DATE);
    assertEquals(dto.getInn(), INN);
    assertEquals(dto.getAccountId(), ACCOUNT_ID);
    assertEquals(dto.getSenderBank(), SENDER_BANK);
    assertEquals(dto.getSenderBankBic(), SENDER_BANK_BIC);
    assertEquals(dto.getRecipientBank(), RECIPIENT_BANK);
    assertEquals(dto.getRecipientBankBic(), RECIPIENT_BANK_BIC);
    assertEquals(dto.getSource(), SOURCE);
    assertEquals(dto.isClientTypeFk(), CLIENT_TYPE_FK);
    assertEquals(dto.getOrganisationNameFk(), ORGANISATION_NAME_FK);
    assertEquals(dto.getPersonalAccountId(), PERSONAL_ACCOUNT_ID);
    assertEquals(dto.getCurrencyType(), CURRENCY_TYPE);
    assertNotNull(dto.getSymbols());
    assertFalse(dto.getSymbols().isEmpty());

    CashSymbolDto symbolDto = dto.getSymbols().get(0);
    assertEquals(symbolDto.getCode(), SYMBOL_CODE);
    assertEquals(symbolDto.getAmount(), SYMBOL_AMOUNT);
  }

  @Test
  public void testAsDto_Announcement_NullEntity() throws Exception {
    // when
    AnnouncementDto dto = mapper.asDto((CashDepositAnnouncement) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Announcements() throws Exception {
    // given
    List<CashDepositAnnouncement> entities = getCashDepositAnnouncements();

    // when
    List<AnnouncementDto> dtos = mapper.asAnnouncementDto(entities);

    // then
    assertNotNull(dtos);
    assertEquals(dtos.size(), entities.size());
  }

  @Test
  public void testAsDto_Announcements_NullEntity() throws Exception {
    // when
    List<AnnouncementDto> dtos = mapper.asAnnouncementDto(null);

    // then
    assertNotNull(dtos);
    assertTrue(dtos.isEmpty());
  }

  @Test
  public void testAsDto_BigDecimal() throws Exception {
    // given
    BigDecimal entity = new BigDecimal(AMOUNT);

    // when
    String dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto, AMOUNT);
  }

  @Test
  public void testAsDto_BigDecimal_NullEntity() throws Exception {
    // when
    String dto = mapper.asDto((BigDecimal) null);

    // then
    assertNull(dto);
  }

  private CashDepositAnnouncement getCashDepositAnnouncement() throws Exception {
    CashDepositAnnouncement entity = new CashDepositAnnouncement();

    entity.setUid(UID);
    entity.setNum(NUM);
    entity.setStatus(STATUS);
    entity.setRepFio(REP_FIO);
    entity.setLegalEntityShortName(LEGAL_ENTITY_SHORT_NAME);
    entity.setAmount(new BigDecimal(AMOUNT));
    entity.setInn(INN);
    entity.setAccountId(ACCOUNT_ID);
    entity.setCardNumber(CARD_NUMBER);
    entity.setSenderBank(SENDER_BANK);
    entity.setSenderBankBic(SENDER_BANK_BIC);
    entity.setRecipientBank(RECIPIENT_BANK);
    entity.setRecipientBankBic(RECIPIENT_BANK_BIC);
    entity.setSource(SOURCE);
    entity.setClientTypeFk(CLIENT_TYPE_FK);
    entity.setOrganisationNameFk(ORGANISATION_NAME_FK);
    entity.setPersonalAccountId(PERSONAL_ACCOUNT_ID);
    entity.setCurrencyType(CURRENCY_TYPE);
    entity.setCreatedDate(longDateFormat.parse(CREATED_DATE));
    entity.setResponseCode(RESPONSE_CODE);
    entity.setCashSymbols(Collections.singletonList(getCashSymbol()));

    return entity;
  }

  private CashSymbol getCashSymbol() {
    CashSymbol entity = new CashSymbol();

    entity.setCode(SYMBOL_CODE);
    entity.setAmount(new BigDecimal(SYMBOL_AMOUNT));

    return entity;
  }

  private List<CashDepositAnnouncement> getCashDepositAnnouncements() {
    List<CashDepositAnnouncement> entities = new ArrayList<>();

    for (long index = 0; index < 3; index++) {
      CashDepositAnnouncement item = new CashDepositAnnouncement();
      item.setNum(index);
      entities.add(item);
    }
    return entities;
  }
}
