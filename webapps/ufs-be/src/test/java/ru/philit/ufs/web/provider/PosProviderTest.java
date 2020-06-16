package ru.philit.ufs.web.provider;

import static org.junit.Assert.assertNotNull;
import static ru.philit.ufs.web.provider.PosProvider.DEFAULT_CARD_NUMBER;
import static ru.philit.ufs.web.provider.PosProvider.DEFAULT_CARD_PIN;

import org.junit.Test;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.mock.MockCacheImpl;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.web.exception.InvalidDataException;

public class PosProviderTest {

  private final MockCache mockCache = new MockCacheImpl();
  private final PosProvider provider = new PosProvider(mockCache);

  @Test
  public void testVerifyCreditCard() throws Exception {
    // when
    Card card = provider.verifyCreditCard(DEFAULT_CARD_NUMBER, DEFAULT_CARD_PIN);

    // then
    assertNotNull(card);
  }

  @Test(expected = InvalidDataException.class)
  public void testVerifyCreditCard_NullNumber() throws Exception {
    // when
    provider.verifyCreditCard(null, DEFAULT_CARD_PIN);
  }

  @Test(expected = InvalidDataException.class)
  public void testVerifyCreditCard_WrongNumber() throws Exception {
    // when
    provider.verifyCreditCard(DEFAULT_CARD_NUMBER + "?", DEFAULT_CARD_PIN);
  }

  @Test(expected = InvalidDataException.class)
  public void testVerifyCreditCard_NullPin() throws Exception {
    // when
    provider.verifyCreditCard(DEFAULT_CARD_NUMBER, null);
  }

  @Test(expected = InvalidDataException.class)
  public void testVerifyCreditCard_WrongPin() throws Exception {
    // when
    provider.verifyCreditCard(DEFAULT_CARD_NUMBER, DEFAULT_CARD_PIN + "?");
  }

}