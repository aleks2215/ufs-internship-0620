package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import org.junit.Test;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.oper.OperationTaskStatus;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.dto.OperationJournalDto;
import ru.philit.ufs.web.mapping.OperationJournalMapper;

public class OperationJournalMapperImplTest {

  private static final Long OPERATION_ID = 55555L;
  private static final String OPERATION_DATE = "31.05.2017 14:35:04";
  private static final String OPERATION_INN = "77700055522244";
  private static final String LEGAL_ENTITY_NAME = "Legal Entity";
  private static final BigDecimal OPERATION_AMOUNT = BigDecimal.valueOf(1400);
  private static final BigDecimal COMMISSION_AMOUNT = BigDecimal.valueOf(14.5);

  private static final String USER_LOGIN = "Sidorov_SS";
  private static final String USER_POSITION = "оператор";

  private static final String OPER_FULL_NAME = "Сидоров С.С.";
  private static final String OPER_LAST_NAME = "Сидоров";
  private static final String OPER_FIRST_NAME = "Сидор";
  private static final String OPER_PATRONYMIC = "Сидорович";
  private static final String OPER_EMAIL = "email@noname.no";
  private static final String OPER_PHONE_WORK = "+79001001010";
  private static final String OPER_PHONE_MOB = "+79002002020";

  private static final String SUBBRANCH_ID = "1234";
  private static final String TB_CODE = "13";
  private static final String GOSB_CODE = "8593";
  private static final String OSB_CODE = null;
  private static final String VSP_CODE = "1385930102";
  private static final Long SUBBRANCH_LEVEL = 1L;

  private static final String REP_ID = "BB374D51ABE946A98A6963F59767DC4F";
  private static final String REP_INN = "628726651431";
  private static final String REP_LAST_NAME = "Петров";
  private static final String REP_FIRST_NAME = "Петр";
  private static final String REP_PATRONYMIC = "Петрович";
  private static final String REP_BIRTH_DATE = "05.04.1995";
  private static final String REP_PHONE_WORK = "+7-495-900-00-00";
  private static final String REP_PHONE_MOB = "+7-495-900-00-00";
  private static final String REP_EMAIL = "petrov@nodomen.nono";
  private static final String REP_ADDRESS = "улица Кремль, г. Москва";
  private static final String REP_POST_INDEX = "101000";
  private static final String REP_PLACE_OF_BIRTH = "г. Москва Родильный дом № 14";
  private static final boolean REP_RESIDENT = true;

  private static final IdentityDocumentType REP_IDD_TYPE = IdentityDocumentType.INTERNAL_PASSPORT;
  private static final String REP_IDD_SERIES = "2005";
  private static final String REP_IDD_NUMBER = "895656";
  private static final String REP_IDD_ISSUED_BY =
      "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург";
  private static final String REP_IDD_ISSUED_DATE = "01.01.2017";

  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  private final OperationJournalMapper mapper = new OperationJournalMapperImpl();

