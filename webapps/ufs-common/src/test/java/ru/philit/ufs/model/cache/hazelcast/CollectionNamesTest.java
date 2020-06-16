package ru.philit.ufs.model.cache.hazelcast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Test;

public class CollectionNamesTest {

  @Test
  public void testPrivateConstructor() throws Exception {
    Constructor constructor = CollectionNames.class.getDeclaredConstructor();
    Assert.assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));
    constructor.setAccessible(true);
    constructor.newInstance();
  }

}
