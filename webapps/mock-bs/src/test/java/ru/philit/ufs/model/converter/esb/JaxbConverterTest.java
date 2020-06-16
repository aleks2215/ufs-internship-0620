package ru.philit.ufs.model.converter.esb;

import javax.xml.bind.JAXBException;
import org.junit.Test;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRq;

public class JaxbConverterTest {

  private final JaxbConverter converter = new JaxbConverter("ru.philit.ufs.model.entity.esb.eks");

  @Test
  public void testGetXml() throws Exception {
    converter.getXml(new SrvAccountByCardNumRq());
  }

  @Test(expected = JAXBException.class)
  public void testGetXml_NullObject() throws Exception {
    converter.getXml(null);
  }

  @Test
  public void testGetObject() throws Exception {
    converter.getObject("<SrvAccountByCardNumRq></SrvAccountByCardNumRq>");
  }

  @Test(expected = JAXBException.class)
  public void testGetObject_NullXml() throws Exception {
    converter.getObject(null);
  }
}
