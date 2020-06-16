package ru.philit.ufs.model.converter.esb.pprb;

import java.util.ArrayList;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.pprb.OVNStatusType;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRq.SrvCashDepAnmntListByAccountIdRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs.SrvCashDepAnmntListByAccountIdRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRq.SrvCreateCashDepAnmntItemRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs.SrvCreateCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRq.SrvGetCashDepAnmntItemRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs.SrvGetCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq.SrvUpdCashDepAnmntItemRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq.SrvUpdCashDepAnmntItemRqMessage.CashSymbols;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq.SrvUpdCashDepAnmntItemRqMessage.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs.SrvUpdCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OvnStatus;

/**
 * Преобразователь между сущностями CashDepositAnnouncement и соответствующим транспортным объектом.
 */
public class CashDepositAnnouncementAdapter extends PprbAdapter {

  //******** Converters *******

  private static OvnStatus ovnStatus(OVNStatusType statusType) {
    return (statusType != null) ? OvnStatus.getByCode(statusType.value()) : null;
  }

  private static OVNStatusType ovnStatusType(OvnStatus status) {
    return (status != null) ? OVNStatusType.fromValue(status.code()) : null;
  }

  //******** Mappers *******

  private static void map(CashDepositAnnouncementsRequest ovnRequest,
      SrvCashDepAnmntListByAccountIdRqMessage message) {
    message.setAccountId(ovnRequest.getAccountId());
    message.setOVNStatus(ovnStatusType(ovnRequest.getStatus()));
  }

  private static void map(SrvCashDepAnmntListByAccountIdRsMessage.CashDepAnmntList cashDepAnmntItem,
      CashDepositAnnouncement ovn) {
    ovn.setUid(cashDepAnmntItem.getOVNUId());
    ovn.setNum(longValue(cashDepAnmntItem.getOVNNum()));
    ovn.setStatus(ovnStatus(cashDepAnmntItem.getOVNStatus()));
    ovn.setAmount(cashDepAnmntItem.getAmount());
    ovn.setCreatedDate(date(cashDepAnmntItem.getCreatedDttm()));
    ovn.setInn(cashDepAnmntItem.getINN());
    ovn.setAccountId(cashDepAnmntItem.getAccountId());
    ovn.setRepFio(cashDepAnmntItem.getRepFIO());
    ovn.setLegalEntityShortName(cashDepAnmntItem.getLegalEntityShortName());
  }

  private static void map(CashDepositAnnouncement ovn, SrvCreateCashDepAnmntItemRqMessage message) {
    message.setOVNNum(bigIntegerValue(ovn.getNum()));
    message.setOVNStatus(ovnStatusType(ovn.getStatus()));
    message.setRepFIO(ovn.getRepFio());
    message.setLegalEntityShortName(ovn.getLegalEntityShortName());
    message.setAmount(ovn.getAmount());
    message.setAmountInWords(ovn.getAmountInWords());
    if (ovn.getAmountCop() != null) {
      message.setAmountCop(ovn.getAmountCop());
    }
    message.setCreatedDttm(xmlCalendar(ovn.getCreatedDate()));
    message.setOperationDttm(xmlCalendar(ovn.getOperationDate()));
    message.setINN(ovn.getInn());
    message.setKPP(ovn.getKpp());
    message.setAccountId(ovn.getAccountId());
    message.setSenderAccountId(ovn.getSenderAccountId());
    message.setSenderBank(ovn.getSenderBank());
    message.setSenderBankBIC(ovn.getSenderBankBic());
    message.setRecipientBank(ovn.getRecipientBank());
    message.setRecipientBankBIC(ovn.getRecipientBankBic());
    message.setSource(ovn.getSource());
    message.setIsClientTypeFK(ovn.isClientTypeFk());
    message.setFDestLEName(ovn.getOrganisationNameFk());
    message.setPersonalAccountId(ovn.getPersonalAccountId());
    message.setCurrencyType(ovn.getCurrencyType());
    if (ovn.getCashSymbols() != null) {
      SrvCreateCashDepAnmntItemRqMessage.CashSymbols cashSymbols =
          new SrvCreateCashDepAnmntItemRqMessage.CashSymbols();
      for (CashSymbol cashSymbol : ovn.getCashSymbols()) {
        SrvCreateCashDepAnmntItemRqMessage.CashSymbols.CashSymbolItem cashSymbolItem =
            new SrvCreateCashDepAnmntItemRqMessage.CashSymbols.CashSymbolItem();
        cashSymbolItem.setCashSymbol(cashSymbol.getCode());
        cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
        cashSymbols.getCashSymbolItem().add(cashSymbolItem);
      }
      message.setCashSymbols(cashSymbols);
    }
    message.setAccountantPosition(ovn.getAccountantPosition());
    message.setAccountantFullName(ovn.getAccountantFullName());
    message.setUserPosition(ovn.getUserPosition());
    message.setUserFullName(ovn.getUserFullName());
  }

