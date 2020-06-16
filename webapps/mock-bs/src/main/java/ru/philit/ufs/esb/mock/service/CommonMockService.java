package ru.philit.ufs.esb.mock.service;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый класс сервисов обработки запросов к Мастер-системам.
 */
public abstract class CommonMockService {

  private static final Logger logger = LoggerFactory.getLogger(CommonMockService.class);

  protected static XMLGregorianCalendar xmlCalendar(int year, int month, int day, int hour,
      int minute) {
    try {
      return DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(new GregorianCalendar(year, month, day, hour, minute));
    } catch (DatatypeConfigurationException e) {
      logger.error("XMLGregorianCalendar building error", e);
      return null;
    }
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
