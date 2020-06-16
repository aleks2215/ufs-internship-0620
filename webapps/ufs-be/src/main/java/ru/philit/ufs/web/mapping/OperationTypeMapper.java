package ru.philit.ufs.web.mapping;

import java.util.List;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.web.dto.CashSymbolDto;
import ru.philit.ufs.web.dto.OperationTypeDto;

/**
 * Конвертер для типов операций.
 */
public interface OperationTypeMapper {

  OperationTypeDto asDto(OperationType in);

  List<String> asFavouriteDto(List<OperationTypeFavourite> in);

  List<CashSymbolDto> asSymbolDto(List<CashSymbol> in);

  List<OperationTypeFavourite> asEntity(List<String> in);

}
