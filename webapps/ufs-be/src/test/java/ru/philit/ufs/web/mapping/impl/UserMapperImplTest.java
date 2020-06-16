package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import org.junit.Test;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.SessionUser;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;
import ru.philit.ufs.web.dto.OperationTypeLimitDto;
import ru.philit.ufs.web.dto.OperatorDto;
import ru.philit.ufs.web.dto.SubbranchDto;
import ru.philit.ufs.web.dto.UserDto;
import ru.philit.ufs.web.dto.WorkplaceDto;
import ru.philit.ufs.web.mapping.UserMapper;

public class UserMapperImplTest {

  private static final String LOGIN = "Login";
  private static final String ROLE_ID = "RoleId";
  private static final String EMPLOYEE_ID = "EmployeeId";
  private static final String LAST_NAME = "LastName";
  private static final String FIRST_NAME = "FirstName";
  private static final String PATRONYMIC = "Patronymic";
  private static final String EMAIL = "Email";
  private static final String POSITION = "Position";
  private static final String SESSION_ID = "SessionId";
  private static final String TB_CODE = "TbCode";
  private static final String GOSB_CODE = "GosbCode";
  private static final String OSB_CODE = "OsbCode";
  private static final String VSP_CODE = "VspCode";
  private static final Long LEVEL = 1L;
  private static final String INN = "Inn";
  private static final String BIC = "Bic";
  private static final String BANK_NAME = "BankName";
  private static final String WORKPLACE_ID = "326346267";
  private static final WorkplaceType WORKPLACE_TYPE = WorkplaceType.CASHBOX;
  private static final boolean CASHBOX_ON_BOARD = true;
  private static final String SUBBRANCH_CODE = "1385930100";
  private static final String CASHBOX_DEVICE_ID = "530690F50B9E49A6B3EDAE2CF6B7CC4F";
  private static final String CASHBOX_DEVICE_TYPE = "CashierPro2520-sx";
  private static final String CURRENCY_TYPE = "CurrencyType";
  private static final String AMOUNT = "23671212.32";
  private static final String LIMIT = "500000.0";
  private static final String LIMIT_DTO = "500000";
  private static final String CATEGORY_ID = "0";
  private static final String TYPE_LIMIT = "30000.0";
  private static final String TYPE_LIMIT_DTO = "30000";
  private static final String FULL_NAME = "FullName";
  private static final String PHONE_NUM_MOBILE = "PhoneNumMobile";
  private static final String PHONE_NUM_WORK = "PhoneNumWork";

  private final UserMapper mapper = new UserMapperImpl();