  @Test
  public void testAsDto_OperationTaskCardDeposit() throws Exception {
    // given
    OperationTaskCardDeposit task = getOperationTaskCardDeposit();
    User user = new User(USER_LOGIN);
    user.setPosition(USER_POSITION);
    Subbranch subbranch = new Subbranch(SUBBRANCH_ID, TB_CODE, GOSB_CODE, OSB_CODE, VSP_CODE,
        VSP_CODE, SUBBRANCH_LEVEL, null, null, null, null, null, null);
    Operator operator = new Operator(null, OPER_FULL_NAME, subbranch, OPER_FIRST_NAME,
        OPER_LAST_NAME, OPER_PATRONYMIC, OPER_EMAIL, OPER_PHONE_MOB, OPER_PHONE_WORK);
    Representative representative = getRepresentative();

    // when
    OperationJournalDto dto = new OperationJournalDto()
        .withOperator(mapper.asDto(operator))
        .withUser(mapper.asDto(user))
        .withRepresentative(mapper.asDto(representative))
        .withDeposit(mapper.asDto(task))
        .withCommission(mapper.asDto(COMMISSION_AMOUNT));

    // then
    assertNotNull(dto);

    assertEquals(dto.getOperator().getSubbranch().getTbCode(), TB_CODE);
    assertEquals(dto.getOperator().getSubbranch().getOsbCode(), GOSB_CODE);
    assertEquals(dto.getOperator().getSubbranch().getVspCode(), VSP_CODE);
    assertEquals(dto.getOperator().getFullName(), OPER_FULL_NAME);
    assertEquals(dto.getOperator().getLastName(), OPER_LAST_NAME);
    assertEquals(dto.getOperator().getFirstName(), OPER_FIRST_NAME);
    assertEquals(dto.getOperator().getPatronymic(), OPER_PATRONYMIC);
    assertEquals(dto.getOperator().getEmail(), OPER_EMAIL);
    assertEquals(dto.getOperator().getPhoneMobile(), OPER_PHONE_MOB);
    assertEquals(dto.getOperator().getPhoneWork(), OPER_PHONE_WORK);
    assertEquals(dto.getUser().getPosition(), USER_POSITION);
    //assertEquals(dto.getOperation().getId(), String.valueOf(OPERATION_ID));
    //assertEquals(dto.getOperation().getTypeCode(), OperationTypeCode.TO_CARD_DEPOSIT.code());
    //assertEquals(dto.getOperation().getTypeName(), OperationTypeCode.TO_CARD_DEPOSIT.value());
    //assertEquals(dto.getOperation().getCreatedDate(), OPERATION_DATE);
    //assertEquals(dto.getOperation().getInn(), OPERATION_INN);
    //assertEquals(dto.getOperation().getLegalEntityName(), LEGAL_ENTITY_NAME);
    /*assertEquals(dto.getRepresentative().getFullName(), REP_LAST_NAME + " " + REP_FIRST_NAME + " "
        + REP_PATRONYMIC);*/
    assertEquals(dto.getRepresentative().getLastName(), REP_LAST_NAME);
    assertEquals(dto.getRepresentative().getFirstName(), REP_FIRST_NAME);
    assertEquals(dto.getRepresentative().getPatronymic(), REP_PATRONYMIC);
    assertEquals(dto.getRepresentative().getDocument().getType(), REP_IDD_TYPE.value());
    assertEquals(dto.getRepresentative().getDocument().getSeries(), REP_IDD_SERIES);
    assertEquals(dto.getRepresentative().getDocument().getNumber(), REP_IDD_NUMBER);
    assertEquals(dto.getRepresentative().getDocument().getIssuedBy(), REP_IDD_ISSUED_BY);
    assertEquals(dto.getRepresentative().getDocument().getIssuedDate(), REP_IDD_ISSUED_DATE);
    assertEquals(dto.getRepresentative().getPostcode(), REP_POST_INDEX);
    assertEquals(dto.getRepresentative().getAddress(), REP_ADDRESS);
    assertEquals(dto.getRepresentative().getInn(), REP_INN);
    assertEquals(dto.getRepresentative().isResident(), REP_RESIDENT);
    //assertEquals(dto.getOperation().getAmount(), String.valueOf(OPERATION_AMOUNT));
    assertEquals(dto.getCommission(), String.valueOf(COMMISSION_AMOUNT));
    //assertEquals(dto.getOperation().getStatus(), OPERATION_STATUS);
    //assertNull(dto.getOperation().getRollbackReason());
  }

  private OperationTaskCardDeposit getOperationTaskCardDeposit() throws Exception {
    OperationTaskCardDeposit entity = new OperationTaskCardDeposit();

    entity.setId(OPERATION_ID);
    entity.setStatus(OperationTaskStatus.COMPLETED);
    entity.setStatusChangedDate(longDateFormat.parse(OPERATION_DATE));
    entity.setInn(OPERATION_INN);
    entity.setLegalEntityShortName(LEGAL_ENTITY_NAME);
    entity.setAmount(OPERATION_AMOUNT);

    return entity;
  }

  private Representative getRepresentative() throws Exception {
    Representative entity = new Representative();

    entity.setId(REP_ID);
    entity.setInn(REP_INN);
    entity.setLastName(REP_LAST_NAME);
    entity.setFirstName(REP_FIRST_NAME);
    entity.setPatronymic(REP_PATRONYMIC);
    entity.setPhoneNumWork(REP_PHONE_WORK);
    entity.setPhoneNumMobile(REP_PHONE_MOB);
    entity.setEmail(REP_EMAIL);
    entity.setAddress(REP_ADDRESS);
    entity.setPostindex(REP_POST_INDEX);
    entity.setBirthDate(shortDateFormat.parse(REP_BIRTH_DATE));
    entity.setPlaceOfBirth(REP_PLACE_OF_BIRTH);
    entity.setResident(REP_RESIDENT);
    entity.getIdentityDocuments().add(getIdentityDocument());

    return entity;
  }

  private IdentityDocument getIdentityDocument() throws Exception {
    IdentityDocument entity = new IdentityDocument();

    entity.setType(REP_IDD_TYPE);
    entity.setSeries(REP_IDD_SERIES);
    entity.setNumber(REP_IDD_NUMBER);
    entity.setIssuedBy(REP_IDD_ISSUED_BY);
    entity.setIssuedDate(shortDateFormat.parse(REP_IDD_ISSUED_DATE));

    return entity;
  }
}
