package ru.philit.ufs.web.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.dto.OperationTypeDto;
import ru.philit.ufs.web.mapping.OperationTypeMapper;
import ru.philit.ufs.web.provider.OperationTypeProvider;
import ru.philit.ufs.web.view.GetCashSymbolsReq;
import ru.philit.ufs.web.view.GetCashSymbolsResp;
import ru.philit.ufs.web.view.GetFavouritesResp;
import ru.philit.ufs.web.view.GetOperationTypesResp;
import ru.philit.ufs.web.view.SaveFavouritesReq;
import ru.philit.ufs.web.view.SaveFavouritesResp;

/**
 * Контроллер действий с типами операций.
 */
@RestController
@RequestMapping("/operationTypes")
public class OperationTypeController {

  private final OperationTypeProvider provider;
  private final OperationTypeMapper mapper;

  @Autowired
  public OperationTypeController(OperationTypeProvider provider, OperationTypeMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Получение типов операций, видимых пользователю.
   *
   * @param clientInfo информация о клиенте
   * @return список типов операций для пользователя
   */
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public GetOperationTypesResp getOperationTypes(ClientInfo clientInfo) {
    List<OperationTypeDto> list = new ArrayList<>();

    for (OperationType operationType : provider.getOperationTypes(clientInfo)) {
      if (operationType.isVisible()) {
        list.add(mapper.asDto(operationType));
      }
    }
    return new GetOperationTypesResp().withSuccess(list);
  }

  /**
   * Получение типов операций, добавленных в избранное.
   *
   * @param clientInfo информация о клиенте
   * @return список идентификаторов избранных типов операций, упорядоченных по индексу
   */
  @RequestMapping(value = "/getFavourites", method = RequestMethod.POST)
  public GetFavouritesResp getFavourites(ClientInfo clientInfo) {
    List<OperationTypeFavourite> favourites = provider.getFavourites(clientInfo);
    return new GetFavouritesResp().withSuccess(mapper.asFavouriteDto(favourites));
  }

  /**
   * Сохранение типов операций, добавленных в избранное.
   *
   * @param request упорядоченный список идентификаторов типов операций
   * @param clientInfo информация о клиенте
   * @return результат сохранения типов операций
   */
  @RequestMapping(value = "/saveFavourites", method = RequestMethod.POST)
  public SaveFavouritesResp saveFavourites(
      @RequestBody SaveFavouritesReq request, ClientInfo clientInfo
  ) {
    List<OperationTypeFavourite> favourites = mapper.asEntity(request.getTypeIds());
    return new SaveFavouritesResp().withSuccess(provider.saveFavourites(favourites, clientInfo));
  }

  /**
   * Получение справочника кодов операций.
   *
   * @param request код кассового символа
   * @param clientInfo информация о клиенте
   * @return список кассовых символов с описанием
   */
  @RequestMapping(value = "/cashSymbols", method = RequestMethod.POST)
  public GetCashSymbolsResp getCashSymbols(
      @RequestBody GetCashSymbolsReq request, ClientInfo clientInfo
  ) {
    List<CashSymbol> cashSymbols = provider.getCashSymbols(request.getCode(), clientInfo);
    return new GetCashSymbolsResp().withSuccess(mapper.asSymbolDto(cashSymbols));
  }

}
