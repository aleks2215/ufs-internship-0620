package ru.philit.ufs.model.converter.esb;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый класс для конверетров Транспортных объектов в Сущности.
 */
public abstract class CommonAdapter {

  protected static final Logger logger = LoggerFactory.getLogger(CommonAdapter.class);

  protected static Long longValue(Number number) {
    return (number != null) ? number.longValue() : null;
  }

  protected static BigInteger bigIntegerValue(Long number) {
    return (number != null) ? BigInteger.valueOf(number) : null;
  }

  protected static XMLGregorianCalendar xmlCalendar(Date date) {
    if (date == null) {
      return null;
    }
    try {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    } catch (DatatypeConfigurationException e) {
      logger.error("XMLGregorianCalendar building error", e);
      return null;
    }
  }

  protected static Date date(XMLGregorianCalendar xmlCalendar) {
    return (xmlCalendar != null) ? xmlCalendar.toGregorianCalendar().getTime() : null;
  }

}
