package ru.philit.ufs.model.cache;

import java.util.List;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.user.ClientInfo;

/**
 * Интерфейс доступа к кешу данных для типов операций.
 */
public interface OperationTypeCache {

  List<OperationType> getOperationTypes(ClientInfo clientInfo);

  List<CashSymbol> getCashSymbols(CashSymbolRequest request, ClientInfo clientInfo);

  List<OperationTypeFavourite> getOperationTypeFavourites(ClientInfo clientInfo);

  void saveOperationTypeFavourites(List<OperationTypeFavourite> current,
      List<OperationTypeFavourite> previous, ClientInfo clientInfo);

}
