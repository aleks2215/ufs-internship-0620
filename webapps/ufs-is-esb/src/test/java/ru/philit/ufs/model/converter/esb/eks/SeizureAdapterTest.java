package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs.SrvSeizureByAccountRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs.SrvSeizureByAccountRsMessage.SrvSeizureByAccountItem;

public class SeizureAdapterTest extends EksAdapterBaseTest {

  private static final String ACCOUNT_ID = "111";
  private static final String FIX_UUID = "a55ed415-3976-41f7-912c-4c16ca79e969";

  private SrvSeizureByAccountRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvSeizureByAccountRs();
    response.setHeaderInfo(headerInfo(FIX_UUID));
    response.setSrvSeizureByAccountRsMessage(new SrvSeizureByAccountRsMessage());
    SrvSeizureByAccountItem seizure1 = new SrvSeizureByAccountItem();
    seizure1.setSeizureSequenceId(BigInteger.valueOf(1001));
    seizure1.setSeizureType(BigInteger.valueOf(3));
    seizure1.setSeizureReasonDesc("Voluntarily");
    seizure1.setDateFrom(xmlCalendar(2017, 4, 10, 0, 0));
    seizure1.setDateTo(xmlCalendar(2017, 4, 20, 15, 30));
    seizure1.setAmount(BigDecimal.valueOf(100));
    seizure1.setInitiatorShortName("Serious organization");
    response.getSrvSeizureByAccountRsMessage().getSrvSeizureByAccountItem().add(seizure1);
    SrvSeizureByAccountItem seizure2 = new SrvSeizureByAccountItem();
    seizure2.setSeizureSequenceId(BigInteger.valueOf(1002));
    seizure2.setSeizureType(BigInteger.valueOf(4));
    seizure2.setSeizureReasonDesc("Voluntarily");
    seizure2.setDateFrom(xmlCalendar(2017, 4, 15, 0, 0));
    seizure2.setDateTo(xmlCalendar(2017, 4, 25, 12, 0));
    seizure2.setAmount(BigDecimal.valueOf(200));
    seizure2.setInitiatorShortName("Serious organization2");
    response.getSrvSeizureByAccountRsMessage().getSrvSeizureByAccountItem().add(seizure2);
  }

  @Test
  public void testRequestByAccount() {
    SrvSeizureByAccountRq request = SeizureAdapter.requestByAccount(ACCOUNT_ID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvSeizureByAccountRqMessage());
    Assert.assertEquals(request.getSrvSeizureByAccountRqMessage().getAccountId(), ACCOUNT_ID);
  }

  @Test
  public void testConvertSrvSeizureByAccountRs() {
    ExternalEntityList<Seizure> seizureList = SeizureAdapter.convert(response);
    assertHeaderInfo(seizureList, FIX_UUID);
    Assert.assertEquals(seizureList.getItems().size(), 2);
    Assert.assertEquals(seizureList.getItems().get(0).getId(), Long.valueOf(1001));
    Assert.assertEquals(seizureList.getItems().get(0).getType(), Long.valueOf(3));
    Assert.assertEquals(seizureList.getItems().get(0).getReason(), "Voluntarily");
    Assert.assertEquals(seizureList.getItems().get(0).getFromDate(), date(2017, 4, 10, 0, 0));
    Assert.assertEquals(seizureList.getItems().get(0).getToDate(), date(2017, 4, 20, 15, 30));
    Assert.assertEquals(seizureList.getItems().get(0).getAmount(), BigDecimal.valueOf(100));
    Assert.assertEquals(seizureList.getItems().get(0).getInitiatorShortName(),
        "Serious organization");
    Assert.assertEquals(seizureList.getItems().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(seizureList.getItems().get(0).getReceiveDate());
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity).getItems().size(), 2);
    Assert.assertEquals(((ExternalEntityList) externalEntity).getItems().get(0).getClass(),
        Seizure.class);
  }
}
