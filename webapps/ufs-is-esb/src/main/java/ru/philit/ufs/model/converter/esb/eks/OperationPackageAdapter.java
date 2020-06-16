package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigInteger;
import java.util.ArrayList;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.model.entity.account.CardNetworkCode;
import ru.philit.ufs.model.entity.account.CardType;
import ru.philit.ufs.model.entity.account.Checkbook;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.eks.CbRequestStatusType;
import ru.philit.ufs.model.entity.esb.eks.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.eks.IDDtype;
import ru.philit.ufs.model.entity.esb.eks.OVNStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRq.SrvAddTaskClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs.SrvAddTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRq.SrvCheckClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs.SrvCheckClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRq.SrvCreateClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs.SrvCreateClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRq.SrvGetTaskClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem.StockholderInfo.Stockholderitem;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRq.SrvUpdTaskClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRs.SrvUpdTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CheckbookRequestStatus;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationPackageStatus;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskAccountDeposit;
import ru.philit.ufs.model.entity.oper.OperationTaskAccountDeposit.Stockholder;
import ru.philit.ufs.model.entity.oper.OperationTaskAccountWithdraw;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.oper.OperationTaskCardWithdraw;
import ru.philit.ufs.model.entity.oper.OperationTaskCheckbookIssuing;
import ru.philit.ufs.model.entity.oper.OperationTaskStatus;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.oper.OvnStatus;

/**
 * Преобразователь между сущностями OperationPackage и соответствующим транспортным объектом.
 */
public class OperationPackageAdapter extends EksAdapter {

  //******** Converters ********

  private static OperationPackageStatus operationPackageStatus(PkgStatusType statusType) {
    return (statusType != null) ? OperationPackageStatus.getByCode(statusType.value()) : null;
  }

  private static OvnStatus ovnStatus(OVNStatusType statusType) {
    return (statusType != null) ? OvnStatus.getByCode(statusType.value()) : null;
  }

  private static OVNStatusType ovnStatusType(OvnStatus status) {
    return (status != null) ? OVNStatusType.fromValue(status.code()) : null;
  }

  private static OperationTaskStatus operationTaskStatus(PkgTaskStatusType statusType) {
    return (statusType != null) ? OperationTaskStatus.getByCode(statusType.value()) : null;
  }

  private static PkgTaskStatusType pkgTaskStatusType(OperationTaskStatus status) {
    return (status != null) ? PkgTaskStatusType.fromValue(status.code()) : null;
  }

  private static IdentityDocumentType identityDocumentType(IDDtype statusType) {
    return (statusType != null) ? IdentityDocumentType.getByCode(statusType.value()) : null;
  }

  private static IDDtype iddType(IdentityDocumentType status) {
    return (status != null) ? IDDtype.fromValue(status.code()) : null;
  }

  private static CheckbookRequestStatus checkbookRequestStatus(CbRequestStatusType statusType) {
    return (statusType != null) ? CheckbookRequestStatus.getByCode(statusType.value()) : null;
  }

  private static CbRequestStatusType cbRequestStatusType(CheckbookRequestStatus status) {
    return (status != null) ? CbRequestStatusType.fromValue(status.code()) : null;
  }

  private static CardNetworkCode cardNetworkCode(BigInteger networkCode) {
    return (networkCode != null) ? CardNetworkCode.getByCode(networkCode.intValue()) : null;
  }

  private static BigInteger cardNetworkCodeId(CardNetworkCode cardNetworkCode) {
    return (cardNetworkCode != null) ? BigInteger.valueOf(cardNetworkCode.code()) : null;
  }

  private static CardType cardType(BigInteger cardTypeId) {
    return (cardTypeId != null) ? CardType.getByCode(cardTypeId.intValue()) : null;
  }

  private static BigInteger cardTypeId(CardType cardType) {
    return (cardType != null) ? BigInteger.valueOf(cardType.code()) : null;
  }

  //******** Mappers ********

  //******** SrvAddTaskClOperPkgRq ********

