package ru.philit.ufs.esb.mock.service;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class CommonMockServiceTest {

  @Test
  public void testXmlCalendarByParams() throws Exception {
    Assert.assertNotNull(CommonMockService.xmlCalendar(2017, 5, 1, 12, 0));
  }

  @Test
  public void testXmlCalendarByDate() throws Exception {
    Assert.assertNotNull(CommonMockService.xmlCalendar(new Date()));
  }

  @Test
  public void testXmlCalendarByDate_Null() throws Exception {
    Assert.assertNull(CommonMockService.xmlCalendar(null));
  }
}
