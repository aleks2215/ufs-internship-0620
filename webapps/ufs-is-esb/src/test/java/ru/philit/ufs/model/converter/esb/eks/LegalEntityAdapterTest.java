package ru.philit.ufs.model.converter.esb.eks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs.SrvLegalEntityByAccountRsMessage;

public class LegalEntityAdapterTest extends EksAdapterBaseTest {

  private static final String ACCOUNT_ID = "111";

  private SrvLegalEntityByAccountRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvLegalEntityByAccountRs();
    response.setHeaderInfo(headerInfo());
    response.setSrvLegalEntityByAccountRsMessage(new SrvLegalEntityByAccountRsMessage());
    response.getSrvLegalEntityByAccountRsMessage().setLegalEntityId("112");
    response.getSrvLegalEntityByAccountRsMessage().setLegalEntityShortName("short name");
    response.getSrvLegalEntityByAccountRsMessage().setLegalEntityFullName("Full Name");
    response.getSrvLegalEntityByAccountRsMessage().setINN("123345667899");
    response.getSrvLegalEntityByAccountRsMessage().setOGRN("496846511265");
    response.getSrvLegalEntityByAccountRsMessage().setKPP("401544125");
    response.getSrvLegalEntityByAccountRsMessage().setLegalAddress("Address1");
    response.getSrvLegalEntityByAccountRsMessage().setFactAddress("Address2");
  }

  @Test
  public void testRequestByAccount() {
    SrvLegalEntityByAccountRq request = LegalEntityAdapter.requestByAccount(ACCOUNT_ID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvLegalEntityByAccountRqMessage());
    Assert.assertEquals(request.getSrvLegalEntityByAccountRqMessage().getAccountId(), ACCOUNT_ID);
  }

  @Test
  public void testConvertSrvLegalEntityByAccountRs() {
    LegalEntity legalEntity = LegalEntityAdapter.convert(response);
    assertHeaderInfo(legalEntity);
    Assert.assertEquals(legalEntity.getId(), "112");
    Assert.assertEquals(legalEntity.getShortName(), "short name");
    Assert.assertEquals(legalEntity.getFullName(), "Full Name");
    Assert.assertEquals(legalEntity.getInn(), "123345667899");
    Assert.assertEquals(legalEntity.getOgrn(), "496846511265");
    Assert.assertEquals(legalEntity.getKpp(), "401544125");
    Assert.assertEquals(legalEntity.getLegalAddress(), "Address1");
    Assert.assertEquals(legalEntity.getFactAddress(), "Address2");
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), LegalEntity.class);
  }
}
