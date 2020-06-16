package ru.philit.ufs.web.provider;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.web.exception.InvalidDataException;

@Service
public class PosProvider {

  protected static final String DEFAULT_CARD_NUMBER = "4279987965419873";
  protected static final String DEFAULT_CARD_PIN = "2210";

  private final MockCache mockCache;

  @Autowired
  public PosProvider(MockCache mockCache) {
    this.mockCache = mockCache;
  }

  /**
   * Верификация карты.
   *
   * @param cardNumber номер карты
   * @param cardPin введённый пин-код
   * @return данные о карте при успешной верификации, сообщение об ошибке иначе
   */
  public Card verifyCreditCard(String cardNumber, String cardPin) {
    if (isEmpty(cardNumber) || !cardNumber.equals(DEFAULT_CARD_NUMBER)) {
      throw new InvalidDataException("Карта не найдена. Повторите попытку ввода");
    }
    if (isEmpty(cardPin) || !cardPin.equals(DEFAULT_CARD_PIN)) {
      throw new InvalidDataException("Пин-код неверен. Повторите попытку ввода");
    }
    return mockCache.getCreditCard();
  }

}
