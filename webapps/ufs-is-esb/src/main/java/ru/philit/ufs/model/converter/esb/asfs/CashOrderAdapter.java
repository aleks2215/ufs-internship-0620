package ru.philit.ufs.model.converter.esb.asfs;

import java.util.ArrayList;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.as_fs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.as_fs.CashOrderType;
import ru.philit.ufs.model.entity.esb.as_fs.IDDtype;
import ru.philit.ufs.model.entity.esb.as_fs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo.CashSymbols;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashOrderTypeModel;
import ru.philit.ufs.model.entity.oper.CashSymbol;

/**
 * Преобразователь между сущностью CashOrder и соответствующим транспортным объектом.
 */
public class CashOrderAdapter extends AsfsAdapter {

  //******** Converters ********

  private static CashOrderStatus cashOrderStatus(CashOrderStatusType cashOrderStatusType) {
    return (cashOrderStatusType != null) ? CashOrderStatus.getByCode(cashOrderStatusType.value())
        : null;
  }

  private static CashOrderStatusType cashOrderStatusType(CashOrderStatus cashOrderStatus) {
    return (cashOrderStatus != null) ? CashOrderStatusType.fromValue(cashOrderStatus.code())
        : null;
  }

  private static CashOrderTypeModel cashOrderTypeModel(CashOrderType cashOrderType) {
    return (cashOrderType != null) ? CashOrderTypeModel.getByCode(cashOrderType.value()) : null;
  }

  private static OperTypeLabel operTypeLabel(OperationTypeCode operationTypeCode) {
    return (operationTypeCode != null) ? OperTypeLabel.fromValue(operationTypeCode.code()) : null;
  }

  private static IDDtype iddType(IdentityDocumentType status) {
    return (status != null) ? IDDtype.fromValue(status.code()) : null;
  }

  //******** Mappers *******

  private static void map(SrvCreateCashOrderRsMessage message, CashOrder cashOrder) {
    KO1 ko1 = message.getKO1();
    cashOrder.setResponseCode(ko1.getResponseCode());
    cashOrder.setResponseMsg(ko1.getResponseMsg());
    cashOrder.setCashOrderId(ko1.getCashOrderId());
    cashOrder.setCashOrderINum(ko1.getCashOrderINum());
    cashOrder.setCashOrderStatus(cashOrderStatus(ko1.getCashOrderStatus()));
    cashOrder.setCashOrderTypeModel(cashOrderTypeModel(ko1.getCashOrderType()));
    cashOrder.setCreatedDttm(date(ko1.getCreatedDttm()));
    cashOrder.setOperationId(ko1.getOperationId());
    cashOrder.setRepFio(ko1.getRepFIO());
    cashOrder.setLegalEntityShortName(ko1.getLegalEntityShortName());
    cashOrder.setInn(ko1.getINN());
    cashOrder.setAmount(ko1.getAmount());
    cashOrder.setAccountId(ko1.getAccountId());
    cashOrder.setCashSymbols(new ArrayList<CashSymbol>());
    if (ko1.getCashSymbols() != null) {
      for (SrvCreateCashOrderRsMessage.KO1.CashSymbols.CashSymbolItem cashSymbolItem :
          ko1.getCashSymbols().getCashSymbolItem()) {
        CashSymbol cashSymbol = new CashSymbol();
        cashSymbol.setCode(cashSymbolItem.getCashSymbol());
        cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
        cashOrder.getCashSymbols().add(cashSymbol);
      }
    }
    cashOrder.setSenderBank(ko1.getSenderBank());
    cashOrder.setSenderBankBic(ko1.getSenderBankBIC());
    cashOrder.setRecipientBank(ko1.getRecipientBank());
    cashOrder.setRecipientBankBic(ko1.getRecipientBankBIC());
    cashOrder.setClientTypeFk(ko1.isClientTypeFK());
    cashOrder.setOrganisationNameFk(ko1.getFDestLEName());
    cashOrder.setOperatorPosition(ko1.getOperatorPosition());
    cashOrder.setUserFullName(ko1.getUserFullName());
    cashOrder.setUserPosition(ko1.getUserPosition());
  }

  private static void map(CashOrder cashOrder, SrvCreateCashOrderRqMessage message) {
    message.setCashOrderId(cashOrder.getCashOrderId());
    message.setOperationType(operTypeLabel(cashOrder.getOperationType()));
    message.setCashOrderINum(cashOrder.getCashOrderINum());
    message.setAccountId(cashOrder.getAccountId());
    message.setAmount(cashOrder.getAmount());
    message.setAmountInWords(cashOrder.getAmountInWords());
    message.setCurrencyType(cashOrder.getCurrencyType());
    message.setCashOrderStatus(cashOrderStatusType(cashOrder.getCashOrderStatus()));
    message.setWorkPlaceUId(cashOrder.getWorkPlaceUId());
    message.setRepData(new SrvCreateCashOrderRqMessage.RepData());
    map(cashOrder, message.getRepData());
    message.setAdditionalInfo(new SrvCreateCashOrderRqMessage.AdditionalInfo());
    map(cashOrder, message.getAdditionalInfo());
  }