  private static void map(SrvCreateCashDepAnmntItemRsMessage message, CashDepositAnnouncement ovn) {
    ovn.setUid(message.getOVNUId());
    ovn.setNum(longValue(message.getOVNNum()));
    ovn.setStatus(ovnStatus(message.getOVNStatus()));
    ovn.setResponseCode(message.getResponseCode());
  }

  private static void map(SrvGetCashDepAnmntItemRsMessage message, CashDepositAnnouncement ovn) {
    ovn.setUid(message.getOVNUId());
    ovn.setNum(longValue(message.getOVNNum()));
    ovn.setStatus(ovnStatus(message.getOVNStatus()));
    ovn.setRepFio(message.getRepFIO());
    ovn.setLegalEntityShortName(message.getLegalEntityShortName());
    ovn.setAmount(message.getAmount());
    ovn.setCreatedDate(date(message.getCreatedDttm()));
    ovn.setInn(message.getINN());
    ovn.setKpp(message.getKPP());
    ovn.setAccountId(message.getAccountId());
    ovn.setSenderAccountId(message.getSenderAccountId());
    ovn.setSenderBank(message.getSenderBank());
    ovn.setSenderBankBic(message.getSenderBankBIC());
    ovn.setRecipientBank(message.getRecipientBank());
    ovn.setRecipientBankBic(message.getRecipientBankBIC());
    ovn.setSource(message.getSource());
    ovn.setClientTypeFk(message.isIsClientTypeFK());
    ovn.setOrganisationNameFk(message.getFDestLEName());
    ovn.setPersonalAccountId(message.getPersonalAccountId());
    ovn.setCurrencyType(message.getCurrencyType());
    ovn.setCashSymbols(new ArrayList<CashSymbol>());
    if (message.getCashSymbols() != null) {
      for (SrvGetCashDepAnmntItemRsMessage.CashSymbols.CashSymbolItem cashSymbolItem :
          message.getCashSymbols().getCashSymbolItem()) {
        CashSymbol cashSymbol = new CashSymbol();
        cashSymbol.setCode(cashSymbolItem.getCashSymbol());
        cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
        ovn.getCashSymbols().add(cashSymbol);
      }
    }
  }

  private static void map(CashDepositAnnouncement ovn, SrvUpdCashDepAnmntItemRqMessage message) {
    message.setOVNUId(ovn.getUid());
    message.setOVNNum(bigIntegerValue(ovn.getNum()));
    message.setOVNStatus(ovnStatusType(ovn.getStatus()));
    message.setRepFIO(ovn.getRepFio());
    message.setLegalEntityShortName(ovn.getLegalEntityShortName());
    message.setAmount(ovn.getAmount());
    message.setAmountInWords(ovn.getAmountInWords());
    message.setAmountCop(ovn.getAmountCop());
    message.setCreatedDttm(xmlCalendar(ovn.getCreatedDate()));
    message.setOperationDttm(xmlCalendar(ovn.getOperationDate()));
    message.setINN(ovn.getInn());
    message.setKPP(ovn.getKpp());
    message.setAccountId(ovn.getAccountId());
    message.setSenderAccountId(ovn.getSenderAccountId());
    message.setSenderBank(ovn.getSenderBank());
    message.setSenderBankBIC(ovn.getSenderBankBic());
    message.setRecipientBank(ovn.getRecipientBank());
    message.setRecipientAccountId(ovn.getRecipientAccountId());
    message.setRecipientBankBIC(ovn.getRecipientBankBic());
    message.setSource(ovn.getSource());
    message.setIsClientTypeFK(ovn.isClientTypeFk());
    message.setFDestLEName(ovn.getOrganisationNameFk());
    message.setPersonalAccountId(ovn.getPersonalAccountId());
    message.setCurrencyType(ovn.getCurrencyType());
    if (ovn.getCashSymbols() != null) {
      CashSymbols cashSymbols = new CashSymbols();
      for (CashSymbol cashSymbol : ovn.getCashSymbols()) {
        CashSymbolItem cashSymbolItem = new CashSymbolItem();
        cashSymbolItem.setCashSymbol(cashSymbol.getCode());
        cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
        cashSymbols.getCashSymbolItem().add(cashSymbolItem);
      }
      message.setCashSymbols(cashSymbols);
    }
    message.setAccountantPosition(ovn.getAccountantPosition());
    message.setAccountantFullName(ovn.getAccountantFullName());
    message.setUserPosition(ovn.getUserPosition());
    message.setUserFullName(ovn.getUserFullName());
  }

