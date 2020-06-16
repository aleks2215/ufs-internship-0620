package ru.philit.ufs.web.mapping.impl;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.web.dto.CashSymbolDto;
import ru.philit.ufs.web.dto.OperationTypeDto;
import ru.philit.ufs.web.mapping.OperationTypeMapper;

@Component
public class OperationTypeMapperImpl extends CommonMapperImpl implements OperationTypeMapper {

  private final Comparator<OperationTypeFavourite> favouriteComparator =
      new Comparator<OperationTypeFavourite>() {
    @Override
    public int compare(OperationTypeFavourite o1, OperationTypeFavourite o2) {
      return ComparisonChain.start()
          .compare(o1.getIndex(), o2.getIndex(), Ordering.natural().nullsLast())
          .compare(o1.getId(), o2.getId())
          .result();
    }
  };

  @Override
  public OperationTypeDto asDto(OperationType in) {
    if (in == null) {
      return null;
    }
    OperationTypeDto out = new OperationTypeDto();

    out.setId(in.getId());
    out.setName(in.getName());
    if (in.getCode() != null) {
      out.setCode(in.getCode().code());
    }
    out.setCategoryId(asDto(in.getCategoryId()));
    out.setCategoryName(in.getCategoryName());
    out.setEnabled(in.isEnabled());

    return out;
  }

  private CashSymbolDto asDto(CashSymbol in) {
    CashSymbolDto out = new CashSymbolDto();

    out.setCode(in.getCode());
    out.setDesc(in.getDescription());

    return out;
  }

  @Override
  public List<String> asFavouriteDto(List<OperationTypeFavourite> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    if (in.size() > 1) {
      Collections.sort(in, favouriteComparator);
    }
    List<String> out = new ArrayList<>();

    for (OperationTypeFavourite favourite : in) {
      out.add(String.valueOf(favourite.getId()));
    }
    return out;
  }

  @Override
  public List<CashSymbolDto> asSymbolDto(List<CashSymbol> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<CashSymbolDto> out = new ArrayList<>();

    for (CashSymbol cashSymbol : in) {
      out.add(asDto(cashSymbol));
    }
    return out;
  }

  @Override
  public List<OperationTypeFavourite> asEntity(List<String> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<OperationTypeFavourite> out = new ArrayList<>();

    for (int i = 0; i < in.size(); i++) {
      out.add(new OperationTypeFavourite(asLongEntity(in.get(i)), i));
    }
    return out;
  }
}
