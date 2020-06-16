package ru.philit.ufs.model.converter.esb.pprb;

import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRq.SrvCashSymbolsListRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs.SrvCashSymbolsListRsMessage;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;

/**
 * Преобразователь между сущностью CashSymbol и соответствующим транспортным объектом.
 */
public class CashSymbolAdapter extends PprbAdapter {

  private static void map(SrvCashSymbolsListRsMessage.CashSymbolItem cashSymbolItem,
      CashSymbol cashSymbol) {
    cashSymbol.setCode(cashSymbolItem.getCashSymbol());
    cashSymbol.setDescription(cashSymbolItem.getCashSymbolDesc());
  }

  /**
   * Преобразует транспортный объект Кассовый символ во внутреннюю сущность.
   */
  public static ExternalEntityList<CashSymbol> convert(SrvCashSymbolsListRs response) {
    ExternalEntityList<CashSymbol> cashSymbolList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), cashSymbolList);
    for (SrvCashSymbolsListRsMessage.CashSymbolItem cashSymbolItem :
        response.getSrvCashSymbolsListRsMessage().getCashSymbolItem()) {
      CashSymbol cashSymbol = new CashSymbol();
      map(response.getHeaderInfo(), cashSymbol);
      map(cashSymbolItem, cashSymbol);
      cashSymbolList.getItems().add(cashSymbol);
    }
    return cashSymbolList;
  }

  /**
   * Возвращает объект запроса на получение справочника кодов операций.
   */
  public static SrvCashSymbolsListRq requestCashSymbol(CashSymbolRequest requestData) {
    SrvCashSymbolsListRq request = new SrvCashSymbolsListRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCashSymbolsListRqMessage(new SrvCashSymbolsListRqMessage());
    request.getSrvCashSymbolsListRqMessage().setGetList(requestData.isGetList());
    request.getSrvCashSymbolsListRqMessage().setCashSymbol(requestData.getCode());
    return request;
  }
}
