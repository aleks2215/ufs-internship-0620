package ru.philit.ufs.web.provider;

import java.util.Collections;
import java.util.List;
import org.javers.core.Javers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.OperationTypeCache;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.user.ClientInfo;

/**
 * Бизнес-логика работы с типами операций.
 */
@Service
public class OperationTypeProvider {

  private final OperationTypeCache cache;
  private final Javers javers;

  @Autowired
  public OperationTypeProvider(OperationTypeCache cache, Javers javers) {
    this.cache = cache;
    this.javers = javers;
  }

  /**
   * Получение типов операций.
   *
   * @param clientInfo информация о клиенте
   * @return список типов операций
   */
  public List<OperationType> getOperationTypes(ClientInfo clientInfo) {
    List<OperationType> operationTypes = cache.getOperationTypes(clientInfo);

    if (CollectionUtils.isEmpty(operationTypes)) {
      operationTypes = Collections.emptyList();
    }
    return operationTypes;
  }

  /**
   * Получение типов операций, добавленных в избранное.
   *
   * @param clientInfo информация о клиенте
   * @return список избранных типов операций
   */
  public List<OperationTypeFavourite> getFavourites(ClientInfo clientInfo) {
    List<OperationTypeFavourite> favourites = cache.getOperationTypeFavourites(clientInfo);

    if (CollectionUtils.isEmpty(favourites)) {
      favourites = Collections.emptyList();
    }
    return favourites;
  }

  /**
   * Сохранение типов операций, добавленных в избранное.
   *
   * @param favourites список избранных типов операций
   * @param clientInfo информация о клиенте
   * @return наличие необходимости сохранения
   */
  public boolean saveFavourites(List<OperationTypeFavourite> favourites, ClientInfo clientInfo) {
    List<OperationTypeFavourite> previous = getFavourites(clientInfo);

    boolean hasChanges = javers.compare(favourites, previous).hasChanges();
    if (hasChanges) {
      cache.saveOperationTypeFavourites(favourites, previous, clientInfo);
    }
    return hasChanges;
  }

  /**
   * Получение кассовых символов.
   *
   * @param code код символа для поиска
   * @param clientInfo информация о клиенте
   * @return список кассовых символов
   */
  public List<CashSymbol> getCashSymbols(String code, ClientInfo clientInfo) {
    CashSymbolRequest request = new CashSymbolRequest();

    request.setCode(code);
    request.setGetList(StringUtils.isEmpty(code));

    List<CashSymbol> cashSymbols = cache.getCashSymbols(request, clientInfo);

    if (CollectionUtils.isEmpty(cashSymbols)) {
      cashSymbols = Collections.emptyList();
    }
    return cashSymbols;
  }
}