  @Test
  public void testAsDto_SessionUser() throws Exception {
    // given
    SessionUser entity = getSessionUser();

    // when
    UserDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getLogin(), LOGIN);
    assertEquals(dto.getSessionId(), SESSION_ID);
    assertEquals(dto.getRoleId(), ROLE_ID);
    assertEquals(dto.getEmployeeId(), EMPLOYEE_ID);
    assertEquals(dto.getLastName(), LAST_NAME);
    assertEquals(dto.getFirstName(), FIRST_NAME);
    assertEquals(dto.getPatronymic(), PATRONYMIC);
    assertEquals(dto.getEmail(), EMAIL);
    assertEquals(dto.getPosition(), POSITION);
  }

  @Test
  public void testAsDto_SessionUser_NullEntity() throws Exception {
    // when
    UserDto dto = mapper.asDto((SessionUser) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Operator() throws Exception {
    // given
    Operator entity = getOperator();
    entity.setSubbranch(getSubbranch());

    // when
    OperatorDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getWorkplaceId(), WORKPLACE_ID);
    assertEquals(dto.getFullName(), FULL_NAME);
    assertEquals(dto.getLastName(), LAST_NAME);
    assertEquals(dto.getFirstName(), FIRST_NAME);
    assertEquals(dto.getPatronymic(), PATRONYMIC);
    assertEquals(dto.getEmail(), EMAIL);
    assertEquals(dto.getPhoneMobile(), PHONE_NUM_MOBILE);
    assertEquals(dto.getPhoneWork(), PHONE_NUM_WORK);

    SubbranchDto subbranchDto = dto.getSubbranch();
    assertNotNull(subbranchDto);
    assertEquals(subbranchDto.getTbCode(), TB_CODE);
    assertEquals(subbranchDto.getGosbCode(), GOSB_CODE);
    assertEquals(subbranchDto.getOsbCode(), OSB_CODE);
    assertEquals(subbranchDto.getVspCode(), VSP_CODE);
    assertEquals(subbranchDto.getCode(), SUBBRANCH_CODE);
    assertEquals(subbranchDto.getLevel(), LEVEL);
    assertEquals(subbranchDto.getInn(), INN);
    assertEquals(subbranchDto.getBic(), BIC);
    assertEquals(subbranchDto.getBankName(), BANK_NAME);
  }

  @Test
  public void testAsDto_Operator_NullEntity() throws Exception {
    // when
    OperatorDto dto = mapper.asDto((Operator) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Operator_NullSubbranchEntity() throws Exception {
    // given
    Operator entity = new Operator();

    // when
    OperatorDto dto = mapper.asDto(entity);

    // then
    assertNull(dto.getSubbranch());
  }

  @Test
  public void testAsDto_Workplace() throws Exception {
    // given
    Workplace entity = getWorkplace();

    // when
    WorkplaceDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getSubbranchCode(), SUBBRANCH_CODE);
    assertEquals(dto.getCashboxDeviceId(), CASHBOX_DEVICE_ID);
    assertEquals(dto.getCashboxDeviceType(), CASHBOX_DEVICE_TYPE);
    assertEquals(dto.getCurrencyType(), CURRENCY_TYPE);
    assertEquals(dto.getAmount(), AMOUNT);
    assertEquals(dto.getLimit(), LIMIT_DTO);
    assertNotNull(dto.getCategoryLimits());
    assertFalse(dto.getCategoryLimits().isEmpty());

    OperationTypeLimitDto limitDto = dto.getCategoryLimits().get(0);
    assertNotNull(limitDto);
    assertEquals(limitDto.getCategoryId(), CATEGORY_ID);
    assertEquals(limitDto.getLimit(), TYPE_LIMIT_DTO);
  }

  @Test
  public void testAsDto_Workplace_NullEntity() throws Exception {
    // when
    WorkplaceDto dto = mapper.asDto((Workplace) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Workplace_EmptyLimits() throws Exception {
    // given
    Workplace entity = new Workplace();
    entity.setCategoryLimits(null);

    // when
    WorkplaceDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertNotNull(dto.getCategoryLimits());
    assertTrue(dto.getCategoryLimits().isEmpty());
  }

  private SessionUser getSessionUser() {
    SessionUser entity = new SessionUser();

    entity.setLogin(LOGIN);
    entity.setRoleId(ROLE_ID);
    entity.setEmployeeId(EMPLOYEE_ID);
    entity.setLastName(LAST_NAME);
    entity.setFirstName(FIRST_NAME);
    entity.setPatronymic(PATRONYMIC);
    entity.setEmail(EMAIL);
    entity.setPosition(POSITION);
    entity.setSessionId(SESSION_ID);

    return entity;
  }

  private Operator getOperator() {
    Operator entity = new Operator();

    entity.setWorkplaceId(WORKPLACE_ID);
    entity.setSubbranch(getSubbranch());
    entity.setOperatorFullName(FULL_NAME);
    entity.setLastName(LAST_NAME);
    entity.setFirstName(FIRST_NAME);
    entity.setPatronymic(PATRONYMIC);
    entity.setEmail(EMAIL);
    entity.setPhoneNumMobile(PHONE_NUM_MOBILE);
    entity.setPhoneNumWork(PHONE_NUM_WORK);

    return entity;
  }

  private Subbranch getSubbranch() {
    Subbranch entity = new Subbranch();

    entity.setTbCode(TB_CODE);
    entity.setGosbCode(GOSB_CODE);
    entity.setOsbCode(OSB_CODE);
    entity.setVspCode(VSP_CODE);
    entity.setSubbranchCode(SUBBRANCH_CODE);
    entity.setLevel(LEVEL);
    entity.setInn(INN);
    entity.setBic(BIC);
    entity.setBankName(BANK_NAME);

    return entity;
  }

  private Workplace getWorkplace() {
    Workplace entity = new Workplace();

    entity.setId(WORKPLACE_ID);
    entity.setType(WORKPLACE_TYPE);
    entity.setCashboxOnBoard(CASHBOX_ON_BOARD);
    entity.setSubbranchCode(SUBBRANCH_CODE);
    entity.setCashboxDeviceId(CASHBOX_DEVICE_ID);
    entity.setCashboxDeviceType(CASHBOX_DEVICE_TYPE);
    entity.setCurrencyType(CURRENCY_TYPE);
    entity.setAmount(new BigDecimal(AMOUNT));
    entity.setLimit(new BigDecimal(LIMIT));
    entity.getCategoryLimits().add(getOperationTypeLimit());

    return entity;
  }

  private OperationTypeLimit getOperationTypeLimit() {
    OperationTypeLimit entity = new OperationTypeLimit();

    entity.setCategoryId(CATEGORY_ID);
    entity.setLimit(new BigDecimal(TYPE_LIMIT));

    return entity;
  }
}
