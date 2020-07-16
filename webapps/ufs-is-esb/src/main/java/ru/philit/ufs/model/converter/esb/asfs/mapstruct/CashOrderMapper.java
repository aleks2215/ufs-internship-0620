package ru.philit.ufs.model.converter.esb.asfs.mapstruct;

import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.philit.ufs.model.converter.esb.asfs.AsfsAdapter;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.IDDtype;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData.IdentityDocumentType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashOrderTypeModel;
import ru.philit.ufs.model.entity.oper.CashSymbol;

@Mapper(componentModel = "spring", imports = {Date.class})
public abstract class CashOrderMapper extends AsfsAdapter {

  /**
   * Возвращает объект запроса кассового ордера.
   */
  @Mappings({
      @Mapping(target = "headerInfo", expression = "java(headerInfo())"),
      @Mapping(target = "srvCreateCashOrderRqMessage.cashOrderId", source = "cashOrderId"),
      @Mapping(target = "srvCreateCashOrderRqMessage.operationType", source = "operationType"),
      @Mapping(target = "srvCreateCashOrderRqMessage.cashOrderINum", source = "cashOrderINum"),
      @Mapping(target = "srvCreateCashOrderRqMessage.accountId", source = "accountId"),
      @Mapping(target = "srvCreateCashOrderRqMessage.amount", source = "amount"),
      @Mapping(target = "srvCreateCashOrderRqMessage.amountInWords", source = "amountInWords"),
      @Mapping(target = "srvCreateCashOrderRqMessage.currencyType", source = "currencyType"),
      @Mapping(target = "srvCreateCashOrderRqMessage.cashOrderStatus", source = "cashOrderStatus"),
      @Mapping(target = "srvCreateCashOrderRqMessage.workPlaceUId", source = "workPlaceUId"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.repID", source = "repId"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.repFIO", source = "repFio"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.address", source = "address"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.dateOfBirth",
          expression = "java(xmlCalendar(cashOrder.getDateOfBirth()))"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.identityDocumentType",
          source = "identityDocuments"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.placeOfBirth",
          source = "placeOfBirth"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.resident", source = "resident"),
      @Mapping(target = "srvCreateCashOrderRqMessage.repData.INN", source = "inn"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.cashSymbols.cashSymbolItem",
          source = "cashSymbols"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.comment", source = "comment"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.subbranchCode",
          source = "subbranch.subbranchCode"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.VSPCode",
          source = "subbranch.vspCode"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.OSBCode",
          source = "subbranch.osbCode"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.GOSBCode",
          source = "subbranch.gosbCode"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.TBCode",
          source = "subbranch.tbCode"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.account20202Num",
          source = "account20202Num"),
      @Mapping(target = "srvCreateCashOrderRqMessage.additionalInfo.userLogin",
          source = "userLogin"),
  })
  public abstract SrvCreateCashOrderRq requestCreateCashOrder(CashOrder cashOrder);

  OperTypeLabel operTypeLabel(OperationTypeCode operationTypeCode) {
    return (operationTypeCode != null) ? OperTypeLabel.fromValue(operationTypeCode.code()) : null;
  }

  @Mappings({
      @Mapping(target = "value", source = "type"),
      @Mapping(target = "series", source = "series"),
      @Mapping(target = "number", source = "number"),
      @Mapping(target = "issuedBy", source = "issuedBy"),
      @Mapping(target = "issuedDate",
          expression = "java(xmlCalendar(identityDocument.getIssuedDate()))")
  })
  abstract IdentityDocumentType identityDocumentType(IdentityDocument identityDocument);

  IDDtype iddType(ru.philit.ufs.model.entity.account.IdentityDocumentType status) {
    return (status != null) ? IDDtype.fromValue(status.code()) : null;
  }

  @Mappings({
      @Mapping(target = "cashSymbol", source = "code"),
      @Mapping(target = "cashSymbolAmount", source = "amount")
  })
  abstract SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage
      .AdditionalInfo.CashSymbols.CashSymbolItem cashSymbolItem(CashSymbol cashSymbol);

  /**
   * Возвращает объект запроса обновления кассового ордера.
   */
  @Mappings({
      @Mapping(target = "headerInfo", expression = "java(headerInfo())"),
      @Mapping(target = "srvUpdCashOrderRqMessage.cashOrderId", source = "cashOrderId"),
      @Mapping(target = "srvUpdCashOrderRqMessage.cashOrderStatus", source = "cashOrderStatus")
  })
  public abstract SrvUpdStCashOrderRq requestUpdStCashOrder(CashOrder cashOrder);

  CashOrderStatusType cashOrderStatusType(CashOrderStatus cashOrderStatus) {
    return (cashOrderStatus != null) ? CashOrderStatusType.fromValue(cashOrderStatus.code())
        : null;
  }

  /**
   * Преобразует транспортный объект обновления кассового ордера во внутреннюю сущность.
   */
  @Mappings({
      @Mapping(target = "requestUid", source = "headerInfo.rqUID"),
      @Mapping(target = "receiveDate", expression = "java(new Date())"),
      @Mapping(target = "responseCode", source = "srvUpdCashOrderRsMessage.responseCode"),
      @Mapping(target = "responseMsg", source = "srvUpdCashOrderRsMessage.responseMsg"),
      @Mapping(target = "cashOrderId", source = "srvUpdCashOrderRsMessage.cashOrderId"),
      @Mapping(target = "cashOrderINum", source = "srvUpdCashOrderRsMessage.cashOrderINum"),
      @Mapping(target = "cashOrderStatus", source = "srvUpdCashOrderRsMessage.cashOrderStatus"),
      @Mapping(target = "cashOrderTypeModel", source = "srvUpdCashOrderRsMessage.cashOrderType")
  })
  public abstract CashOrder convert(SrvUpdStCashOrderRs response);

  /**
   * Преобразует транспортный объект кассового ордера во внутреннюю сущность.
   */
  @Mappings({
      @Mapping(target = "requestUid", source = "headerInfo.rqUID"),
      @Mapping(target = "receiveDate", expression = "java(new Date())"),
      @Mapping(target = "responseCode", source = "srvCreateCashOrderRsMessage.KO1.responseCode"),
      @Mapping(target = "responseMsg", source = "srvCreateCashOrderRsMessage.KO1.responseMsg"),
      @Mapping(target = "cashOrderId", source = "srvCreateCashOrderRsMessage.KO1.cashOrderId"),
      @Mapping(target = "cashOrderINum", source = "srvCreateCashOrderRsMessage.KO1.cashOrderINum"),
      @Mapping(target = "cashOrderStatus",
          source = "srvCreateCashOrderRsMessage.KO1.cashOrderStatus"),
      @Mapping(target = "cashOrderTypeModel",
          source = "srvCreateCashOrderRsMessage.KO1.cashOrderType"),
      @Mapping(target = "createdDttm",
          expression = "java(date(response.getSrvCreateCashOrderRsMessage()"
              + ".getKO1().getCreatedDttm()))"),
      @Mapping(target = "operationId", source = "srvCreateCashOrderRsMessage.KO1.operationId"),
      @Mapping(target = "repFio", source = "srvCreateCashOrderRsMessage.KO1.repFIO"),
      @Mapping(target = "legalEntityShortName",
          source = "srvCreateCashOrderRsMessage.KO1.legalEntityShortName"),
      @Mapping(target = "inn",
          source = "srvCreateCashOrderRsMessage.KO1.INN"),
      @Mapping(target = "amount",
          source = "srvCreateCashOrderRsMessage.KO1.amount"),
      @Mapping(target = "accountId",
          source = "srvCreateCashOrderRsMessage.KO1.accountId"),
      @Mapping(target = "cashSymbols",
          source = "srvCreateCashOrderRsMessage.KO1.cashSymbols.cashSymbolItem"),
      @Mapping(target = "senderBank",
          source = "srvCreateCashOrderRsMessage.KO1.senderBank"),
      @Mapping(target = "senderBankBic",
          source = "srvCreateCashOrderRsMessage.KO1.senderBankBIC"),
      @Mapping(target = "recipientBank",
          source = "srvCreateCashOrderRsMessage.KO1.recipientBank"),
      @Mapping(target = "recipientBankBic",
          source = "srvCreateCashOrderRsMessage.KO1.recipientBankBIC"),
      @Mapping(target = "clientTypeFk",
          source = "srvCreateCashOrderRsMessage.KO1.clientTypeFK"),
      @Mapping(target = "organisationNameFk",
          source = "srvCreateCashOrderRsMessage.KO1.FDestLEName"),
      @Mapping(target = "operatorPosition",
          source = "srvCreateCashOrderRsMessage.KO1.operatorPosition"),
      @Mapping(target = "userFullName",
          source = "srvCreateCashOrderRsMessage.KO1.userFullName"),
      @Mapping(target = "userPosition",
          source = "srvCreateCashOrderRsMessage.KO1.userPosition")
  })
  public abstract CashOrder convert(SrvCreateCashOrderRs response);

  @Mappings({
      @Mapping(target = "code", source = "cashSymbol"),
      @Mapping(target = "amount", source = "cashSymbolAmount")
  })
  abstract CashSymbol cashSymbol(SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage
      .KO1.CashSymbols.CashSymbolItem cashSymbolItem);

  CashOrderStatus cashOrderStatus(CashOrderStatusType cashOrderStatusType) {
    return (cashOrderStatusType != null) ? CashOrderStatus.getByCode(cashOrderStatusType.value())
        : null;
  }

  CashOrderTypeModel cashOrderTypeModel(CashOrderType cashOrderType) {
    return (cashOrderType != null) ? CashOrderTypeModel.getByCode(cashOrderType.value()) : null;
  }
}
