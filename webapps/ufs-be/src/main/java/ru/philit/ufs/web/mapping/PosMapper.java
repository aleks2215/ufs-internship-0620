package ru.philit.ufs.web.mapping;

import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.web.dto.CreditCardDto;

/**
 * Конвертер для объектов POS-терминала.
 */
public interface PosMapper {

  CreditCardDto asDto(Card in);

}
