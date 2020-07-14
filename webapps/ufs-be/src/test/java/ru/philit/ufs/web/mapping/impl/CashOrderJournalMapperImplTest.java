package ru.philit.ufs.web.mapping.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashOrderTypeModel;
import ru.philit.ufs.web.dto.CashOrderJournalDto;

public class CashOrderJournalMapperImplTest {

  private static final String CASH_ORDER_ID = "101";
  private static final String CASH_ORDER_INUM = "50";
  private static final CashOrderStatus CASH_ORDER_STATUS = CashOrderStatus.CREATED;
  private static final CashOrderTypeModel CASH_ORDER_TYPE = CashOrderTypeModel.KO_1;
  private static final BigDecimal AMOUNT = new BigDecimal("100000");
  private static final String CREATE_DTTM = "31.05.2020 14:35:04";
  private static final String USER_LOGIN = "aleks-login";

  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");


  private final CashOrderJournalMapperImpl mapper = new CashOrderJournalMapperImpl();

  @Test
  public void testAsDto_CashOrder() throws Exception {
    // given
    CashOrder cashOrder = getCashOrder();

    // when
    CashOrderJournalDto dto = mapper.asDto(cashOrder);

    // then
    Assert.assertEquals(dto.getCashOrderId(), CASH_ORDER_ID);
    Assert.assertEquals(dto.getCashOrderINum(), CASH_ORDER_INUM);
    Assert.assertEquals(dto.getCashOrderStatus(), CASH_ORDER_STATUS.value());
    Assert.assertEquals(dto.getCashOrderType(), CASH_ORDER_TYPE.code());
    Assert.assertEquals(dto.getAmount(), AMOUNT.toString());
    Assert.assertEquals(dto.getCreatedDttm(), CREATE_DTTM);
    Assert.assertEquals(dto.getUserLogin(), USER_LOGIN);
  }

  /**
   * Получение кассового ордера для теста.
   */
  public CashOrder getCashOrder() throws Exception {
    CashOrder cashOrder = new CashOrder();
    cashOrder.setCashOrderId(CASH_ORDER_ID);
    cashOrder.setCashOrderINum(CASH_ORDER_INUM);
    cashOrder.setCashOrderStatus(CASH_ORDER_STATUS);
    cashOrder.setCashOrderTypeModel(CASH_ORDER_TYPE);
    cashOrder.setAmount(AMOUNT);
    cashOrder.setCreatedDttm(longDateFormat.parse(CREATE_DTTM));
    cashOrder.setUserLogin(USER_LOGIN);

    return cashOrder;
  }
}