  private static void map(OperationPackage pakage, SrvAddTaskClOperPkgRqMessage message) {
    message.setPkgId(pakage.getId());
    message.setToCardDeposit(new SrvAddTaskClOperPkgRqMessage.ToCardDeposit());
    message.setFromCardWithdraw(new SrvAddTaskClOperPkgRqMessage.FromCardWithdraw());
    message.setToAccountDepositRub(new SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub());
    message.setFromAccountWithdrawRub(new SrvAddTaskClOperPkgRqMessage.FromAccountWithdrawRub());
    message.setCheckbookIssuing(new SrvAddTaskClOperPkgRqMessage.CheckbookIssuing());

    for (OperationTask task : pakage.getToCardDeposits()) {
      if (task instanceof OperationTaskCardDeposit) {
        SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem taskItem =
            new SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem();
        map((OperationTaskCardDeposit) task, taskItem);
        message.getToCardDeposit().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getFromCardWithdraws()) {
      if (task instanceof OperationTaskCardWithdraw) {
        SrvAddTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem taskItem =
            new SrvAddTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem();
        map((OperationTaskCardWithdraw) task, taskItem);
        message.getFromCardWithdraw().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getToAccountDeposits()) {
      if (task instanceof OperationTaskAccountDeposit) {
        SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem taskItem =
            new SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem();
        map((OperationTaskAccountDeposit) task, taskItem);
        message.getToAccountDepositRub().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getFromAccountWithdraws()) {
      if (task instanceof OperationTaskAccountWithdraw) {
        SrvAddTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem taskItem =
            new SrvAddTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem();
        map((OperationTaskAccountWithdraw) task, taskItem);
        message.getFromAccountWithdrawRub().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getCheckbookIssuing()) {
      if (task instanceof OperationTaskCheckbookIssuing) {
        SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem taskItem =
            new SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem();
        map((OperationTaskCheckbookIssuing) task, taskItem);
        message.getCheckbookIssuing().getTaskItem().add(taskItem);
      }
    }
  }

  private static void map(OperationTaskCardDeposit task,
      SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem taskItem) {
    taskItem.setOVNUId(task.getOvnUid());
    taskItem.setOVNNum(bigIntegerValue(task.getOvnNum()));
    taskItem.setOVNStatus(ovnStatusType(task.getOvnStatus()));
    taskItem.setRepID(task.getRepresentativeId());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setKPP(task.getKpp());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setCardInfo(new SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CardInfo());
    map(task.getCard(), taskItem.getCardInfo());
    taskItem.setSenderAccountId(task.getSenderAccountId());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setRecipientBank(task.getRecipientBank());
    taskItem.setRecipientBankBIC(task.getRecipientBankBic());
    taskItem.setSource(task.getSource());
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCashSymbols(new SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CashSymbols());
    for (CashSymbol cashSymbol : task.getCashSymbols()) {
      SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CashSymbols.CashSymbolItem cashSymbolItem
          = new SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CashSymbols.CashSymbolItem();
      map(cashSymbol, cashSymbolItem);
      taskItem.getCashSymbols().getCashSymbolItem().add(cashSymbolItem);
    }
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(Card card,
      SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CardInfo cardInfo) {
    cardInfo.setCardNumber(card.getNumber());
    cardInfo.setCardExpDate(xmlCalendar(card.getExpiryDate()));
    cardInfo.setCardIssuingNetworkCode(cardNetworkCodeId(card.getIssuingNetworkCode()));
    cardInfo.setCardTypeId(cardTypeId(card.getType()));
    cardInfo.setCardOwnerFirstName(card.getOwnerFirstName());
    cardInfo.setCardOwnerLastName(card.getOwnerLastName());
  }

  private static void map(CashSymbol cashSymbol, SrvAddTaskClOperPkgRqMessage.ToCardDeposit
      .TaskItem.CashSymbols.CashSymbolItem cashSymbolItem) {
    cashSymbolItem.setCashSymbol(cashSymbol.getCode());
    cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
  }

  private static void map(OperationTaskCardWithdraw task,
      SrvAddTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem taskItem) {
    taskItem.setRepFIO(task.getRepFio());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setCardNumber(task.getCardNumber());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setReason(task.getReason());
    map(task.getIdentityDocument(), taskItem);
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCause(task.getCause());
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(IdentityDocument identityDocument,
      SrvAddTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem taskItem) {
    taskItem.setIdentityDocumentType(iddType(identityDocument.getType()));
    taskItem.setSeries(identityDocument.getSeries());
    taskItem.setNumber(identityDocument.getNumber());
    taskItem.setIssuedBy(identityDocument.getIssuedBy());
    taskItem.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(OperationTaskAccountDeposit task,
      SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem taskItem) {
    taskItem.setOVNUId(task.getOvnUid());
    taskItem.setOVNNum(bigIntegerValue(task.getOvnNum()));
    taskItem.setOVNStatus(ovnStatusType(task.getOvnStatus()));
    taskItem.setRepID(task.getRepresentativeId());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setKPP(task.getKpp());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setSenderAccountId(task.getSenderAccountId());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setRecipientBank(task.getRecipientBank());
    taskItem.setRecipientBankBIC(task.getRecipientBankBic());
    taskItem.setSource(task.getSource());
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCause(task.getCause());
    taskItem.setIsAuthСaporMutFund(task.isAuthCaporMutFund());
    taskItem.setCashSymbols(new SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
        .CashSymbols());
    for (CashSymbol cashSymbol : task.getCashSymbols()) {
      SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem.CashSymbols.CashSymbolItem
          cashSymbolItem = new SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
          .CashSymbols.CashSymbolItem();
      map(cashSymbol, cashSymbolItem);
      taskItem.getCashSymbols().getCashSymbolItem().add(cashSymbolItem);
    }
    taskItem.setStockholderInfo(new SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
        .StockholderInfo());
    for (Stockholder stockholder : task.getStockholders()) {
      SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem.StockholderInfo.Stockholderitem
          stockholderItem = new SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
          .StockholderInfo.Stockholderitem();
      map(stockholder, stockholderItem);
      taskItem.getStockholderInfo().getStockholderitem().add(stockholderItem);
    }
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(CashSymbol cashSymbol, SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub
      .TaskItem.CashSymbols.CashSymbolItem cashSymbolItem) {
    cashSymbolItem.setCashSymbol(cashSymbol.getCode());
    cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
  }

  private static void map(Stockholder stockholder, SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub
      .TaskItem.StockholderInfo.Stockholderitem stockholderItem) {
    stockholderItem.setStockholderName(stockholder.getName());
    stockholderItem.setShare(stockholder.getShare());
  }

  private static void map(OperationTaskAccountWithdraw task,
      SrvAddTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem taskItem) {
    taskItem.setRepFIO(task.getRepFio());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setReason(task.getReason());
    map(task.getIdentityDocument(), taskItem);
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCause(task.getCause());
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(IdentityDocument identityDocument,
      SrvAddTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem taskItem) {
    taskItem.setIdentityDocumentType(iddType(identityDocument.getType()));
    taskItem.setSeries(identityDocument.getSeries());
    taskItem.setNumber(identityDocument.getNumber());
    taskItem.setIssuedBy(identityDocument.getIssuedBy());
    taskItem.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(OperationTaskCheckbookIssuing task,
      SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem taskItem) {
    taskItem.setRepFIO(task.getRepFio());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setBankName(task.getBankName());
    taskItem.setBankBIC(task.getBankBic());
    taskItem.setSubbranchCompositeCode(task.getSubbranchCompositeCode());
    map(task.getIdentityDocument(), taskItem);
    taskItem.setDueDt(xmlCalendar(task.getDueDate()));
    taskItem.setContainsCheckbook(task.isContainsCheckbook());
    taskItem.setCheckbookInfo(new SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem
        .CheckbookInfo());
    for (Checkbook checkbook : task.getCheckbooks()) {
      SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem.CheckbookInfo.CheckbookItem
          checkbookItem = new SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem.CheckbookInfo
          .CheckbookItem();
      map(checkbook, checkbookItem);
      taskItem.getCheckbookInfo().getCheckbookItem().add(checkbookItem);
    }
    taskItem.setCbRequestStatus(cbRequestStatusType(task.getRequestStatus()));
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(IdentityDocument identityDocument,
      SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem taskItem) {
    taskItem.setIdentityDocumentType(iddType(identityDocument.getType()));
    taskItem.setSeries(identityDocument.getSeries());
    taskItem.setNumber(identityDocument.getNumber());
    taskItem.setIssuedBy(identityDocument.getIssuedBy());
    taskItem.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(Checkbook checkbook, SrvAddTaskClOperPkgRqMessage.CheckbookIssuing
      .TaskItem.CheckbookInfo.CheckbookItem checkbookItem) {
    checkbookItem.setCheckbookId(checkbook.getId());
    checkbookItem.setFirstCheckId(checkbook.getFirstCheckId());
    checkbookItem.setCbPageAmount(BigInteger.valueOf(checkbook.getCheckCount()));
  }

  //******** SrvAddTaskClOperPkgRs ********

  private static void map(SrvAddTaskClOperPkgRs response, OperationPackage pakage) {
    SrvAddTaskClOperPkgRsMessage message = response.getSrvAddTaskClOperPkgRsMessage();
    pakage.setId(message.getPkgId());

    if (message.getToCardDeposit() != null) {
      for (SrvAddTaskClOperPkgRsMessage.ToCardDeposit.TaskItem taskItem :
          message.getToCardDeposit().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getToCardDeposits().add(operationTask);
      }
    }

    if (message.getFromCardWithdraw() != null) {
      for (SrvAddTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem taskItem :
          message.getFromCardWithdraw().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getFromCardWithdraws().add(operationTask);
      }
    }

    if (message.getToAccountDepositRub() != null) {
      for (SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem taskItem :
          message.getToAccountDepositRub().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getToAccountDeposits().add(operationTask);
      }
    }

    if (message.getFromAccountWithdrawRub() != null) {
      for (SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem taskItem :
          message.getFromAccountWithdrawRub().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getFromAccountWithdraws().add(operationTask);
      }
    }

    if (message.getCheckbookIssuing() != null) {
      for (SrvAddTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem taskItem :
          message.getCheckbookIssuing().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getCheckbookIssuing().add(operationTask);
      }
    }
  }

  private static void map(SrvAddTaskClOperPkgRsMessage.ToCardDeposit.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvAddTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvAddTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  //******** SrvCheckClOperPkg ********

  private static void map(OperationPackageRequest request, SrvCheckClOperPkgRqMessage message) {
    message.setWorkPlaceUId(request.getWorkPlaceUid());
    message.setINN(request.getInn());
  }

  private static void map(SrvCheckClOperPkgRsMessage message, OperationPackage pakage) {
    if (message.isPkgExistsFlg()) {
      pakage.setId(message.getPkgId());
      pakage.setStatus(operationPackageStatus(message.getPkgStatus()));
    }
  }

  //******** SrvCreateClOperPkg ********

  private static void map(OperationPackageRequest request, SrvCreateClOperPkgRqMessage message) {
    message.setWorkPlaceUId(request.getWorkPlaceUid());
    message.setINN(request.getInn());
    message.setUserLogin(request.getUserLogin());
  }

  private static void map(SrvCreateClOperPkgRsMessage message, OperationPackage pakage) {
    pakage.setId(message.getPkgId());
    pakage.setStatus(operationPackageStatus(message.getPkgStatus()));
    pakage.setResponseCode(longValue(message.getResponseCode()));
  }

  //******** SrvGetTaskClOperPkg ********

  private static void map(OperationTasksRequest request, SrvGetTaskClOperPkgRqMessage message) {
    message.setPkgId(request.getPackageId());
    message.setTaskStatusGlobal(pkgTaskStatusType(request.getTaskStatusGlobal()));
    message.setDateFrom(xmlCalendar(request.getFromDate()));
    message.setDateTo(xmlCalendar(request.getToDate()));

    for (OperationTask task : request.getTasks()) {
      SrvGetTaskClOperPkgRqMessage.TaskType taskType = new SrvGetTaskClOperPkgRqMessage.TaskType();
      taskType.setPkgTaskId(task.getId());
      taskType.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
      message.getTaskType().add(taskType);
    }
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem packageItem,
      HeaderInfoType headerInfo, OperationPackage pakage) {
    pakage.setId(packageItem.getPkgId());
    pakage.setUserLogin(packageItem.getUserLogin());

    if (packageItem.getToCardDeposit() != null) {
      for (SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem taskItem :
          packageItem.getToCardDeposit().getTaskItem()) {
        OperationTaskCardDeposit operationTask = new OperationTaskCardDeposit();
        map(taskItem, operationTask);
        map(headerInfo, operationTask);
        pakage.getToCardDeposits().add(operationTask);
      }
    }

    if (packageItem.getFromCardWithdraw() != null) {
      for (SrvGetTaskClOperPkgRsMessage.PkgItem.FromCardWithdraw.TaskItem taskItem :
          packageItem.getFromCardWithdraw().getTaskItem()) {
        OperationTaskCardWithdraw operationTask = new OperationTaskCardWithdraw();
        map(taskItem, operationTask);
        map(headerInfo, operationTask);
        pakage.getFromCardWithdraws().add(operationTask);
      }
    }

    if (packageItem.getToAccountDepositRub() != null) {
      for (SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem taskItem :
          packageItem.getToAccountDepositRub().getTaskItem()) {
        OperationTaskAccountDeposit operationTask = new OperationTaskAccountDeposit();
        map(taskItem, operationTask);
        map(headerInfo, operationTask);
        pakage.getToAccountDeposits().add(operationTask);
      }
    }

    if (packageItem.getFromAccountWithdrawRub() != null) {
      for (SrvGetTaskClOperPkgRsMessage.PkgItem.FromAccountWithdrawRub.TaskItem taskItem :
          packageItem.getFromAccountWithdrawRub().getTaskItem()) {
        OperationTaskAccountWithdraw operationTask = new OperationTaskAccountWithdraw();
        map(taskItem, operationTask);
        map(headerInfo, operationTask);
        pakage.getFromAccountWithdraws().add(operationTask);
      }
    }

    if (packageItem.getCheckbookIssuing() != null) {
      for (SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem taskItem :
          packageItem.getCheckbookIssuing().getTaskItem()) {
        OperationTaskCheckbookIssuing operationTask = new OperationTaskCheckbookIssuing();
        map(taskItem, operationTask);
        map(headerInfo, operationTask);
        pakage.getCheckbookIssuing().add(operationTask);
      }
    }
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem taskItem,
      OperationTaskCardDeposit task) {
    task.setId(taskItem.getPkgTaskId());
    task.setOvnUid(taskItem.getOVNUId());
    task.setOvnNum(longValue(taskItem.getOVNNum()));
    task.setOvnStatus(ovnStatus(taskItem.getOVNStatus()));
    task.setRepresentativeId(taskItem.getRepID());
    task.setLegalEntityShortName(taskItem.getLegalEntityShortName());
    task.setAmount(taskItem.getAmount());
    task.setCreatedDate(date(taskItem.getCreatedDttm()));
    task.setInn(taskItem.getINN());
    task.setKpp(taskItem.getKPP());
    task.setAccountId(taskItem.getAccountId());
    map(taskItem.getCardInfo(), task.getCard());
    task.setSenderAccountId(taskItem.getSenderAccountId());
    task.setSenderBank(taskItem.getSenderBank());
    task.setSenderBankBic(taskItem.getSenderBankBIC());
    task.setRecipientBank(taskItem.getRecipientBank());
    task.setRecipientBankBic(taskItem.getRecipientBankBIC());
    task.setSource(taskItem.getSource());
    task.setClientTypeFk(taskItem.isIsClientTypeFK());
    task.setOrganisationNameFk(taskItem.getFDestLEName());
    task.setPersonalAccountId(taskItem.getPersonalAccountId());
    task.setCurrencyType(taskItem.getCurrencyType());
    for (SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.CashSymbols.CashSymbolItem
        cashSymbolItem : taskItem.getCashSymbols().getCashSymbolItem()) {
      CashSymbol cashSymbol = new CashSymbol();
      map(cashSymbolItem, cashSymbol);
      task.getCashSymbols().add(cashSymbol);
    }
    task.setStatus(operationTaskStatus(taskItem.getPkgTaskStatus()));
    task.setChangedDate(date(taskItem.getChangedDttm()));
    task.setStatusChangedDate(date(taskItem.getStatusChangedDttm()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.CardInfo
      cardInfo, Card card) {
    card.setNumber(cardInfo.getCardNumber());
    card.setExpiryDate(date(cardInfo.getCardExpDate()));
    card.setIssuingNetworkCode(cardNetworkCode(cardInfo.getCardIssuingNetworkCode()));
    card.setType(cardType(cardInfo.getCardTypeId()));
    card.setOwnerFirstName(cardInfo.getCardOwnerFirstName());
    card.setOwnerLastName(cardInfo.getCardOwnerLastName());
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.CashSymbols
      .CashSymbolItem cashSymbolItem, CashSymbol cashSymbol) {
    cashSymbol.setCode(cashSymbolItem.getCashSymbol());
    cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.FromCardWithdraw.TaskItem taskItem,
      OperationTaskCardWithdraw task) {
    task.setId(taskItem.getPkgTaskId());
    task.setRepFio(taskItem.getRepFIO());
    task.setLegalEntityShortName(taskItem.getLegalEntityShortName());
    task.setAmount(taskItem.getAmount());
    task.setCreatedDate(date(taskItem.getCreatedDttm()));
    task.setInn(taskItem.getINN());
    task.setAccountId(taskItem.getAccountId());
    task.setCardNumber(taskItem.getCardNumber());
    task.setSenderBank(taskItem.getSenderBank());
    task.setSenderBankBic(taskItem.getSenderBankBIC());
    task.setReason(taskItem.getReason());
    task.setIdentityDocument(new IdentityDocument());
    map(taskItem, task.getIdentityDocument());
    task.setClientTypeFk(taskItem.isIsClientTypeFK());
    task.setOrganisationNameFk(taskItem.getFDestLEName());
    task.setPersonalAccountId(taskItem.getPersonalAccountId());
    task.setCurrencyType(taskItem.getCurrencyType());
    task.setCause(taskItem.getCause());
    task.setStatus(operationTaskStatus(taskItem.getPkgTaskStatus()));
    task.setChangedDate(date(taskItem.getChangedDttm()));
    task.setStatusChangedDate(date(taskItem.getStatusChangedDttm()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.FromCardWithdraw.TaskItem taskItem,
      IdentityDocument identityDocument) {
    identityDocument.setType(identityDocumentType(taskItem.getIdentityDocumentType()));
    identityDocument.setSeries(taskItem.getSeries());
    identityDocument.setNumber(taskItem.getNumber());
    identityDocument.setIssuedBy(taskItem.getIssuedBy());
    identityDocument.setIssuedDate(date(taskItem.getIssuedDate()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem
      taskItem, OperationTaskAccountDeposit task) {
    task.setId(taskItem.getPkgTaskId());
    task.setOvnUid(taskItem.getOVNUId());
    task.setOvnNum(longValue(taskItem.getOVNNum()));
    task.setOvnStatus(ovnStatus(taskItem.getOVNStatus()));
    task.setRepresentativeId(taskItem.getRepID());
    task.setLegalEntityShortName(taskItem.getLegalEntityShortName());
    task.setAmount(taskItem.getAmount());
    task.setCreatedDate(date(taskItem.getCreatedDttm()));
    task.setInn(taskItem.getINN());
    task.setKpp(taskItem.getKPP());
    task.setAccountId(taskItem.getAccountId());
    task.setSenderAccountId(taskItem.getSenderAccountId());
    task.setSenderBank(taskItem.getSenderBank());
    task.setSenderBankBic(taskItem.getSenderBankBIC());
    task.setRecipientBank(taskItem.getRecipientBank());
    task.setRecipientBankBic(taskItem.getRecipientBankBIC());
    task.setSource(taskItem.getSource());
    task.setClientTypeFk(taskItem.isIsClientTypeFK());
    task.setOrganisationNameFk(taskItem.getFDestLEName());
    task.setPersonalAccountId(taskItem.getPersonalAccountId());
    task.setCurrencyType(taskItem.getCurrencyType());
    task.setCause(taskItem.getCause());
    task.setAuthCaporMutFund(taskItem.isIsAuthСaporMutFund());
    for (CashSymbolItem cashSymbolItem : taskItem.getCashSymbols().getCashSymbolItem()) {
      CashSymbol cashSymbol = new CashSymbol();
      map(cashSymbolItem, cashSymbol);
      task.getCashSymbols().add(cashSymbol);
    }
    for (Stockholderitem stockholderItem : taskItem.getStockholderInfo().getStockholderitem()) {
      Stockholder stockholder = new Stockholder();
      map(stockholderItem, stockholder);
      task.getStockholders().add(stockholder);
    }
    task.setStatus(operationTaskStatus(taskItem.getPkgTaskStatus()));
    task.setChangedDate(date(taskItem.getChangedDttm()));
    task.setStatusChangedDate(date(taskItem.getStatusChangedDttm()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem
      .CashSymbols.CashSymbolItem cashSymbolItem, CashSymbol cashSymbol) {
    cashSymbol.setCode(cashSymbolItem.getCashSymbol());
    cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem
      .StockholderInfo.Stockholderitem stockholderItem, Stockholder stockholder) {
    stockholder.setName(stockholderItem.getStockholderName());
    stockholder.setShare(stockholderItem.getShare());
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.FromAccountWithdrawRub.TaskItem
      taskItem, OperationTaskAccountWithdraw task) {
    task.setId(taskItem.getPkgTaskId());
    task.setRepFio(taskItem.getRepFIO());
    task.setLegalEntityShortName(taskItem.getLegalEntityShortName());
    task.setAmount(taskItem.getAmount());
    task.setCreatedDate(date(taskItem.getCreatedDttm()));
    task.setInn(taskItem.getINN());
    task.setAccountId(taskItem.getAccountId());
    task.setSenderBank(taskItem.getSenderBank());
    task.setSenderBankBic(taskItem.getSenderBankBIC());
    task.setReason(taskItem.getReason());
    task.setIdentityDocument(new IdentityDocument());
    map(taskItem, task.getIdentityDocument());
    task.setClientTypeFk(taskItem.isIsClientTypeFK());
    task.setOrganisationNameFk(taskItem.getFDestLEName());
    task.setPersonalAccountId(taskItem.getPersonalAccountId());
    task.setCurrencyType(taskItem.getCurrencyType());
    task.setCause(taskItem.getCause());
    task.setStatus(operationTaskStatus(taskItem.getPkgTaskStatus()));
    task.setChangedDate(date(taskItem.getChangedDttm()));
    task.setStatusChangedDate(date(taskItem.getStatusChangedDttm()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.FromAccountWithdrawRub.TaskItem
      taskItem, IdentityDocument identityDocument) {
    identityDocument.setType(identityDocumentType(taskItem.getIdentityDocumentType()));
    identityDocument.setSeries(taskItem.getSeries());
    identityDocument.setNumber(taskItem.getNumber());
    identityDocument.setIssuedBy(taskItem.getIssuedBy());
    identityDocument.setIssuedDate(date(taskItem.getIssuedDate()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem taskItem,
      OperationTaskCheckbookIssuing task) {
    task.setId(taskItem.getPkgTaskId());
    task.setRepFio(taskItem.getRepFIO());
    task.setLegalEntityShortName(taskItem.getLegalEntityShortName());
    task.setCreatedDate(date(taskItem.getCreatedDttm()));
    task.setInn(taskItem.getINN());
    task.setAccountId(taskItem.getAccountId());
    task.setBankName(taskItem.getBankName());
    task.setBankBic(taskItem.getBankBIC());
    task.setSubbranchCompositeCode(taskItem.getSubbranchCompositeCode());
    task.setIdentityDocument(new IdentityDocument());
    map(taskItem, task.getIdentityDocument());
    task.setDueDate(date(taskItem.getDueDt()));
    task.setContainsCheckbook(taskItem.isContainsCheckbook());
    for (SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem.CheckbookInfo.CheckbookItem
        checkbookItem : taskItem.getCheckbookInfo().getCheckbookItem()) {
      Checkbook checkbook = new Checkbook();
      map(checkbookItem, checkbook);
      task.getCheckbooks().add(checkbook);
    }
    task.setRequestId(taskItem.getCbRequestId());
    task.setRequestStatus(checkbookRequestStatus(taskItem.getCbRequestStatus()));
    task.setStatus(operationTaskStatus(taskItem.getPkgTaskStatus()));
    task.setChangedDate(date(taskItem.getChangedDttm()));
    task.setStatusChangedDate(date(taskItem.getStatusChangedDttm()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem taskItem,
      IdentityDocument identityDocument) {
    identityDocument.setType(identityDocumentType(taskItem.getIdentityDocumentType()));
    identityDocument.setSeries(taskItem.getSeries());
    identityDocument.setNumber(taskItem.getNumber());
    identityDocument.setIssuedBy(taskItem.getIssuedBy());
    identityDocument.setIssuedDate(date(taskItem.getIssuedDate()));
  }

  private static void map(SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem
      .CheckbookInfo.CheckbookItem checkbookItem, Checkbook checkbook) {
    checkbook.setId(checkbookItem.getCheckbookId());
    checkbook.setFirstCheckId(checkbookItem.getFirstCheckId());
    checkbook.setCheckCount(longValue(checkbookItem.getCbPageAmount()));
  }

  //******** SrvUpdTaskClOperPkgRq ********

  private static void map(OperationPackage pakage, SrvUpdTaskClOperPkgRqMessage message) {
    message.setPkgId(pakage.getId());
    message.setToCardDeposit(new SrvUpdTaskClOperPkgRqMessage.ToCardDeposit());
    message.setFromCardWithdraw(new SrvUpdTaskClOperPkgRqMessage.FromCardWithdraw());
    message.setToAccountDepositRub(new SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub());
    message.setFromAccountWithdrawRub(new SrvUpdTaskClOperPkgRqMessage.FromAccountWithdrawRub());
    message.setCheckbookIssuing(new SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing());

    for (OperationTask task : pakage.getToCardDeposits()) {
      if (task instanceof OperationTaskCardDeposit) {
        SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem taskItem =
            new SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem();
        map((OperationTaskCardDeposit) task, taskItem);
        message.getToCardDeposit().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getFromCardWithdraws()) {
      if (task instanceof OperationTaskCardWithdraw) {
        SrvUpdTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem taskItem =
            new SrvUpdTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem();
        map((OperationTaskCardWithdraw) task, taskItem);
        message.getFromCardWithdraw().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getToAccountDeposits()) {
      if (task instanceof OperationTaskAccountDeposit) {
        SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem taskItem =
            new SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem();
        map((OperationTaskAccountDeposit) task, taskItem);
        message.getToAccountDepositRub().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getFromAccountWithdraws()) {
      if (task instanceof OperationTaskAccountWithdraw) {
        SrvUpdTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem taskItem =
            new SrvUpdTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem();
        map((OperationTaskAccountWithdraw) task, taskItem);
        message.getFromAccountWithdrawRub().getTaskItem().add(taskItem);
      }
    }

    for (OperationTask task : pakage.getCheckbookIssuing()) {
      if (task instanceof OperationTaskCheckbookIssuing) {
        SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem taskItem =
            new SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem();
        map((OperationTaskCheckbookIssuing) task, taskItem);
        message.getCheckbookIssuing().getTaskItem().add(taskItem);
      }
    }
  }

  private static void map(OperationTaskCardDeposit task,
      SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem taskItem) {
    taskItem.setPkgTaskId(task.getId());
    taskItem.setOVNUId(task.getOvnUid());
    taskItem.setOVNNum(bigIntegerValue(task.getOvnNum()));
    taskItem.setOVNStatus(ovnStatusType(task.getOvnStatus()));
    taskItem.setRepID(task.getRepresentativeId());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setINN(task.getInn());
    taskItem.setKPP(task.getKpp());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setCardInfo(new SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CardInfo());
    map(task.getCard(), taskItem.getCardInfo());
    taskItem.setSenderAccountId(task.getSenderAccountId());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setRecipientAccountId(task.getRecipientAccountId());
    taskItem.setRecipientBank(task.getRecipientBank());
    taskItem.setRecipientBankBIC(task.getRecipientBankBic());
    taskItem.setSource(task.getSource());
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCashSymbols(new SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CashSymbols());
    for (CashSymbol cashSymbol : task.getCashSymbols()) {
      SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CashSymbols.CashSymbolItem cashSymbolItem
          = new SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CashSymbols.CashSymbolItem();
      map(cashSymbol, cashSymbolItem);
      taskItem.getCashSymbols().getCashSymbolItem().add(cashSymbolItem);
    }
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(Card card,
      SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem.CardInfo cardInfo) {
    cardInfo.setCardNumber(card.getNumber());
    cardInfo.setCardExpDate(xmlCalendar(card.getExpiryDate()));
    cardInfo.setCardIssuingNetworkCode(cardNetworkCodeId(card.getIssuingNetworkCode()));
    cardInfo.setCardTypeId(cardTypeId(card.getType()));
    cardInfo.setCardOwnerFirstName(card.getOwnerFirstName());
    cardInfo.setCardOwnerLastName(card.getOwnerLastName());
  }

  private static void map(CashSymbol cashSymbol, SrvUpdTaskClOperPkgRqMessage.ToCardDeposit
      .TaskItem.CashSymbols.CashSymbolItem cashSymbolItem) {
    cashSymbolItem.setCashSymbol(cashSymbol.getCode());
    cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
  }

  private static void map(OperationTaskCardWithdraw task,
      SrvUpdTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem taskItem) {
    taskItem.setPkgTaskId(task.getId());
    taskItem.setRepFIO(task.getRepFio());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setCardNumber(task.getCardNumber());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setReason(task.getReason());
    map(task.getIdentityDocument(), taskItem);
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCause(task.getCause());
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(IdentityDocument identityDocument,
      SrvUpdTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem taskItem) {
    taskItem.setIdentityDocumentType(iddType(identityDocument.getType()));
    taskItem.setSeries(identityDocument.getSeries());
    taskItem.setNumber(identityDocument.getNumber());
    taskItem.setIssuedBy(identityDocument.getIssuedBy());
    taskItem.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(OperationTaskAccountDeposit task,
      SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem taskItem) {
    taskItem.setPkgTaskId(task.getId());
    taskItem.setOVNUId(task.getOvnUid());
    taskItem.setOVNNum(bigIntegerValue(task.getOvnNum()));
    taskItem.setOVNStatus(ovnStatusType(task.getOvnStatus()));
    taskItem.setRepID(task.getRepresentativeId());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setKPP(task.getKpp());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setSenderAccountId(task.getSenderAccountId());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setRecipientBank(task.getRecipientBank());
    taskItem.setRecipientBankBIC(task.getRecipientBankBic());
    taskItem.setSource(task.getSource());
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCause(task.getCause());
    taskItem.setIsAuthСaporMutFund(task.isAuthCaporMutFund());
    taskItem.setCashSymbols(new SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
        .CashSymbols());
    for (CashSymbol cashSymbol : task.getCashSymbols()) {
      SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem.CashSymbols.CashSymbolItem
          cashSymbolItem = new SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
          .CashSymbols.CashSymbolItem();
      map(cashSymbol, cashSymbolItem);
      taskItem.getCashSymbols().getCashSymbolItem().add(cashSymbolItem);
    }
    taskItem.setStockholderInfo(new SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
        .StockholderInfo());
    for (Stockholder stockholder : task.getStockholders()) {
      SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem.StockholderInfo.Stockholderitem
          stockholderItem = new SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem
          .StockholderInfo.Stockholderitem();
      map(stockholder, stockholderItem);
      taskItem.getStockholderInfo().getStockholderitem().add(stockholderItem);
    }
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(CashSymbol cashSymbol, SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub
      .TaskItem.CashSymbols.CashSymbolItem cashSymbolItem) {
    cashSymbolItem.setCashSymbol(cashSymbol.getCode());
    cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
  }

  private static void map(Stockholder stockholder, SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub
      .TaskItem.StockholderInfo.Stockholderitem stockholderItem) {
    stockholderItem.setStockholderName(stockholder.getName());
    stockholderItem.setShare(stockholder.getShare());
  }

  private static void map(OperationTaskAccountWithdraw task,
      SrvUpdTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem taskItem) {
    taskItem.setPkgTaskId(task.getId());
    taskItem.setRepFIO(task.getRepFio());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setAmount(task.getAmount());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setSenderBank(task.getSenderBank());
    taskItem.setSenderBankBIC(task.getSenderBankBic());
    taskItem.setReason(task.getReason());
    map(task.getIdentityDocument(), taskItem);
    taskItem.setIsClientTypeFK(task.isClientTypeFk());
    taskItem.setFDestLEName(task.getOrganisationNameFk());
    taskItem.setPersonalAccountId(task.getPersonalAccountId());
    taskItem.setCurrencyType(task.getCurrencyType());
    taskItem.setCause(task.getCause());
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(IdentityDocument identityDocument,
      SrvUpdTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem taskItem) {
    taskItem.setIdentityDocumentType(iddType(identityDocument.getType()));
    taskItem.setSeries(identityDocument.getSeries());
    taskItem.setNumber(identityDocument.getNumber());
    taskItem.setIssuedBy(identityDocument.getIssuedBy());
    taskItem.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(OperationTaskCheckbookIssuing task,
      SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem taskItem) {
    taskItem.setPkgTaskId(task.getId());
    taskItem.setCbRequestId(task.getRequestId());
    taskItem.setRepFIO(task.getRepFio());
    taskItem.setLegalEntityShortName(task.getLegalEntityShortName());
    taskItem.setCreatedDttm(xmlCalendar(task.getCreatedDate()));
    taskItem.setINN(task.getInn());
    taskItem.setAccountId(task.getAccountId());
    taskItem.setBankName(task.getBankName());
    taskItem.setBankBIC(task.getBankBic());
    taskItem.setSubbranchCompositeCode(task.getSubbranchCompositeCode());
    map(task.getIdentityDocument(), taskItem);
    taskItem.setDueDt(xmlCalendar(task.getDueDate()));
    taskItem.setContainsCheckbook(task.isContainsCheckbook());
    taskItem.setCheckbookInfo(new SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem
        .CheckbookInfo());
    for (Checkbook checkbook : task.getCheckbooks()) {
      SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem.CheckbookInfo.CheckbookItem
          checkbookItem = new SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem.CheckbookInfo
          .CheckbookItem();
      map(checkbook, checkbookItem);
      taskItem.getCheckbookInfo().getCheckbookItem().add(checkbookItem);
    }
    taskItem.setCbRequestStatus(cbRequestStatusType(task.getRequestStatus()));
    taskItem.setPkgTaskStatus(pkgTaskStatusType(task.getStatus()));
  }

  private static void map(IdentityDocument identityDocument,
      SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem taskItem) {
    taskItem.setIdentityDocumentType(iddType(identityDocument.getType()));
    taskItem.setSeries(identityDocument.getSeries());
    taskItem.setNumber(identityDocument.getNumber());
    taskItem.setIssuedBy(identityDocument.getIssuedBy());
    taskItem.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(Checkbook checkbook, SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing
      .TaskItem.CheckbookInfo.CheckbookItem checkbookItem) {
    checkbookItem.setCheckbookId(checkbook.getId());
    checkbookItem.setFirstCheckId(checkbook.getFirstCheckId());
    checkbookItem.setCbPageAmount(BigInteger.valueOf(checkbook.getCheckCount()));
  }

  //******** SrvUpdTaskClOperPkgRs ********

  private static void map(SrvUpdTaskClOperPkgRs response, OperationPackage pakage) {
    SrvUpdTaskClOperPkgRsMessage message = response.getSrvUpdTaskClOperPkgRsMessage();
    pakage.setId(message.getPkgId());
    pakage.setToCardDeposits(new ArrayList<OperationTask>());
    pakage.setFromCardWithdraws(new ArrayList<OperationTask>());

    if (message.getToCardDeposit() != null) {
      for (SrvUpdTaskClOperPkgRsMessage.ToCardDeposit.TaskItem taskItem :
          message.getToCardDeposit().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getToCardDeposits().add(operationTask);
      }
    }

    if (message.getFromCardWithdraw() != null) {
      for (SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem taskItem :
          message.getFromCardWithdraw().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getFromCardWithdraws().add(operationTask);
      }
    }

    if (message.getToAccountDepositRub() != null) {
      for (SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem taskItem :
          message.getToAccountDepositRub().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getToAccountDeposits().add(operationTask);
      }
    }

    if (message.getFromAccountWithdrawRub() != null) {
      for (SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem taskItem :
          message.getFromAccountWithdrawRub().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getFromAccountWithdraws().add(operationTask);
      }
    }

    if (message.getCheckbookIssuing() != null) {
      for (SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem taskItem :
          message.getCheckbookIssuing().getTaskItem()) {
        OperationTask operationTask = new OperationTask();
        map(taskItem, operationTask);
        map(response.getHeaderInfo(), operationTask);
        pakage.getCheckbookIssuing().add(operationTask);
      }
    }
  }

  private static void map(SrvUpdTaskClOperPkgRsMessage.ToCardDeposit.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  private static void map(SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem taskItem,
      OperationTask task) {
    task.setId(taskItem.getPkgTaskId());
    task.setResponseCode(longValue(taskItem.getResponseCode()));
  }

  //******** Methods *******

  /**
   * Возвращает объект запроса на добавление операций в пакет.
   */
  public static SrvAddTaskClOperPkgRq requestAddTasks(OperationPackage operationPackage) {
    SrvAddTaskClOperPkgRq request = new SrvAddTaskClOperPkgRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvAddTaskClOperPkgRqMessage(new SrvAddTaskClOperPkgRqMessage());
    map(operationPackage, request.getSrvAddTaskClOperPkgRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса проверки пакета.
   */
  public static SrvCheckClOperPkgRq requestCheckPackage(OperationPackageRequest opRequest) {
    SrvCheckClOperPkgRq request = new SrvCheckClOperPkgRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCheckClOperPkgRqMessage(new SrvCheckClOperPkgRqMessage());
    map(opRequest, request.getSrvCheckClOperPkgRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса добавления пакета.
   */
  public static SrvCreateClOperPkgRq requestCreatePackage(OperationPackageRequest opRequest) {
    SrvCreateClOperPkgRq request = new SrvCreateClOperPkgRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateClOperPkgRqMessage(new SrvCreateClOperPkgRqMessage());
    map(opRequest, request.getSrvCreateClOperPkgRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса списка операций.
   */
  public static SrvGetTaskClOperPkgRq requestTasks(OperationTasksRequest otRequest) {
    SrvGetTaskClOperPkgRq request = new SrvGetTaskClOperPkgRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetTaskClOperPkgRqMessage(new SrvGetTaskClOperPkgRqMessage());
    map(otRequest, request.getSrvGetTaskClOperPkgRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса на редактирование операций.
   */
  public static SrvUpdTaskClOperPkgRq requestUpdateTasks(OperationPackage operationPackage) {
    SrvUpdTaskClOperPkgRq request = new SrvUpdTaskClOperPkgRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdTaskClOperPkgRqMessage(new SrvUpdTaskClOperPkgRqMessage());
    map(operationPackage, request.getSrvUpdTaskClOperPkgRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект ответа добавления операций во внутреннюю сущность.
   */
  public static OperationPackage convert(SrvAddTaskClOperPkgRs response) {
    OperationPackage operationPackage = new OperationPackage();
    map(response.getHeaderInfo(), operationPackage);
    map(response, operationPackage);
    return operationPackage;
  }

  /**
   * Преобразует транспортный объект ответа проверки пакета во внутреннюю сущность.
   */
  public static OperationPackage convert(SrvCheckClOperPkgRs response) {
    OperationPackage operationPackage = new OperationPackage();
    map(response.getHeaderInfo(), operationPackage);
    map(response.getSrvCheckClOperPkgRsMessage(), operationPackage);
    return operationPackage;
  }

  /**
   * Преобразует транспортный объект ответа создания пакета во внутреннюю сущность.
   */
  public static OperationPackage convert(SrvCreateClOperPkgRs response) {
    OperationPackage operationPackage = new OperationPackage();
    map(response.getHeaderInfo(), operationPackage);
    map(response.getSrvCreateClOperPkgRsMessage(), operationPackage);
    return operationPackage;
  }

  /**
   * Преобразует транспортный объект списка операций во внутреннюю сущность.
   */
  public static ExternalEntityList<OperationPackage> convert(SrvGetTaskClOperPkgRs response) {
    ExternalEntityList<OperationPackage> packageList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), packageList);

    for (SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage.PkgItem packageItem :
        response.getSrvGetTaskClOperPkgRsMessage().getPkgItem()) {
      OperationPackage operationPackage = new OperationPackage();
      map(response.getHeaderInfo(), operationPackage);
      map(packageItem, response.getHeaderInfo(), operationPackage);
      packageList.getItems().add(operationPackage);
    }
    return packageList;
  }

  /**
   * Преобразует транспортный объект ответа редактирования операций во внутреннюю сущность.
   */
  public static OperationPackage convert(SrvUpdTaskClOperPkgRs response) {
    OperationPackage operationPackage = new OperationPackage();
    map(response.getHeaderInfo(), operationPackage);
    map(response, operationPackage);
    return operationPackage;
  }
}
