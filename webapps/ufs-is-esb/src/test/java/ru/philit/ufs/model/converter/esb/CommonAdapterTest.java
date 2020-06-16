package ru.philit.ufs.model.converter.esb;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class CommonAdapterTest {

  @Test
  public void testLongValue() throws Exception {
    Assert.assertNotNull(CommonAdapter.longValue(100.0));
  }

  @Test
  public void testLongValue_NullValue() throws Exception {
    Assert.assertNull(CommonAdapter.longValue(null));
  }

  @Test
  public void testBigIntegerValue() throws Exception {
    Assert.assertNotNull(CommonAdapter.bigIntegerValue(100L));
  }

  @Test
  public void testBigIntegerValue_NullValue() throws Exception {
    Assert.assertNull(CommonAdapter.bigIntegerValue(null));
  }

  @Test
  public void testXmlCalendar() throws Exception {
    Assert.assertNotNull(CommonAdapter.xmlCalendar(new Date()));
  }

  @Test
  public void testXmlCalendar_NullValue() throws Exception {
    Assert.assertNull(CommonAdapter.xmlCalendar(null));
  }

  @Test
  public void testDate() throws Exception {
    Assert.assertNotNull(CommonAdapter.date(new XMLGregorianCalendarImpl()));
  }

  @Test
  public void testDate_NullValue() throws Exception {
    Assert.assertNull(CommonAdapter.date(null));
  }
}
