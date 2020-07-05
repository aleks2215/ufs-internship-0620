package ru.philit.ufs.model.cache.mock;

import org.junit.Assert;
import org.junit.Test;
import ru.philit.ufs.model.entity.oper.CashOrder;

public class MockCacheOrderTest {

  @Test
  public void testGetCashOrder() {
    // when
    CashOrder cashOrder = MockCacheOrder.getCashOrder();

    // then
    Assert.assertNotNull(cashOrder);
  }
}