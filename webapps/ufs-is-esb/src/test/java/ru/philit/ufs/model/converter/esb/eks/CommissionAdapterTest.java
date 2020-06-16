package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.eks.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs.SrvCountCommissionRsMessage;

public class CommissionAdapterTest extends EksAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-42f7-912c-4c26ca79e969";

  private AccountOperationRequest commissionRequest;
  private SrvCountCommissionRs response1;
  private SrvCountCommissionRs response2;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    commissionRequest = new AccountOperationRequest();
    commissionRequest.setAccountId("111");
    commissionRequest.setAmount(BigDecimal.valueOf(1048.26));
    commissionRequest.setOperationTypeCode(OperationTypeCode.FROM_CARD_WITHDRAW);

    response1 = new SrvCountCommissionRs();
    response1.setHeaderInfo(headerInfo(FIX_UUID));
    response1.setSrvCountCommissionRsMessage(new SrvCountCommissionRsMessage());
    response1.getSrvCountCommissionRsMessage().setIsCommission(true);
    response1.getSrvCountCommissionRsMessage().setCommissionAmount(BigDecimal.valueOf(12.5));

    response2 = new SrvCountCommissionRs();
    response2.setHeaderInfo(headerInfo(FIX_UUID));
    response2.setSrvCountCommissionRsMessage(new SrvCountCommissionRsMessage());
    response2.getSrvCountCommissionRsMessage().setIsCommission(false);
    response2.getSrvCountCommissionRsMessage().setCommissionAmount(BigDecimal.valueOf(10));
  }

  @Test
  public void testRequestByParams() {
    SrvCountCommissionRq request = CommissionAdapter.requestByParams(commissionRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCountCommissionRqMessage());
    Assert.assertEquals(request.getSrvCountCommissionRqMessage().getAccountId(), "111");
    Assert.assertEquals(request.getSrvCountCommissionRqMessage().getAmount(),
        BigDecimal.valueOf(1048.26));
    Assert.assertEquals(request.getSrvCountCommissionRqMessage().getOperationType(),
        OperTypeLabel.FROM_CARD_WITHDRAW);
  }

  @Test
  public void testConvertSrvCountCommissionRs() {
    ExternalEntityContainer<BigDecimal> container1 = CommissionAdapter.convert(response1);
    assertHeaderInfo(container1, FIX_UUID);
    Assert.assertEquals(container1.getData(), BigDecimal.valueOf(12.5));

    ExternalEntityContainer<BigDecimal> container2 = CommissionAdapter.convert(response2);
    assertHeaderInfo(container2, FIX_UUID);
    Assert.assertNull(container2.getData());
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response1);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityContainer.class);
  }
}