  private static void map(SrvUpdCashDepAnmntItemRsMessage message, CashDepositAnnouncement ovn) {
    ovn.setUid(message.getOVNUId());
    ovn.setNum(longValue(message.getOVNNum()));
    ovn.setStatus(ovnStatus(message.getOVNStatus()));
    ovn.setResponseCode(message.getResponseCode());
  }

  //******** Methods *******

  /**
   * Возвращает объект запроса списка ОВН.
   */
  public static SrvCashDepAnmntListByAccountIdRq requestOvnList(
      CashDepositAnnouncementsRequest ovnRequest) {
    SrvCashDepAnmntListByAccountIdRq request = new SrvCashDepAnmntListByAccountIdRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCashDepAnmntListByAccountIdRqMessage(
        new SrvCashDepAnmntListByAccountIdRqMessage());
    map(ovnRequest, request.getSrvCashDepAnmntListByAccountIdRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса на добавление ОВН.
   */
  public static SrvCreateCashDepAnmntItemRq requestCreateOvn(CashDepositAnnouncement ovn) {
    SrvCreateCashDepAnmntItemRq request = new SrvCreateCashDepAnmntItemRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateCashDepAnmntItemRqMessage(new SrvCreateCashDepAnmntItemRqMessage());
    map(ovn, request.getSrvCreateCashDepAnmntItemRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса ОВН.
   */
  public static SrvGetCashDepAnmntItemRq requestGetOvn(String ovnUid) {
    SrvGetCashDepAnmntItemRq request = new SrvGetCashDepAnmntItemRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetCashDepAnmntItemRqMessage(new SrvGetCashDepAnmntItemRqMessage());
    request.getSrvGetCashDepAnmntItemRqMessage().setOVNUId(ovnUid);
    return request;
  }

  /**
   * Возвращает объект запроса на редактирование ОВН.
   */
  public static SrvUpdCashDepAnmntItemRq requestUpdateOvn(CashDepositAnnouncement ovn) {
    SrvUpdCashDepAnmntItemRq request = new SrvUpdCashDepAnmntItemRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdCashDepAnmntItemRqMessage(new SrvUpdCashDepAnmntItemRqMessage());
    map(ovn, request.getSrvUpdCashDepAnmntItemRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект списка ОВН во внутреннюю сущность.
   */
  public static ExternalEntityList<CashDepositAnnouncement> convert(
      SrvCashDepAnmntListByAccountIdRs response) {
    ExternalEntityList<CashDepositAnnouncement> ovnList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), ovnList);

    for (SrvCashDepAnmntListByAccountIdRsMessage.CashDepAnmntList cashDepAnmntItem :
        response.getSrvCashDepAnmntListByAccountIdRsMessage().getCashDepAnmntList()) {
      CashDepositAnnouncement ovn = new CashDepositAnnouncement();
      map(response.getHeaderInfo(), ovn);
      map(cashDepAnmntItem, ovn);
      ovnList.getItems().add(ovn);
    }
    return ovnList;
  }

  /**
   * Преобразует транспортный объект ответа во внутреннюю сущность.
   */
  public static CashDepositAnnouncement convert(SrvCreateCashDepAnmntItemRs response) {
    CashDepositAnnouncement ovn = new CashDepositAnnouncement();
    map(response.getHeaderInfo(), ovn);
    map(response.getSrvCreateCashDepAnmntItemRsMessage(), ovn);
    return ovn;
  }

  /**
   * Преобразует транспортный объект ОВН во внутреннюю сущность.
   */
  public static CashDepositAnnouncement convert(SrvGetCashDepAnmntItemRs response) {
    CashDepositAnnouncement ovn = new CashDepositAnnouncement();
    map(response.getHeaderInfo(), ovn);
    map(response.getSrvGetCashDepAnmntItemRsMessage(), ovn);
    return ovn;
  }

  /**
   * Преобразует транспортный объект ответа на редактирвоание ОВН во внутреннюю сущность.
   */
  public static CashDepositAnnouncement convert(SrvUpdCashDepAnmntItemRs response) {
    CashDepositAnnouncement ovn = new CashDepositAnnouncement();
    map(response.getHeaderInfo(), ovn);
    map(response.getSrvUpdCashDepAnmntItemRsMessage(), ovn);
    return ovn;
  }
}
