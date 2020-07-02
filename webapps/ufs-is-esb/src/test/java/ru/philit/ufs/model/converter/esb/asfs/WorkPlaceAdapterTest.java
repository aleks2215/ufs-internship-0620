package ru.philit.ufs.model.converter.esb.asfs;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;

public class WorkPlaceAdapterTest extends AsfsAdapterBaseTest {

  private static final String WORKPLACE_UID = "12";
  public static final String FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";

  private SrvGetWorkPlaceInfoRs getWorkPlaceInfoRs;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    getWorkPlaceInfoRs = new SrvGetWorkPlaceInfoRs();
    getWorkPlaceInfoRs.setHeaderInfo(headerInfo(FIX_UUID));
    getWorkPlaceInfoRs.setSrvGetWorkPlaceInfoRsMessage(new SrvGetWorkPlaceInfoRsMessage());
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceType(BigInteger.valueOf(1));
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoard(true);
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().setSubbranchCode("1385930100");
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage()
        .setCashboxOnBoardDevice("530690F50B9E49A6B3EDAE2CF6B7CC4F");
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().setCashboxDeviceType("CashierPro2520-sx");
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().setCurrentCurrencyType("RUB");
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().setAmount(new BigDecimal("100000.0"));
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage()
        .setWorkPlaceLimit(new BigDecimal("500000.0"));
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage()
        .setWorkPlaceOperationTypeLimit(new WorkPlaceOperationTypeLimit());
    OperationTypeLimitItem item1 = new OperationTypeLimitItem();
    item1.setOperationCategory(BigInteger.valueOf(0));
    item1.setOperationLimit(new BigDecimal("50000.0"));
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().getWorkPlaceOperationTypeLimit()
        .getOperationTypeLimitItem().add(item1);
    OperationTypeLimitItem item2 = new OperationTypeLimitItem();
    item2.setOperationCategory(BigInteger.valueOf(1));
    item2.setOperationLimit(new BigDecimal("15000.0"));
    getWorkPlaceInfoRs.getSrvGetWorkPlaceInfoRsMessage().getWorkPlaceOperationTypeLimit()
        .getOperationTypeLimitItem().add(item2);
  }

  @Test
  public void requestByWorkPlaceUId() {
    SrvGetWorkPlaceInfoRq request = WorkPlaceAdapter.requestByWorkPlaceUId(WORKPLACE_UID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetWorkPlaceInfoRqMessage());
    Assert.assertEquals(request.getSrvGetWorkPlaceInfoRqMessage().getWorkPlaceUId(), WORKPLACE_UID);
  }

  @Test
  public void testConvertSrvGetWorkPlaceInfoRs() {
    Workplace workplace = WorkPlaceAdapter.convert(getWorkPlaceInfoRs);
    assertHeaderInfo(workplace, FIX_UUID);
    Assert.assertEquals(workplace.getType(), WorkplaceType.UWP);
    assertTrue(workplace.isCashboxOnBoard());
    Assert.assertEquals(workplace.getSubbranchCode(), "1385930100");
    Assert.assertEquals(workplace.getCashboxDeviceId(), "530690F50B9E49A6B3EDAE2CF6B7CC4F");
    Assert.assertEquals(workplace.getCashboxDeviceType(), "CashierPro2520-sx");
    Assert.assertEquals(workplace.getCurrencyType(), "RUB");
    Assert.assertEquals(workplace.getAmount(), new BigDecimal("100000.0"));
    Assert.assertEquals(workplace.getLimit(), new BigDecimal("500000.0"));
    Assert.assertNotNull(workplace.getCategoryLimits());
    Assert.assertEquals(workplace.getCategoryLimits().size(), 2);
    Assert.assertNotNull(workplace.getCategoryLimits().get(0));
    Assert.assertEquals(workplace.getCategoryLimits().get(0).getCategoryId(),
        BigInteger.valueOf(0).toString());
    Assert.assertEquals(workplace.getCategoryLimits().get(0).getLimit(),
        new BigDecimal("50000.0"));
    Assert.assertNotNull(workplace.getCategoryLimits().get(1));
    Assert.assertEquals(workplace.getCategoryLimits().get(1).getCategoryId(),
        BigInteger.valueOf(1).toString());
    Assert.assertEquals(workplace.getCategoryLimits().get(1).getLimit(),
        new BigDecimal("15000.0"));
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(getWorkPlaceInfoRs);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), Workplace.class);
  }
}