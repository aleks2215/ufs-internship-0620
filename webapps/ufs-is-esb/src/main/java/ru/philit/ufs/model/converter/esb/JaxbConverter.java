package ru.philit.ufs.model.converter.esb;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Конвертер объектов, передаваемых по КСШ.
 */
public class JaxbConverter {

  private final String contextPath;

  public JaxbConverter(String contextPath) {
    this.contextPath = contextPath;
  }

  /**
   * Преобразует транспортный объект в строку xml формата.
   */
  public String getXml(Object object) throws JAXBException {
    if (object == null) {
      throw new JAXBException("Can not convert null to XML");
    }
    JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
    Marshaller marshaller = jaxbContext.createMarshaller();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    marshaller.marshal(object, outputStream);
    return new String(outputStream.toByteArray(), Charset.forName("UTF-8"));
  }

  /**
   * Преобразует строку xml формата в транспортный объект из заданного пакета.
   */
  public Object getObject(String xml) throws JAXBException {
    if (xml == null || xml.isEmpty()) {
      throw new JAXBException("Can not convert empty XML to object");
    }
    JAXBContext jaxbContext = JAXBContext.newInstance(contextPath);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    return unmarshaller.unmarshal(new StringReader(xml));
  }
}
