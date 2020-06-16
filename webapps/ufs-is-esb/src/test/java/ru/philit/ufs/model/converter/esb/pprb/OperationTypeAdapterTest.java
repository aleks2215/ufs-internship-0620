package ru.philit.ufs.model.converter.esb.pprb;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.pprb.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs.SrvGetUserOperationsByRoleRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs.SrvGetUserOperationsByRoleRsMessage.OperationTypeItem;
import ru.philit.ufs.model.entity.oper.OperationType;

public class OperationTypeAdapterTest extends PprbAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-41f7-912c-4c17ca79e969";

  private SrvGetUserOperationsByRoleRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvGetUserOperationsByRoleRs();
    response.setHeaderInfo(headerInfo(FIX_UUID));
    response.setSrvGetUserOperationsByRoleRsMessage(new SrvGetUserOperationsByRoleRsMessage());
    OperationTypeItem operationTypeItem = new OperationTypeItem();
    operationTypeItem.setOperationTypeId("0");
    operationTypeItem.setOperationType(OperTypeLabel.TO_CARD_DEPOSIT);
    operationTypeItem.setOperationTypeName("Взнос наличных на счет корпоративной/бюджетной карты");
    operationTypeItem.setOperationCatId(BigInteger.valueOf(0));
    operationTypeItem.setOperationCatName("Операции корпоративных клиентов");
    operationTypeItem.setVisibleFlg(true);
    operationTypeItem.setEnabledFlg(false);
    response.getSrvGetUserOperationsByRoleRsMessage().getOperationTypeItem().add(operationTypeItem);
  }

  @Test
  public void testRequestByRoles() {
    List<String> roles = Arrays.asList("Role1", "Role2", "Role3");
    SrvGetUserOperationsByRoleRq request = OperationTypeAdapter.requestByRoles(roles);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetUserOperationsByRoleRqMessage());
    Assert.assertEquals(request.getSrvGetUserOperationsByRoleRqMessage().getRole().size(), 3);
    Assert.assertEquals(request.getSrvGetUserOperationsByRoleRqMessage().getRole().get(0), "Role1");
  }

  @Test
  public void testConvertSrvGetUserOperationsByRoleRs() {
    ExternalEntityList<OperationType> operationTypeList = OperationTypeAdapter.convert(response);
    assertHeaderInfo(operationTypeList, FIX_UUID);
    Assert.assertEquals(operationTypeList.getItems().size(), 1);
    Assert.assertEquals(operationTypeList.getItems().get(0).getId(), "0");
    Assert.assertEquals(operationTypeList.getItems().get(0).getCode(),
        OperationTypeCode.TO_CARD_DEPOSIT);
    Assert.assertEquals(operationTypeList.getItems().get(0).getName(),
        "Взнос наличных на счет корпоративной/бюджетной карты");
    Assert.assertEquals(operationTypeList.getItems().get(0).getCategoryId(), Long.valueOf(0));
    Assert.assertEquals(operationTypeList.getItems().get(0).getCategoryName(),
        "Операции корпоративных клиентов");
    Assert.assertEquals(operationTypeList.getItems().get(0).isVisible(), true);
    Assert.assertEquals(operationTypeList.getItems().get(0).isEnabled(), false);
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntity).getItems().get(0).getClass(),
        OperationType.class);
  }
}
