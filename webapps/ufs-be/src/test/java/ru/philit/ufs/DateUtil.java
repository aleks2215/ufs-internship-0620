package ru.philit.ufs;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {
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
