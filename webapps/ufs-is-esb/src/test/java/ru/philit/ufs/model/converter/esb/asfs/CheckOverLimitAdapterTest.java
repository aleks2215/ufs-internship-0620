package ru.philit.ufs.model.converter.esb.asfs;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.as_fs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;

public class CheckOverLimitAdapterTest extends AsfsAdapterBaseTest {

  public static final String FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";
  private SrvCheckOverLimitRs checkOverLimitRs;
  private CheckOverLimitRequest checkOverLimitRequest;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    checkOverLimitRs = new SrvCheckOverLimitRs();
    checkOverLimitRs.setHeaderInfo(headerInfo(FIX_UUID));
    checkOverLimitRs.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRsMessage());
    checkOverLimitRs.getSrvCheckOverLimitRsMessage().setResponseCode("0");
    checkOverLimitRs.getSrvCheckOverLimitRsMessage().setStatus(LimitStatusType.LIMIT_PASSED);

    checkOverLimitRequest = new CheckOverLimitRequest();
    checkOverLimitRequest.setAmount(new BigDecimal("100000"));
    checkOverLimitRequest.setTobeIncreased(true);
    checkOverLimitRequest.setUserLogin("aleks_login");
  }

  @Test
  public void testRequestCheckOverLimit() {
    SrvCheckOverLimitRq request = CheckOverLimitAdapter
        .requestCheckOverLimit(checkOverLimitRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCheckOverLimitRqMessage());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getAmount(),
        new BigDecimal("100000"));
    assertTrue(request.getSrvCheckOverLimitRqMessage().isTobeIncreased());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getUserLogin(),
        "aleks_login");
  }

  @Test
  public void testConvertSrvCheckOverLimitRs() {
    ExternalEntityContainer<Boolean> container = CheckOverLimitAdapter.convert(checkOverLimitRs);
    assertHeaderInfo(container, FIX_UUID);
    Assert.assertEquals(container.getData(), true);
    Assert.assertEquals(container.getResponseCode(), "0");
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(checkOverLimitRs);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityContainer.class);
    Assert.assertNotNull(((ExternalEntityContainer) externalEntity).getData());
    Assert.assertEquals(((ExternalEntityContainer) externalEntity).getData().getClass(),
        Boolean.class);
  }
}