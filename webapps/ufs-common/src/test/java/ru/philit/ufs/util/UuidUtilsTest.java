package ru.philit.ufs.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Test;

public class UuidUtilsTest {

  @Test
  public void testGetRandomUuid() throws Exception {
    String uuid = UuidUtils.getRandomUuid();
    Assert.assertNotNull(uuid);
    Assert.assertEquals(uuid.length(), 32);
  }

  @Test
  public void testPrivateConstructor() throws Exception {
    Constructor constructor = UuidUtils.class.getDeclaredConstructor();
    Assert.assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));
    constructor.setAccessible(true);
    constructor.newInstance();
  }

}
