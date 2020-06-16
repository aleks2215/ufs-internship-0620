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
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs.SrvCheckWithFraudRsMessage;

public class CheckFraudAdapterTest extends EksAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-43f7-912c-4c26ca79e969";

  private AccountOperationRequest checkFraudRequest;
  private SrvCheckWithFraudRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    checkFraudRequest = new AccountOperationRequest();
    checkFraudRequest.setAccountId("111");
    checkFraudRequest.setAmount(BigDecimal.valueOf(1048.26));
    checkFraudRequest.setOperationTypeCode(OperationTypeCode.FROM_CARD_WITHDRAW);

    response = new SrvCheckWithFraudRs();
    response.setHeaderInfo(headerInfo(FIX_UUID));
    response.setSrvCheckWithFraudRsMessage(new SrvCheckWithFraudRsMessage());
    response.getSrvCheckWithFraudRsMessage().setIsWithFraud(true);
    response.getSrvCheckWithFraudRsMessage().setResponseCode("1");
  }

  @Test
  public void testRequestByParams() {
    SrvCheckWithFraudRq request = CheckFraudAdapter.requestByParams(checkFraudRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCheckWithFraudRqMessage());
    Assert.assertEquals(request.getSrvCheckWithFraudRqMessage().getAccountId(), "111");
    Assert.assertEquals(request.getSrvCheckWithFraudRqMessage().getAmount(),
        BigDecimal.valueOf(1048.26));
    Assert.assertEquals(request.getSrvCheckWithFraudRqMessage().getOperationType(),
        OperTypeLabel.FROM_CARD_WITHDRAW);
  }

  @Test
  public void testConvertSrvCountCommissionRs() {
    ExternalEntityContainer<Boolean> container = CheckFraudAdapter.convert(response);
    assertHeaderInfo(container, FIX_UUID);
    Assert.assertEquals(container.getData(), true);
    Assert.assertEquals(container.getResponseCode(), "1");
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityContainer.class);
    Assert.assertNotNull(((ExternalEntityContainer) externalEntity).getData());
    Assert.assertEquals(((ExternalEntityContainer) externalEntity).getData().getClass(),
        Boolean.class);
  }
}