  private static void map(CashOrder cashOrder, SrvCreateCashOrderRqMessage.RepData message) {
    message.setRepID(cashOrder.getRepId());
    message.setRepFIO(cashOrder.getRepFio());
    message.setAddress(cashOrder.getAddress());
    message.setDateOfBirth(xmlCalendar(cashOrder.getDateOfBirth()));

    if (cashOrder.getIdentityDocuments() != null) {
      for (IdentityDocument identityDocument : cashOrder.getIdentityDocuments()) {
        RepData.IdentityDocumentType identityDocumentType = new RepData.IdentityDocumentType();
        identityDocumentType.setValue(iddType(identityDocument.getType()));
        identityDocumentType.setSeries(identityDocument.getSeries());
        identityDocumentType.setNumber(identityDocument.getNumber());
        identityDocumentType.setIssuedBy(identityDocument.getIssuedBy());
        identityDocumentType.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
        message.getIdentityDocumentType().add(identityDocumentType);
      }
    }

    message.setPlaceOfBirth(cashOrder.getPlaceOfBirth());
    message.setResident(cashOrder.isResident());
    message.setINN(cashOrder.getInn());
  }

  private static void map(CashOrder cashOrder,
      SrvCreateCashOrderRqMessage.AdditionalInfo additionalInfo) {
    if (cashOrder.getCashSymbols() != null) {
      CashSymbols cashSymbols = new CashSymbols();
      for (CashSymbol cashSymbol : cashOrder.getCashSymbols()) {
        CashSymbolItem cashSymbolItem = new CashSymbolItem();
        cashSymbolItem.setCashSymbol(cashSymbol.getCode());
        cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
        cashSymbols.getCashSymbolItem().add(cashSymbolItem);
      }
      additionalInfo.setCashSymbols(cashSymbols);
    }

    if (cashOrder.getSubbranch() != null) {
      additionalInfo.setSubbranchCode(cashOrder.getSubbranch().getSubbranchCode());
      additionalInfo.setVSPCode(cashOrder.getSubbranch().getVspCode());
      additionalInfo.setOSBCode(cashOrder.getSubbranch().getOsbCode());
      additionalInfo.setGOSBCode(cashOrder.getSubbranch().getGosbCode());
      additionalInfo.setTBCode(cashOrder.getSubbranch().getTbCode());
    }

    additionalInfo.setComment(cashOrder.getComment());
    additionalInfo.setAccount20202Num(cashOrder.getAccount20202Num());
    additionalInfo.setUserLogin(cashOrder.getUserLogin());
  }

  private static void map(SrvUpdCashOrderRsMessage message, CashOrder cashOrder) {
    cashOrder.setResponseCode(message.getResponseCode());
    cashOrder.setResponseMsg(message.getResponseMsg());
    cashOrder.setCashOrderId(message.getCashOrderId());
    cashOrder.setCashOrderINum(message.getCashOrderINum());
    cashOrder.setCashOrderStatus(cashOrderStatus(message.getCashOrderStatus()));
    cashOrder.setCashOrderTypeModel(cashOrderTypeModel(message.getCashOrderType()));
  }

  private static void map(CashOrder cashOrder, SrvUpdCashOrderRqMessage message) {
    message.setCashOrderId(cashOrder.getCashOrderId());
    message.setCashOrderStatus(cashOrderStatusType(cashOrder.getCashOrderStatus()));
  }

  //******** Methods *******

  /**
   * Возвращает объект запроса кассового ордера.
   */
  public static SrvCreateCashOrderRq requestCreateCashOrder(CashOrder cashOrder) {
    SrvCreateCashOrderRq request = new SrvCreateCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateCashOrderRqMessage(new SrvCreateCashOrderRqMessage());
    map(cashOrder, request.getSrvCreateCashOrderRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса обновления кассового ордера.
   */
  public static SrvUpdStCashOrderRq requestUpdStCashOrder(CashOrder cashOrder) {
    SrvUpdStCashOrderRq request = new SrvUpdStCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdCashOrderRqMessage(new SrvUpdCashOrderRqMessage());
    map(cashOrder, request.getSrvUpdCashOrderRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект обновления кассового ордера во внутреннюю сущность.
   */
  public static CashOrder convert(SrvUpdStCashOrderRs response) {
    CashOrder cashOrder = new CashOrder();
    map(response.getHeaderInfo(), cashOrder);
    map(response.getSrvUpdCashOrderRsMessage(), cashOrder);
    return cashOrder;
  }

  /**
   * Преобразует транспортный объект кассового ордера во внутреннюю сущность.
   */
  public static CashOrder convert(SrvCreateCashOrderRs response) {
    CashOrder cashOrder = new CashOrder();
    map(response.getHeaderInfo(), cashOrder);
    map(response.getSrvCreateCashOrderRsMessage(), cashOrder);
    return cashOrder;
  }

}
