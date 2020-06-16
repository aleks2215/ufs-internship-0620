package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import org.junit.Test;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.model.entity.account.CardNetworkCode;
import ru.philit.ufs.model.entity.account.CardType;
import ru.philit.ufs.web.dto.CreditCardDto;
import ru.philit.ufs.web.mapping.PosMapper;

public class PosMapperImplTest {

  private static final String NUMBER = "Number";
  private static final String EXPIRY_DATE = "31.12.2017";
  private static final CardNetworkCode NETWORK_CODE = CardNetworkCode.MASTER_CARD;
  private static final CardType CARD_TYPE = CardType.DEBIT;
  private static final String FIRST_NAME = "FirstName";
  private static final String LAST_NAME = "LastName";

  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  private final PosMapper mapper = new PosMapperImpl();

  @Test
  public void testAsDto_Card() throws Exception {
    // given
    Card entity = getCard();

    // when
    CreditCardDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getNumber(), NUMBER);
    assertEquals(dto.getExpiryDate(), EXPIRY_DATE);
    assertEquals(dto.getIssuingNetworkCode(), NETWORK_CODE.value());
    assertEquals(dto.getType(), CARD_TYPE.value());
    assertEquals(dto.getOwnerFirstName(), FIRST_NAME);
    assertEquals(dto.getOwnerLastName(), LAST_NAME);
  }

  @Test
  public void testAsDto_Card_NullEntity() throws Exception {
    // when
    CreditCardDto dto = mapper.asDto(null);

    // then
    assertNull(dto);
  }

  private Card getCard() throws Exception {
    Card entity = new Card();

    entity.setNumber(NUMBER);
    entity.setExpiryDate(shortDateFormat.parse(EXPIRY_DATE));
    entity.setIssuingNetworkCode(NETWORK_CODE);
    entity.setType(CARD_TYPE);
    entity.setOwnerFirstName(FIRST_NAME);
    entity.setOwnerLastName(LAST_NAME);

    return entity;
  }

}