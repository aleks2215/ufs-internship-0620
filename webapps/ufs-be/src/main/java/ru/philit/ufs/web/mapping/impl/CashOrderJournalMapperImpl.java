package ru.philit.ufs.web.mapping.impl;

import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.web.dto.AccountDto;
import ru.philit.ufs.web.dto.CashOrderJournalDto;
import ru.philit.ufs.web.mapping.CashOrderJournalMapper;

@Component
public class CashOrderJournalMapperImpl extends CommonMapperImpl implements CashOrderJournalMapper {

  @Override
  public CashOrderJournalDto asDto(CashOrder in) {

    if (in == null) {
      return null;
    }
    CashOrderJournalDto out = new CashOrderJournalDto();
    out.setCashOrderId(in.getCashOrderId());
    out.setCashOrderINum(in.getCashOrderINum());
    if (in.getCashOrderStatus() != null) {
      out.setCashOrderStatus(in.getCashOrderStatus().value());
    }
    if (in.getCashOrderTypeModel() != null) {
      out.setCashOrderType(in.getCashOrderTypeModel().code());
    }
    if (in.getAmount() != null) {
      out.setAmount(in.getAmount().toString());
    }
    out.setUserLogin(in.getUserLogin());

    return out;
  }
}
