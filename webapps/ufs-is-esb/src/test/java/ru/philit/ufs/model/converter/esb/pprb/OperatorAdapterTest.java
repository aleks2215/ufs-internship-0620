package ru.philit.ufs.model.converter.esb.pprb;

import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage.OperatorInfoItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage.OperatorInfoItem.SubbranchInfoItem;
import ru.philit.ufs.model.entity.user.Operator;

public class OperatorAdapterTest extends PprbAdapterBaseTest {

  private SrvGetOperatorInfoByUserRs response;
  private final String user = "User";

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvGetOperatorInfoByUserRs();
    response.setHeaderInfo(headerInfo());
    response.setSrvGetOperatorInfoByUserRsMessage(new SrvGetOperatorInfoByUserRsMessage());
    OperatorInfoItem operatorInfoItem = new OperatorInfoItem();
    operatorInfoItem.setWorkPlaceUId("NHJ786JHKJ979");
    operatorInfoItem.setOperatorFullName("Петров Петр Петрович");
    SubbranchInfoItem subbranchInfoItem = new SubbranchInfoItem();
    subbranchInfoItem.setTBCode("TBCode");
    subbranchInfoItem.setGOSBCode("GOSBCode");
    subbranchInfoItem.setOSBCode("OSBCode");
    subbranchInfoItem.setVSPCode("VSPCode");
    subbranchInfoItem.setSubbranchCode("SubbranchCode");
    subbranchInfoItem.setSubbranchLevel(BigInteger.TEN);
    subbranchInfoItem.setINN("0278000222");
    subbranchInfoItem.setBIC("044525225");
    subbranchInfoItem.setBankName("ПАО \"Сбербанк\"");
    operatorInfoItem.setSubbranchInfoItem(subbranchInfoItem);
    operatorInfoItem.setFirstName("Петр");
    operatorInfoItem.setLastName("Петров");
    operatorInfoItem.setPatronymic("Петрович");
    operatorInfoItem.setEmail("petrov@nodomen.nono");
    operatorInfoItem.setPhoneNumMobile("+7-495-900-00-00");
    operatorInfoItem.setPhoneNumWork("+7-495-900-00-00");
    response.getSrvGetOperatorInfoByUserRsMessage().setOperatorInfoItem(operatorInfoItem);
  }

  @Test
  public void testRequestByUser() {
    SrvGetOperatorInfoByUserRq request = OperatorAdapter.requestByUser(user);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetOperatorInfoByUserRqMessage());
    Assert.assertEquals(request.getSrvGetOperatorInfoByUserRqMessage().getUserLogin(), user);
  }

  @Test
  public void testConvertSrvGetOperatorInfoByUserRs() {
    Operator operator = OperatorAdapter.convert(response);
    assertHeaderInfo(operator);
    OperatorInfoItem operatorInfoItem = response.getSrvGetOperatorInfoByUserRsMessage()
        .getOperatorInfoItem();
    Assert.assertEquals(operator.getWorkplaceId(), operatorInfoItem.getWorkPlaceUId());
    Assert.assertEquals(operator.getOperatorFullName(), operatorInfoItem.getOperatorFullName());
    Assert.assertEquals(operator.getFirstName(), operatorInfoItem.getFirstName());
    Assert.assertEquals(operator.getLastName(), operatorInfoItem.getLastName());
    Assert.assertEquals(operator.getPatronymic(), operatorInfoItem.getPatronymic());
    Assert.assertEquals(operator.getEmail(), operatorInfoItem.getEmail());
    Assert.assertEquals(operator.getPhoneNumMobile(), operatorInfoItem.getPhoneNumMobile());
    Assert.assertEquals(operator.getPhoneNumWork(), operatorInfoItem.getPhoneNumWork());

    SubbranchInfoItem subbranchInfoItem = operatorInfoItem.getSubbranchInfoItem();
    Assert.assertNotNull(operator.getSubbranch());
    Assert.assertEquals(operator.getSubbranch().getTbCode(), subbranchInfoItem.getTBCode());
    Assert.assertEquals(operator.getSubbranch().getGosbCode(), subbranchInfoItem.getGOSBCode());
    Assert.assertEquals(operator.getSubbranch().getOsbCode(), subbranchInfoItem.getOSBCode());
    Assert.assertEquals(operator.getSubbranch().getVspCode(), subbranchInfoItem.getVSPCode());
    Assert.assertEquals(operator.getSubbranch().getSubbranchCode(),
        subbranchInfoItem.getSubbranchCode());
    Assert.assertEquals(operator.getSubbranch().getLevel(),
        Long.valueOf(subbranchInfoItem.getSubbranchLevel().longValue()));
    Assert.assertEquals(operator.getSubbranch().getInn(), subbranchInfoItem.getINN());
    Assert.assertEquals(operator.getSubbranch().getBic(), subbranchInfoItem.getBIC());
    Assert.assertEquals(operator.getSubbranch().getBankName(), subbranchInfoItem.getBankName());
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), Operator.class);
  }
}
