package ru.philit.ufs.web.mapping.impl;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.web.dto.IdentityDocumentDto;
import ru.philit.ufs.web.dto.SubbranchDto;

public abstract class CommonMapperImpl {

  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  protected IdentityDocumentDto asDto(IdentityDocument in) {
    if (in == null) {
      return null;
    }
    IdentityDocumentDto out = new IdentityDocumentDto();

    if (in.getType() != null) {
      out.setType(in.getType().value());
    }
    out.setSeries(in.getSeries());
    out.setNumber(in.getNumber());
    out.setIssuedBy(in.getIssuedBy());
    out.setIssuedDate(asShortDateDto(in.getIssuedDate()));

    return out;
  }

  protected SubbranchDto asDto(Subbranch in) {
    if (in == null) {
      return null;
    }
    SubbranchDto out = new SubbranchDto();

    out.setTbCode(in.getTbCode());
    out.setGosbCode(in.getGosbCode());
    out.setOsbCode(in.getOsbCode());
    out.setVspCode(in.getVspCode());
    out.setCode(in.getSubbranchCode());
    out.setLevel(in.getLevel());
    out.setInn(in.getInn());
    out.setBic(in.getBic());
    out.setBankName(in.getBankName());

    return out;
  }

  protected String asDto(Number in) {
    return (in == null) ? null : in.toString();
  }

  protected String asDto(BigDecimal in) {
    return (in == null) ? null : in.setScale(2, HALF_UP).stripTrailingZeros().toPlainString();
  }

  protected String asShortDateDto(Date in) {
    return (in == null) ? null : shortDateFormat.format(in);
  }

  protected String asLongDateDto(Date in) {
    return (in == null) ? null : longDateFormat.format(in);
  }

  protected IdentityDocumentDto asDocumentDto(List<IdentityDocument> in) {
    if (CollectionUtils.isEmpty(in)) {
      return null;
    }
    if (in.size() > 1) {
      Collections.sort(in);
    }
    return asDto(in.get(0));
  }

  protected BigDecimal asDecimalEntity(String in) {
    return (in == null) ? null : new BigDecimal(in);
  }

  protected Long asLongEntity(String in) {
    return (in == null) ? null : Long.parseLong(in);
  }

  protected Date asShortDateEntity(String in) throws ParseException {
    return (in == null) ? null : shortDateFormat.parse(in);
  }

  protected Date asLongDateEntity(String in) throws ParseException {
    return (in == null) ? null : longDateFormat.parse(in);
  }

}
