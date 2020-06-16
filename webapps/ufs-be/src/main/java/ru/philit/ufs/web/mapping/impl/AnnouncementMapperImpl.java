package ru.philit.ufs.web.mapping.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.web.dto.AnnouncementDto;
import ru.philit.ufs.web.dto.CashSymbolDto;
import ru.philit.ufs.web.mapping.AnnouncementMapper;

@Component
public class AnnouncementMapperImpl extends CommonMapperImpl implements AnnouncementMapper {

  @Override
  public AnnouncementDto asDto(CashDepositAnnouncement in) {
    if (in == null) {
      return null;
    }
    AnnouncementDto out = new AnnouncementDto();

    out.setId(in.getUid());
    out.setNum(asDto(in.getNum()));
    if (in.getStatus() != null) {
      out.setStatus(in.getStatus().value());
    }
    out.setRepFio(in.getRepFio());
    out.setLegalEntityShortName(in.getLegalEntityShortName());
    out.setAmount(asDto(in.getAmount()));
    out.setCreatedDate(asLongDateDto(in.getCreatedDate()));
    out.setInn(in.getInn());
    out.setKpp(in.getKpp());
    out.setAccountId(in.getAccountId());
    out.setSenderAccountId(in.getSenderAccountId());
    out.setSenderBank(in.getSenderBank());
    out.setSenderBankBic(in.getSenderBankBic());
    out.setRecipientAccountId(in.getRecipientAccountId());
    out.setRecipientBank(in.getRecipientBank());
    out.setRecipientBankBic(in.getRecipientBankBic());
    out.setSource(in.getSource());
    out.setClientTypeFk(in.isClientTypeFk());
    out.setOrganisationNameFk(in.getOrganisationNameFk());
    out.setPersonalAccountId(in.getPersonalAccountId());
    out.setCurrencyType(in.getCurrencyType());
    out.setSymbols(asSymbolDto(in.getCashSymbols()));

    return out;
  }

  @Override
  public String asDto(BigDecimal in) {
    return super.asDto(in);
  }

  private CashSymbolDto asDto(CashSymbol in) {
    CashSymbolDto out = new CashSymbolDto();

    out.setCode(in.getCode());
    out.setAmount(asDto(in.getAmount()));

    return out;
  }

  @Override
  public List<AnnouncementDto> asAnnouncementDto(List<CashDepositAnnouncement> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<AnnouncementDto> out = new ArrayList<>();

    for (CashDepositAnnouncement announcement : in) {
      out.add(asDto(announcement));
    }
    return out;
  }

  private List<CashSymbolDto> asSymbolDto(List<CashSymbol> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<CashSymbolDto> out = new ArrayList<>();

    for (CashSymbol symbol : in) {
      out.add(asDto(symbol));
    }
    return out;
  }
}
