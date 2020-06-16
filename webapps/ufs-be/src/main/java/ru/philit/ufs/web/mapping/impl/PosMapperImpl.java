package ru.philit.ufs.web.mapping.impl;

import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.web.dto.CreditCardDto;
import ru.philit.ufs.web.mapping.PosMapper;

@Component
public class PosMapperImpl extends CommonMapperImpl implements PosMapper {

  @Override
  public CreditCardDto asDto(Card in) {
    if (in == null) {
      return null;
    }
    CreditCardDto out = new CreditCardDto();

    out.setNumber(in.getNumber());
    out.setExpiryDate(asShortDateDto(in.getExpiryDate()));
    if (in.getIssuingNetworkCode() != null) {
      out.setIssuingNetworkCode(in.getIssuingNetworkCode().value());
    }
    if (in.getType() != null) {
      out.setType(in.getType().value());
    }
    out.setOwnerFirstName(in.getOwnerFirstName());
    out.setOwnerLastName(in.getOwnerLastName());

    return out;
  }
}
