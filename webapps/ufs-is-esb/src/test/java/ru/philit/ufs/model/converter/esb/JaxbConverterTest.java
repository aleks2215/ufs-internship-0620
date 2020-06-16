package ru.philit.ufs.model.converter.esb;

import java.math.BigInteger;
import javax.xml.bind.JAXBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.TestData;
import ru.philit.ufs.model.converter.ConverterBaseTest;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs;

public class JaxbConverterTest extends ConverterBaseTest {

  private final JaxbConverter jaxbConverter =
      new JaxbConverter("ru.philit.ufs.model.entity.esb.eks");

  private TestData testData;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    testData = new TestData();
  }

  @Test
  public void testGetXml() throws JAXBException {
    String xml = jaxbConverter.getXml(testData.getAccountResponse());
    Assert.assertEquals(xml, TestData.ACCOUNT_RESPONSE_XML);
  }

  @Test(expected = JAXBException.class)
  public void testGetXml_NullObject() throws Exception {
    jaxbConverter.getXml(null);
  }

  @Test
  public void testGetObject() throws JAXBException {
    Object obj = jaxbConverter.getObject(TestData.ACCOUNT_RESPONSE_XML);
    Assert.assertEquals(obj.getClass(), SrvAccountByCardNumRs.class);
    SrvAccountByCardNumRs response = (SrvAccountByCardNumRs) obj;
    Assert.assertNotNull(response.getHeaderInfo());
    Assert.assertEquals(response.getHeaderInfo().getRqUID(), TestData.ACCOUNT_REQUEST_FIX_UUID);
    Assert.assertNotNull(response.getSrvAccountByCardNumRsMessage());
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getAccountId(),
        TestData.ACCOUNT_ID);
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getAccountTypeId(),
        TestData.ACCOUNT_TYPE_ID);
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getAccountStatus(),
        TestData.ACCOUNT_STATUS);
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getBankBIC(), TestData.BANK_BIC);
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getAccountancyTypeId(),
        BigInteger.valueOf(2));
    Assert.assertNotNull(response.getSrvAccountByCardNumRsMessage().getAgreementInfo());
    Assert.assertNotNull(response.getSrvAccountByCardNumRsMessage().getAgreementInfo()
        .getDateFrom());
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getAgreementInfo()
        .getDateFrom().toGregorianCalendar(), xmlCalendar(2017, 5, 1, 0, 0).toGregorianCalendar());
    Assert.assertNotNull(response.getSrvAccountByCardNumRsMessage().getBankInfo());
    Assert.assertNotNull(response.getSrvAccountByCardNumRsMessage().getBankInfo()
        .getSubbranchCode());
    Assert.assertEquals(response.getSrvAccountByCardNumRsMessage().getBankInfo()
        .getSubbranchCode(), TestData.BANK_SUBBRANCH_CODE);
  }

  @Test(expected = JAXBException.class)
  public void testGetObject_NullXml() throws Exception {
    jaxbConverter.getObject(null);
  }
}
