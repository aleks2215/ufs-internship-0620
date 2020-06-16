package ru.philit.ufs.model.converter;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class ConverterBaseTest {

  /**
   * Возвращает XMLGregorianCalendar для заданных парамтеров даты.
   */
  public static XMLGregorianCalendar xmlCalendar(int year, int month, int day, int hour,
      int minute) {
    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(
          getCalendar(year, month, day, hour, minute)
      );
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Возвращает Date для заданных парамтеров даты.
   */
  public static Date date(int year, int month, int day, int hour,
      int minute) {
    return getCalendar(year, month, day, hour, minute).getTime();
  }

  private static GregorianCalendar getCalendar(int year, int month, int day, int hour, int minute) {
    GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute);
    calendar.setTimeZone(TimeZone.getTimeZone("GMT+03:00"));
    return calendar;
  }
}
