package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.CardNetworkCode;
import ru.philit.ufs.model.entity.account.CardType;
import ru.philit.ufs.model.entity.account.Checkbook;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.eks.CbRequestStatusType;
import ru.philit.ufs.model.entity.esb.eks.IDDtype;
import ru.philit.ufs.model.entity.esb.eks.OVNStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs.SrvAddTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs.SrvCheckClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs.SrvCreateClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRq;
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

public class OperationPackageAdapterTest extends EksAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-41f7-912c-4c16ca78e969";

  private OperationPackage operationPackage1;
  private SrvAddTaskClOperPkgRs response1;
  private OperationPackageRequest opRequest2;
  private SrvCheckClOperPkgRs response2;
  private OperationPackageRequest opRequest3;
  private SrvCreateClOperPkgRs response3;
  private OperationTasksRequest otRequest4;
  private SrvGetTaskClOperPkgRs response4;
  private OperationPackage operationPackage5;
  private SrvUpdTaskClOperPkgRs response5;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    operationPackage1 = new OperationPackage();
    operationPackage1.setId(21L);
    OperationTaskCardDeposit task111 = new OperationTaskCardDeposit();
    task111.setOvnUid("112233");
    task111.setOvnNum(5L);
    task111.setOvnStatus(OvnStatus.NEW);
    task111.setRepresentativeId("98971461998295679");
    task111.setLegalEntityShortName("short name");
    task111.setAmount(BigDecimal.valueOf(200));
    task111.setCreatedDate(date(2017, 4, 28, 13, 45));
    task111.setInn("123345667899");
    task111.setKpp("44762383");
    task111.setAccountId("111111");
    task111.setSenderAccountId("157485");
    task111.setSenderBank("Bank1");
    task111.setSenderBankBic("043876396");
    task111.setRecipientAccountId("163556");
    task111.setRecipientBank("Bank2");
    task111.setRecipientBankBic("043876166");
    task111.setSource("top secret");
    task111.setClientTypeFk(true);
    task111.setOrganisationNameFk("Secret organization");
    task111.setPersonalAccountId("333");
    task111.setCurrencyType("RUB");
    task111.setStatus(OperationTaskStatus.ACTIVE);
    task111.getCard().setNumber("4894123569871254");
    task111.getCard().setExpiryDate(date(2017, 9, 17, 0, 0));
    task111.getCard().setIssuingNetworkCode(CardNetworkCode.MASTER_CARD);
    task111.getCard().setType(CardType.CREDIT);
    task111.getCard().setOwnerFirstName("PETR");
    task111.getCard().setOwnerLastName("PETROV");
    CashSymbol cashSymbol111 = new CashSymbol();
    cashSymbol111.setCode("02");
    cashSymbol111.setDescription("Символ 02");
    cashSymbol111.setAmount(BigDecimal.valueOf(200));
    task111.getCashSymbols().add(cashSymbol111);
    operationPackage1.getToCardDeposits().add(task111);

    OperationTaskCardWithdraw task121 = new OperationTaskCardWithdraw();
    task121.setRepFio("Иванов Иван Никифорович");
    task121.setLegalEntityShortName("short name2");
    task121.setAmount(BigDecimal.valueOf(300));
    task121.setCreatedDate(date(2017, 4, 28, 14, 0));
    task121.setInn("123345667889");
    task121.setAccountId("112");
    task121.setSenderBank("Bank1");
    task121.setSenderBankBic("043876396");
    task121.setReason("I want this");
    task121.setClientTypeFk(true);
    task121.setOrganisationNameFk("Secret organization2");
    task121.setPersonalAccountId("333");
    task121.setCurrencyType("RUB");
    task121.setCause("cause1");
    task121.setStatus(OperationTaskStatus.ON_APPROVAL);
    task121.setIdentityDocument(new IdentityDocument());
    task121.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    task121.getIdentityDocument().setSeries("0825");
    task121.getIdentityDocument().setNumber("954745");
    task121.getIdentityDocument().setIssuedBy("OVD");
    task121.getIdentityDocument().setIssuedDate(date(2000, 1, 15, 0, 0));
    task121.setCardNumber("4894123566371254");
    operationPackage1.getFromCardWithdraws().add(task111);
    operationPackage1.getFromCardWithdraws().add(task121);

    OperationTaskAccountDeposit task131 = new OperationTaskAccountDeposit();
    task131.setOvnUid("112234");
    task131.setOvnNum(5L);
    task131.setOvnStatus(OvnStatus.NEW);
    task131.setRepresentativeId("98971461998295679");
    task131.setLegalEntityShortName("short name");
    task131.setAmount(BigDecimal.valueOf(200));
    task131.setCreatedDate(date(2017, 4, 28, 13, 45));
    task131.setInn("123345667899");
    task131.setKpp("44762383");
    task131.setAccountId("111111");
    task131.setSenderAccountId("157485");
    task131.setSenderBank("Bank1");
    task131.setSenderBankBic("043876396");
    task131.setRecipientAccountId("163556");
    task131.setRecipientBank("Bank2");
    task131.setRecipientBankBic("043876166");
    task131.setSource("top secret");
    task131.setClientTypeFk(true);
    task131.setOrganisationNameFk("Secret organization");
    task131.setPersonalAccountId("333");
    task131.setCurrencyType("RUB");
    task131.setCause("cause1");
    task131.setAuthCaporMutFund(true);
    task131.setStatus(OperationTaskStatus.ACTIVE);
    CashSymbol cashSymbol131 = new CashSymbol();
    cashSymbol131.setCode("02");
    cashSymbol131.setDescription("Символ 02");
    cashSymbol131.setAmount(BigDecimal.valueOf(200));
    task131.getCashSymbols().add(cashSymbol131);
    Stockholder stockholder131 = new Stockholder();
    stockholder131.setName("Некто");
    stockholder131.setShare(BigDecimal.valueOf(51));
    task131.getStockholders().add(stockholder131);
    operationPackage1.getToAccountDeposits().add(task131);

    OperationTaskAccountWithdraw task141 = new OperationTaskAccountWithdraw();
    task141.setRepFio("Иванов Иван Никифорович");
    task141.setLegalEntityShortName("short name2");
    task141.setAmount(BigDecimal.valueOf(300));
    task141.setCreatedDate(date(2017, 4, 28, 14, 0));
    task141.setInn("123345667889");
    task141.setAccountId("112");
    task141.setSenderBank("Bank1");
    task141.setSenderBankBic("043876396");
    task141.setReason("I want this");
    task141.setClientTypeFk(true);
    task141.setOrganisationNameFk("Secret organization2");
    task141.setPersonalAccountId("333");
    task141.setCurrencyType("RUB");
    task141.setCause("cause1");
    task141.setStatus(OperationTaskStatus.ON_APPROVAL);
    task141.setIdentityDocument(new IdentityDocument());
    task141.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    task141.getIdentityDocument().setSeries("0825");
    task141.getIdentityDocument().setNumber("954745");
    task141.getIdentityDocument().setIssuedBy("OVD");
    task141.getIdentityDocument().setIssuedDate(date(2000, 1, 15, 0, 0));
    operationPackage1.getFromAccountWithdraws().add(task141);

    OperationTaskCheckbookIssuing task151 = new OperationTaskCheckbookIssuing();
    task151.setRepFio("Иванов Иван Никифорович");
    task151.setLegalEntityShortName("short name2");
    task151.setCreatedDate(date(2017, 4, 28, 14, 0));
    task151.setInn("123345667889");
    task151.setAccountId("112");
    task151.setBankName("Bank1");
    task151.setBankBic("043876396");
    task151.setSubbranchCompositeCode("8463/13587");
    task151.setIdentityDocument(new IdentityDocument());
    task151.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    task151.getIdentityDocument().setSeries("0825");
    task151.getIdentityDocument().setNumber("954745");
    task151.getIdentityDocument().setIssuedBy("OVD");
    task151.getIdentityDocument().setIssuedDate(date(2000, 1, 15, 0, 0));
    task151.setDueDate(date(2017, 9, 28, 0, 0));
    task151.setContainsCheckbook(true);
    task151.setStatus(OperationTaskStatus.ON_APPROVAL);
    Checkbook checkbook151 = new Checkbook();
    checkbook151.setId("241546467546");
    checkbook151.setFirstCheckId("15800");
    checkbook151.setCheckCount(100L);
    task151.getCheckbooks().add(checkbook151);
    task151.setRequestId("65588445522");
    task151.setRequestStatus(CheckbookRequestStatus.PENDING);
    operationPackage1.getCheckbookIssuing().add(task151);
    
    response1 = new SrvAddTaskClOperPkgRs();
    response1.setHeaderInfo(headerInfo(FIX_UUID));
    response1.setSrvAddTaskClOperPkgRsMessage(new SrvAddTaskClOperPkgRsMessage());
    response1.getSrvAddTaskClOperPkgRsMessage().setPkgId(2121);

    response1.getSrvAddTaskClOperPkgRsMessage()
        .setToCardDeposit(new SrvAddTaskClOperPkgRsMessage.ToCardDeposit());
    SrvAddTaskClOperPkgRsMessage.ToCardDeposit.TaskItem task115 =
        new SrvAddTaskClOperPkgRsMessage.ToCardDeposit.TaskItem();
    task115.setPkgTaskId(1597L);
    task115.setResponseCode(BigInteger.valueOf(1));
    response1.getSrvAddTaskClOperPkgRsMessage().getToCardDeposit().getTaskItem().add(task115);

    response1.getSrvAddTaskClOperPkgRsMessage()
        .setFromCardWithdraw(new SrvAddTaskClOperPkgRsMessage.FromCardWithdraw());
    SrvAddTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem task125 =
        new SrvAddTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem();
    task125.setPkgTaskId(1598L);
    task125.setResponseCode(BigInteger.valueOf(1));
    response1.getSrvAddTaskClOperPkgRsMessage().getFromCardWithdraw().getTaskItem().add(task125);

    response1.getSrvAddTaskClOperPkgRsMessage()
        .setToAccountDepositRub(new SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub());
    SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem task135 =
        new SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem();
    task135.setPkgTaskId(1599L);
    task135.setResponseCode(BigInteger.valueOf(1));
    response1.getSrvAddTaskClOperPkgRsMessage().getToAccountDepositRub().getTaskItem().add(task135);

    response1.getSrvAddTaskClOperPkgRsMessage()
        .setFromAccountWithdrawRub(new SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub());
    SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem task145 =
        new SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem();
    task145.setPkgTaskId(1600L);
    task145.setResponseCode(BigInteger.valueOf(1));
    response1.getSrvAddTaskClOperPkgRsMessage().getFromAccountWithdrawRub().getTaskItem()
        .add(task145);

    response1.getSrvAddTaskClOperPkgRsMessage()
        .setCheckbookIssuing(new SrvAddTaskClOperPkgRsMessage.CheckbookIssuing());
    SrvAddTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem task155 =
        new SrvAddTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem();
    task155.setPkgTaskId(1601L);
    task155.setResponseCode(BigInteger.valueOf(1));
    response1.getSrvAddTaskClOperPkgRsMessage().getCheckbookIssuing().getTaskItem()
        .add(task155);

    opRequest2 = new OperationPackageRequest();
    opRequest2.setWorkPlaceUid("123456");
    opRequest2.setInn("123345667889");

    response2 = new SrvCheckClOperPkgRs();
    response2.setHeaderInfo(headerInfo(FIX_UUID));
    response2.setSrvCheckClOperPkgRsMessage(new SrvCheckClOperPkgRsMessage());
    response2.getSrvCheckClOperPkgRsMessage().setPkgExistsFlg(true);
    response2.getSrvCheckClOperPkgRsMessage().setPkgId(2121L);
    response2.getSrvCheckClOperPkgRsMessage().setPkgStatus(PkgStatusType.PACKED);

    opRequest3 = new OperationPackageRequest();
    opRequest3.setWorkPlaceUid("123456");
    opRequest3.setInn("123345667889");
    opRequest3.setUserLogin("Sidorov_SS");

    response3 = new SrvCreateClOperPkgRs();
    response3.setHeaderInfo(headerInfo(FIX_UUID));
    response3.setSrvCreateClOperPkgRsMessage(new SrvCreateClOperPkgRsMessage());
    response3.getSrvCreateClOperPkgRsMessage().setResponseCode(BigInteger.valueOf(1));
    response3.getSrvCreateClOperPkgRsMessage().setPkgId(2121L);
    response3.getSrvCreateClOperPkgRsMessage().setPkgStatus(PkgStatusType.NEW);

    otRequest4 = new OperationTasksRequest();
    otRequest4.setPackageId(2121L);
    otRequest4.setTasks(new ArrayList<OperationTask>());
    OperationTask task401 = new OperationTask();
    task401.setId(1597L);
    task401.setStatus(OperationTaskStatus.ACTIVE);
    otRequest4.getTasks().add(task401);
    OperationTask task402 = new OperationTask();
    task402.setId(1598L);
    task402.setStatus(OperationTaskStatus.ACTIVE);
    otRequest4.getTasks().add(task402);
    otRequest4.setTaskStatusGlobal(OperationTaskStatus.APPROVED);
    otRequest4.setFromDate(date(2017, 5, 20, 0, 0));
    otRequest4.setToDate(date(2017, 5, 21, 0, 0));

    response4 = new SrvGetTaskClOperPkgRs();
    response4.setHeaderInfo(headerInfo(FIX_UUID));
    response4.setSrvGetTaskClOperPkgRsMessage(new SrvGetTaskClOperPkgRsMessage());

    SrvGetTaskClOperPkgRsMessage.PkgItem package421 = new SrvGetTaskClOperPkgRsMessage.PkgItem();
    package421.setPkgId(2121);
    package421.setUserLogin("Sidorov_SS");

    package421.setToCardDeposit(new SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit());
    SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem task411 =
        new SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem();
    task411.setPkgTaskId(1597L);
    task411.setOVNUId("112233");
    task411.setOVNNum(BigInteger.valueOf(5));
    task411.setOVNStatus(OVNStatusType.NEW);
    task411.setRepID("98971461998295679");
    task411.setLegalEntityShortName("short name");
    task411.setAmount(BigDecimal.valueOf(200));
    task411.setCreatedDttm(xmlCalendar(2017, 4, 28, 13, 45));
    task411.setINN("123345667899");
    task411.setKPP("44762383");
    task411.setAccountId("111111");
    task411.setSenderAccountId("157485");
    task411.setSenderBank("Bank1");
    task411.setSenderBankBIC("043876396");
    task411.setRecipientBank("Bank2");
    task411.setRecipientBankBIC("043876166");
    task411.setSource("top secret");
    task411.setIsClientTypeFK(true);
    task411.setFDestLEName("Secret organization");
    task411.setPersonalAccountId("333");
    task411.setCurrencyType("RUB");
    task411.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
    task411.setChangedDttm(xmlCalendar(2017, 4, 29, 12, 30));
    task411.setStatusChangedDttm(xmlCalendar(2017, 4, 29, 13, 30));
    task411.setCardInfo(new SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.CardInfo());
    task411.getCardInfo().setCardNumber("4894123569871254");
    task411.getCardInfo().setCardExpDate(xmlCalendar(2017, 9, 17, 0, 0));
    task411.getCardInfo().setCardIssuingNetworkCode(BigInteger.valueOf(1));
    task411.getCardInfo().setCardTypeId(BigInteger.valueOf(1));
    task411.getCardInfo().setCardOwnerFirstName("PETR");
    task411.getCardInfo().setCardOwnerLastName("PETROV");
    task411.setCashSymbols(new SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem
        .CashSymbols());
    SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.CashSymbols.CashSymbolItem
        cashSymbol411 = new SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.CashSymbols
        .CashSymbolItem();
    cashSymbol411.setCashSymbol("02");
    cashSymbol411.setCashSymbolAmount(BigDecimal.valueOf(200));
    task411.getCashSymbols().getCashSymbolItem().add(cashSymbol411);
    package421.getToCardDeposit().getTaskItem().add(task411);

    package421.setFromCardWithdraw(new SrvGetTaskClOperPkgRsMessage.PkgItem.FromCardWithdraw());
    SrvGetTaskClOperPkgRsMessage.PkgItem.FromCardWithdraw.TaskItem task421 =
        new SrvGetTaskClOperPkgRsMessage.PkgItem.FromCardWithdraw.TaskItem();
    task421.setPkgTaskId(1598L);
    task421.setRepFIO("Иванов Иван Никифорович");
    task421.setLegalEntityShortName("short name2");
    task421.setAmount(BigDecimal.valueOf(300));
    task421.setCreatedDttm(xmlCalendar(2017, 4, 28, 14, 0));
    task421.setINN("123345667889");
    task421.setAccountId("112");
    task421.setCardNumber("4894123566371254");
    task421.setSenderBank("Bank1");
    task421.setSenderBankBIC("043876396");
    task421.setReason("I want this");
    task421.setIsClientTypeFK(true);
    task421.setFDestLEName("Secret organization2");
    task421.setPersonalAccountId("333");
    task421.setCurrencyType("RUB");
    task421.setCause("cause1");
    task421.setPkgTaskStatus(PkgTaskStatusType.ON_APPROVAL);
    task421.setChangedDttm(xmlCalendar(2017, 4, 29, 13, 30));
    task421.setStatusChangedDttm(xmlCalendar(2017, 4, 29, 14, 30));
    task421.setIdentityDocumentType(IDDtype.INTERNPASSPORT);
    task421.setSeries("0825");
    task421.setNumber("954745");
    task421.setIssuedBy("OVD");
    task421.setIssuedDate(xmlCalendar(2000, 1, 15, 0, 0));
    package421.getFromCardWithdraw().getTaskItem().add(task421);

    package421.setToAccountDepositRub(new SrvGetTaskClOperPkgRsMessage.PkgItem
        .ToAccountDepositRub());
    SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem task431 =
        new SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem();
    task431.setPkgTaskId(1599L);
    task431.setOVNUId("112234");
    task431.setOVNNum(BigInteger.valueOf(5));
    task431.setOVNStatus(OVNStatusType.NEW);
    task431.setRepID("98971461998295679");
    task431.setLegalEntityShortName("short name");
    task431.setAmount(BigDecimal.valueOf(200));
    task431.setCreatedDttm(xmlCalendar(2017, 4, 28, 13, 45));
    task431.setINN("123345667899");
    task431.setKPP("44762383");
    task431.setAccountId("111111");
    task431.setSenderAccountId("157485");
    task431.setSenderBank("Bank1");
    task431.setSenderBankBIC("043876396");
    task431.setRecipientBank("Bank2");
    task431.setRecipientBankBIC("043876166");
    task431.setSource("top secret");
    task431.setIsClientTypeFK(true);
    task431.setFDestLEName("Secret organization");
    task431.setPersonalAccountId("333");
    task431.setCurrencyType("RUB");
    task431.setCause("cause1");
    task431.setIsAuthСaporMutFund(true);
    task431.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
    task431.setChangedDttm(xmlCalendar(2017, 4, 29, 12, 30));
    task431.setStatusChangedDttm(xmlCalendar(2017, 4, 29, 13, 30));
    task431.setStockholderInfo(new SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub
        .TaskItem.StockholderInfo());
    SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem.StockholderInfo
        .Stockholderitem stockholderInfo431 = new SrvGetTaskClOperPkgRsMessage.PkgItem
        .ToAccountDepositRub.TaskItem.StockholderInfo.Stockholderitem();
    stockholderInfo431.setStockholderName("Некто");
    stockholderInfo431.setShare(BigDecimal.valueOf(51));
    task431.getStockholderInfo().getStockholderitem().add(stockholderInfo431);
    task431.setCashSymbols(new SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem
        .CashSymbols());
    SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem.CashSymbols.CashSymbolItem
        cashSymbol431 = new SrvGetTaskClOperPkgRsMessage.PkgItem.ToAccountDepositRub.TaskItem
        .CashSymbols.CashSymbolItem();
    cashSymbol431.setCashSymbol("02");
    cashSymbol431.setCashSymbolAmount(BigDecimal.valueOf(200));
    task431.getCashSymbols().getCashSymbolItem().add(cashSymbol431);
    package421.getToAccountDepositRub().getTaskItem().add(task431);

    package421.setFromAccountWithdrawRub(new SrvGetTaskClOperPkgRsMessage.PkgItem
        .FromAccountWithdrawRub());
    SrvGetTaskClOperPkgRsMessage.PkgItem.FromAccountWithdrawRub.TaskItem task441 =
        new SrvGetTaskClOperPkgRsMessage.PkgItem.FromAccountWithdrawRub.TaskItem();
    task441.setPkgTaskId(1600L);
    task441.setRepFIO("Иванов Иван Никифорович");
    task441.setLegalEntityShortName("short name2");
    task441.setAmount(BigDecimal.valueOf(300));
    task441.setCreatedDttm(xmlCalendar(2017, 4, 28, 14, 0));
    task441.setINN("123345667889");
    task441.setAccountId("112");
    task441.setSenderBank("Bank1");
    task441.setSenderBankBIC("043876396");
    task441.setReason("I want this");
    task441.setIsClientTypeFK(true);
    task441.setFDestLEName("Secret organization2");
    task441.setPersonalAccountId("333");
    task441.setCurrencyType("RUB");
    task441.setCause("cause1");
    task441.setPkgTaskStatus(PkgTaskStatusType.ON_APPROVAL);
    task441.setChangedDttm(xmlCalendar(2017, 4, 29, 13, 30));
    task441.setStatusChangedDttm(xmlCalendar(2017, 4, 29, 14, 30));
    task441.setIdentityDocumentType(IDDtype.INTERNPASSPORT);
    task441.setSeries("0825");
    task441.setNumber("954745");
    task441.setIssuedBy("OVD");
    task441.setIssuedDate(xmlCalendar(2000, 1, 15, 0, 0));
    package421.getFromAccountWithdrawRub().getTaskItem().add(task441);

    package421.setCheckbookIssuing(new SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing());
    SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem task451 =
        new SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem();
    task451.setPkgTaskId(1601L);
    task451.setCbRequestId("65588445522");
    task451.setBankName("Bank1");
    task451.setBankBIC("043876396");
    task451.setSubbranchCompositeCode("8463/13587");
    task451.setRepFIO("Иванов Иван Никифорович");
    task451.setLegalEntityShortName("short name2");
    task451.setCreatedDttm(xmlCalendar(2017, 4, 28, 14, 0));
    task451.setINN("123345667889");
    task451.setAccountId("112");
    task451.setPkgTaskStatus(PkgTaskStatusType.ON_APPROVAL);
    task451.setChangedDttm(xmlCalendar(2017, 4, 29, 13, 30));
    task451.setStatusChangedDttm(xmlCalendar(2017, 4, 29, 14, 30));
    task451.setCbRequestStatus(CbRequestStatusType.PENDING);
    task451.setCbRqstatusChangedDttm(xmlCalendar(2017, 4, 29, 13, 20));
    task451.setIdentityDocumentType(IDDtype.INTERNPASSPORT);
    task451.setSeries("0825");
    task451.setNumber("954745");
    task451.setIssuedBy("OVD");
    task451.setIssuedDate(xmlCalendar(2000, 1, 15, 0, 0));
    task451.setDueDt(xmlCalendar(2017, 9, 28, 0, 0));
    task451.setContainsCheckbook(true);
    task451.setCheckbookInfo(new SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem
        .CheckbookInfo());
    SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem.CheckbookInfo.CheckbookItem
        checkbookInfo451 = new SrvGetTaskClOperPkgRsMessage.PkgItem.CheckbookIssuing.TaskItem
        .CheckbookInfo.CheckbookItem();
    checkbookInfo451.setCheckbookId("241546467546");
    checkbookInfo451.setFirstCheckId("15800");
    checkbookInfo451.setCbPageAmount(BigInteger.valueOf(100));
    task451.getCheckbookInfo().getCheckbookItem().add(checkbookInfo451);
    package421.getCheckbookIssuing().getTaskItem().add(task451);

    response4.getSrvGetTaskClOperPkgRsMessage().getPkgItem().add(package421);

    operationPackage5 = new OperationPackage();
    operationPackage5.setId(21L);
    OperationTaskCardDeposit task511 = new OperationTaskCardDeposit();
    task511.setId(1597L);
    task511.setOvnUid("112233");
    task511.setOvnNum(5L);
    task511.setOvnStatus(OvnStatus.NEW);
    task511.setRepresentativeId("98971461998295679");
    task511.setLegalEntityShortName("short name");
    task511.setAmount(BigDecimal.valueOf(200));
    task511.setCreatedDate(date(2017, 4, 28, 13, 45));
    task511.setInn("123345667899");
    task511.setKpp("44762383");
    task511.setAccountId("111111");
    task511.setSenderAccountId("157485");
    task511.setSenderBank("Bank1");
    task511.setSenderBankBic("043876396");
    task511.setRecipientAccountId("163556");
    task511.setRecipientBank("Bank2");
    task511.setRecipientBankBic("043876166");
    task511.setSource("top secret");
    task511.setClientTypeFk(true);
    task511.setOrganisationNameFk("Secret organization");
    task511.setPersonalAccountId("333");
    task511.setCurrencyType("RUB");
    task511.setStatus(OperationTaskStatus.ACTIVE);
    task511.getCard().setNumber("4894123569871254");
    task511.getCard().setExpiryDate(date(2017, 9, 17, 0, 0));
    task511.getCard().setIssuingNetworkCode(CardNetworkCode.MASTER_CARD);
    task511.getCard().setType(CardType.CREDIT);
    task511.getCard().setOwnerFirstName("PETR");
    task511.getCard().setOwnerLastName("PETROV");
    CashSymbol cashSymbol511 = new CashSymbol();
    cashSymbol511.setCode("02");
    cashSymbol511.setDescription("Описание 02");
    cashSymbol511.setAmount(BigDecimal.valueOf(200));
    task511.getCashSymbols().add(cashSymbol511);
    operationPackage5.getToCardDeposits().add(task511);

    OperationTaskCardWithdraw task521 = new OperationTaskCardWithdraw();
    task521.setId(1598L);
    task521.setRepFio("Иванов Иван Никифорович");
    task521.setLegalEntityShortName("short name2");
    task521.setAmount(BigDecimal.valueOf(300));
    task521.setCreatedDate(date(2017, 4, 28, 14, 0));
    task521.setInn("123345667889");
    task521.setAccountId("112");
    task521.setSenderBank("Bank1");
    task521.setSenderBankBic("043876396");
    task521.setReason("I want this");
    task521.setClientTypeFk(true);
    task521.setOrganisationNameFk("Secret organization2");
    task521.setPersonalAccountId("333");
    task521.setCurrencyType("RUB");
    task521.setCause("cause1");
    task521.setStatus(OperationTaskStatus.ON_APPROVAL);
    task521.setIdentityDocument(new IdentityDocument());
    task521.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    task521.getIdentityDocument().setSeries("0825");
    task521.getIdentityDocument().setNumber("954745");
    task521.getIdentityDocument().setIssuedBy("OVD");
    task521.getIdentityDocument().setIssuedDate(date(2000, 1, 15, 0, 0));
    task521.setCardNumber("4894123566371254");
    operationPackage5.getFromCardWithdraws().add(task521);

    OperationTaskAccountDeposit task531 = new OperationTaskAccountDeposit();
    task531.setId(1599L);
    task531.setOvnUid("112234");
    task531.setOvnNum(5L);
    task531.setOvnStatus(OvnStatus.NEW);
    task531.setRepresentativeId("98971461998295679");
    task531.setLegalEntityShortName("short name");
    task531.setAmount(BigDecimal.valueOf(200));
    task531.setCreatedDate(date(2017, 4, 28, 13, 45));
    task531.setInn("123345667899");
    task531.setKpp("44762383");
    task531.setAccountId("111111");
    task531.setSenderAccountId("157485");
    task531.setSenderBank("Bank1");
    task531.setSenderBankBic("043876396");
    task531.setRecipientAccountId("163556");
    task531.setRecipientBank("Bank2");
    task531.setRecipientBankBic("043876166");
    task531.setSource("top secret");
    task531.setClientTypeFk(true);
    task531.setOrganisationNameFk("Secret organization");
    task531.setPersonalAccountId("333");
    task531.setCurrencyType("RUB");
    task531.setCause("cause1");
    task531.setAuthCaporMutFund(true);
    task531.setStatus(OperationTaskStatus.ACTIVE);
    CashSymbol cashSymbol531 = new CashSymbol();
    cashSymbol531.setCode("02");
    cashSymbol531.setDescription("Символ 02");
    cashSymbol531.setAmount(BigDecimal.valueOf(200));
    task531.getCashSymbols().add(cashSymbol531);
    Stockholder stockholder531 = new Stockholder();
    stockholder531.setName("Некто");
    stockholder531.setShare(BigDecimal.valueOf(51));
    task531.getStockholders().add(stockholder531);
    operationPackage5.getToAccountDeposits().add(task531);

    OperationTaskAccountWithdraw task541 = new OperationTaskAccountWithdraw();
    task541.setId(1600L);
    task541.setRepFio("Иванов Иван Никифорович");
    task541.setLegalEntityShortName("short name2");
    task541.setAmount(BigDecimal.valueOf(300));
    task541.setCreatedDate(date(2017, 4, 28, 14, 0));
    task541.setInn("123345667889");
    task541.setAccountId("112");
    task541.setSenderBank("Bank1");
    task541.setSenderBankBic("043876396");
    task541.setReason("I want this");
    task541.setClientTypeFk(true);
    task541.setOrganisationNameFk("Secret organization2");
    task541.setPersonalAccountId("333");
    task541.setCurrencyType("RUB");
    task541.setCause("cause1");
    task541.setStatus(OperationTaskStatus.ON_APPROVAL);
    task541.setIdentityDocument(new IdentityDocument());
    task541.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    task541.getIdentityDocument().setSeries("0825");
    task541.getIdentityDocument().setNumber("954745");
    task541.getIdentityDocument().setIssuedBy("OVD");
    task541.getIdentityDocument().setIssuedDate(date(2000, 1, 15, 0, 0));
    operationPackage5.getFromAccountWithdraws().add(task541);

    OperationTaskCheckbookIssuing task551 = new OperationTaskCheckbookIssuing();
    task551.setId(1601L);
    task551.setRepFio("Иванов Иван Никифорович");
    task551.setLegalEntityShortName("short name2");
    task551.setCreatedDate(date(2017, 4, 28, 14, 0));
    task551.setInn("123345667889");
    task551.setAccountId("112");
    task551.setBankName("Bank1");
    task551.setBankBic("043876396");
    task551.setSubbranchCompositeCode("8463/13587");
    task551.setIdentityDocument(new IdentityDocument());
    task551.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    task551.getIdentityDocument().setSeries("0825");
    task551.getIdentityDocument().setNumber("954745");
    task551.getIdentityDocument().setIssuedBy("OVD");
    task551.getIdentityDocument().setIssuedDate(date(2000, 1, 15, 0, 0));
    task551.setDueDate(date(2017, 9, 28, 0, 0));
    task551.setContainsCheckbook(true);
    task551.setStatus(OperationTaskStatus.ON_APPROVAL);
    Checkbook checkbook551 = new Checkbook();
    checkbook551.setId("241546467546");
    checkbook551.setFirstCheckId("15800");
    checkbook551.setCheckCount(100L);
    task551.getCheckbooks().add(checkbook551);
    task551.setRequestId("65588445522");
    task551.setRequestStatus(CheckbookRequestStatus.PENDING);
    operationPackage5.getCheckbookIssuing().add(task551);
    
    response5 = new SrvUpdTaskClOperPkgRs();
    response5.setHeaderInfo(headerInfo(FIX_UUID));
    response5.setSrvUpdTaskClOperPkgRsMessage(new SrvUpdTaskClOperPkgRsMessage());
    response5.getSrvUpdTaskClOperPkgRsMessage().setPkgId(2121);

    response5.getSrvUpdTaskClOperPkgRsMessage()
        .setToCardDeposit(new SrvUpdTaskClOperPkgRsMessage.ToCardDeposit());
    SrvUpdTaskClOperPkgRsMessage.ToCardDeposit.TaskItem task515 =
        new SrvUpdTaskClOperPkgRsMessage.ToCardDeposit.TaskItem();
    task515.setPkgTaskId(1597L);
    task515.setResponseCode(BigInteger.valueOf(1));
    response5.getSrvUpdTaskClOperPkgRsMessage().getToCardDeposit().getTaskItem().add(task515);

    response5.getSrvUpdTaskClOperPkgRsMessage()
        .setFromCardWithdraw(new SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw());
    SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem task525 =
        new SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem();
    task525.setPkgTaskId(1598L);
    task525.setResponseCode(BigInteger.valueOf(1));
    response5.getSrvUpdTaskClOperPkgRsMessage().getFromCardWithdraw().getTaskItem().add(task525);

    response5.getSrvUpdTaskClOperPkgRsMessage()
        .setToAccountDepositRub(new SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub());
    SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem task535 =
        new SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem();
    task535.setPkgTaskId(1599L);
    task535.setResponseCode(BigInteger.valueOf(1));
    response5.getSrvUpdTaskClOperPkgRsMessage().getToAccountDepositRub().getTaskItem().add(task535);

    response5.getSrvUpdTaskClOperPkgRsMessage()
        .setFromAccountWithdrawRub(new SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub());
    SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem task545 =
        new SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem();
    task545.setPkgTaskId(1600L);
    task545.setResponseCode(BigInteger.valueOf(1));
    response5.getSrvUpdTaskClOperPkgRsMessage().getFromAccountWithdrawRub().getTaskItem()
        .add(task545);

    response5.getSrvUpdTaskClOperPkgRsMessage()
        .setCheckbookIssuing(new SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing());
    SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem task555 =
        new SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem();
    task555.setPkgTaskId(1601L);
    task555.setResponseCode(BigInteger.valueOf(1));
    response5.getSrvUpdTaskClOperPkgRsMessage().getCheckbookIssuing().getTaskItem().add(task555);
  }

  @Test
  public void testRequestAddTasks() {
    SrvAddTaskClOperPkgRq request = OperationPackageAdapter.requestAddTasks(operationPackage1);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvAddTaskClOperPkgRqMessage());
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getPkgId(), 21);

    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getOVNUId(), "112233");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getOVNNum(), BigInteger.valueOf(5));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getOVNStatus(), OVNStatusType.NEW);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRepID(), "98971461998295679");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getLegalEntityShortName(), "short name");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 13, 45));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getINN(), "123345667899");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getKPP(), "44762383");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getAccountId(), "111111");
    Assert.assertNotNull(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo());
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardNumber(), "4894123569871254");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardExpDate(), xmlCalendar(2017, 9, 17, 0, 0));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardIssuingNetworkCode(), BigInteger.valueOf(1));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardTypeId(), BigInteger.valueOf(1));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardOwnerFirstName(), "PETR");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardOwnerLastName(), "PETROV");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSenderAccountId(), "157485");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRecipientBank(), "Bank2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRecipientBankBIC(), "043876166");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSource(), "top secret");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getFDestLEName(), "Secret organization");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getPkgTaskStatus(), PkgTaskStatusType.ACTIVE);
    Assert.assertNotNull(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols());
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols().getCashSymbolItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbol(), "02");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbolAmount(),
        BigDecimal.valueOf(200));

    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getRepFIO(), "Иванов Иван Никифорович");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getAmount(), BigDecimal.valueOf(300));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 14, 0));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getINN(), "123345667889");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getAccountId(), "112");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCardNumber(), "4894123566371254");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getReason(), "I want this");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getFDestLEName(), "Secret organization2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCause(), "cause1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ON_APPROVAL);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getIdentityDocumentType(), IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getSeries(), "0825");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getNumber(), "954745");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getIssuedBy(), "OVD");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getIssuedDate(), xmlCalendar(2000, 1, 15, 0, 0));

    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getOVNUId(), "112234");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getOVNNum(), BigInteger.valueOf(5));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getOVNStatus(), OVNStatusType.NEW);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getRepID(), "98971461998295679");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 13, 45));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getINN(), "123345667899");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getKPP(), "44762383");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getAccountId(), "111111");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSenderAccountId(), "157485");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getRecipientBank(), "Bank2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getRecipientBankBIC(), "043876166");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSource(), "top secret");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getFDestLEName(), "Secret organization");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ACTIVE);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCause(), "cause1");
    Assert.assertTrue(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).isIsAuthСaporMutFund());
    Assert.assertNotNull(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols());
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols().getCashSymbolItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbol(), "02");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbolAmount(),
        BigDecimal.valueOf(200));
    Assert.assertNotNull(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo());
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo().getStockholderitem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo().getStockholderitem().get(0).getStockholderName(),
        "Некто");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo().getStockholderitem().get(0).getShare(),
        BigDecimal.valueOf(51));

    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getRepFIO(), "Иванов Иван Никифорович");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getAmount(), BigDecimal.valueOf(300));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 14, 0));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getINN(), "123345667889");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getAccountId(), "112");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getReason(), "I want this");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getFDestLEName(), "Secret organization2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getCause(), "cause1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ON_APPROVAL);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getIdentityDocumentType(), IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getSeries(), "0825");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getNumber(), "954745");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getIssuedBy(), "OVD");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getIssuedDate(), xmlCalendar(2000, 1, 15, 0, 0));

    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getBankName(), "Bank1");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getSubbranchCompositeCode(), "8463/13587");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 14, 0));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getAccountId(), "112");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getINN(), "123345667889");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getRepFIO(), "Иванов Иван Никифорович");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ON_APPROVAL);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getIdentityDocumentType(), IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getSeries(), "0825");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getNumber(), "954745");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getIssuedBy(), "OVD");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getIssuedDate(), xmlCalendar(2000, 1, 15, 0, 0));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getDueDt(), xmlCalendar(2017, 9, 28, 0, 0));
    Assert.assertTrue(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).isContainsCheckbook());
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().size(), 1);
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().get(0).getCheckbookId(),
        "241546467546");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().get(0).getFirstCheckId(),
        "15800");
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().get(0).getCbPageAmount(),
        BigInteger.valueOf(100));
    Assert.assertEquals(request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCbRequestStatus(), CbRequestStatusType.PENDING);
  }

  @Test
  public void testConvertSrvAddTaskClOperPkgRs() {
    OperationPackage operationPackage = OperationPackageAdapter.convert(response1);
    assertHeaderInfo(operationPackage, FIX_UUID);
    Assert.assertEquals(operationPackage.getId(), Long.valueOf(2121));
    Assert.assertNull(operationPackage.getStatus());

    Assert.assertEquals(operationPackage.getToCardDeposits().size(), 1);
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getId(), Long.valueOf(1597));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getResponseCode(),
        Long.valueOf(1));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getToCardDeposits().get(0).getReceiveDate());
    Assert.assertNull(operationPackage.getToCardDeposits().get(0).getStatus());

    Assert.assertEquals(operationPackage.getFromCardWithdraws().size(), 1);
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getId(), Long.valueOf(1598));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getResponseCode(),
        Long.valueOf(1));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getFromCardWithdraws().get(0).getReceiveDate());
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getClass(),
        OperationTask.class);
  }

  @Test
  public void testRequestCheckPackage() {
    SrvCheckClOperPkgRq request = OperationPackageAdapter.requestCheckPackage(opRequest2);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCheckClOperPkgRqMessage());
    Assert.assertEquals(request.getSrvCheckClOperPkgRqMessage().getWorkPlaceUId(), "123456");
    Assert.assertEquals(request.getSrvCheckClOperPkgRqMessage().getINN(), "123345667889");
  }

  @Test
  public void testConvertSrvCheckClOperPkgRs() {
    OperationPackage operationPackage = OperationPackageAdapter.convert(response2);
    assertHeaderInfo(operationPackage, FIX_UUID);
    Assert.assertEquals(operationPackage.getId(), Long.valueOf(2121));
    Assert.assertEquals(operationPackage.getStatus(), OperationPackageStatus.PACKED);
    Assert.assertTrue(operationPackage.getToCardDeposits().isEmpty());
  }

  @Test
  public void testRequestCreatePackage() {
    SrvCreateClOperPkgRq request = OperationPackageAdapter.requestCreatePackage(opRequest3);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCreateClOperPkgRqMessage());
    Assert.assertEquals(request.getSrvCreateClOperPkgRqMessage().getWorkPlaceUId(), "123456");
    Assert.assertEquals(request.getSrvCreateClOperPkgRqMessage().getINN(), "123345667889");
    Assert.assertEquals(request.getSrvCreateClOperPkgRqMessage().getUserLogin(), "Sidorov_SS");
  }

  @Test
  public void testConvertSrvCreateClOperPkgRs() {
    OperationPackage operationPackage = OperationPackageAdapter.convert(response3);
    assertHeaderInfo(operationPackage, FIX_UUID);
    Assert.assertEquals(operationPackage.getId(), Long.valueOf(2121));
    Assert.assertEquals(operationPackage.getStatus(), OperationPackageStatus.NEW);
    Assert.assertEquals(operationPackage.getResponseCode(), Long.valueOf(1));
    Assert.assertTrue(operationPackage.getToCardDeposits().isEmpty());
  }

  @Test
  public void testRequestTasks() {
    SrvGetTaskClOperPkgRq request = OperationPackageAdapter.requestTasks(otRequest4);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetTaskClOperPkgRqMessage());
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getPkgId(), Long.valueOf(2121));
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getTaskStatusGlobal(),
        PkgTaskStatusType.APPROVED);
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getDateFrom(),
        xmlCalendar(2017, 5, 20, 0, 0));
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getDateTo(),
        xmlCalendar(2017, 5, 21, 0, 0));
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getTaskType().size(), 2);
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getTaskType()
        .get(0).getPkgTaskId(), Long.valueOf(1597));
    Assert.assertEquals(request.getSrvGetTaskClOperPkgRqMessage().getTaskType()
        .get(0).getPkgTaskStatus(), PkgTaskStatusType.ACTIVE);
  }

  @Test
  public void testConvertSrvGetTaskClOperPkgRs() {
    ExternalEntityList<OperationPackage> packageList = OperationPackageAdapter.convert(response4);
    assertHeaderInfo(packageList, FIX_UUID);
    Assert.assertEquals(packageList.getItems().size(), 1);

    OperationPackage operationPackage = packageList.getItems().get(0);
    assertHeaderInfo(operationPackage, FIX_UUID);
    Assert.assertEquals(operationPackage.getId(), Long.valueOf(2121));
    Assert.assertEquals(operationPackage.getUserLogin(), "Sidorov_SS");

    Assert.assertEquals(operationPackage.getToCardDeposits().size(), 1);
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getClass(),
        OperationTaskCardDeposit.class);
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getId(), Long.valueOf(1597));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getStatus(),
        OperationTaskStatus.ACTIVE);
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getCreatedDate(),
        date(2017, 4, 28, 13, 45));
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getOvnUid(), "112233");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getOvnNum(), Long.valueOf(5));
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getOvnStatus(), OvnStatus.NEW);
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getRepresentativeId(), "98971461998295679");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getLegalEntityShortName(), "short name");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getInn(), "123345667899");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getKpp(), "44762383");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getAccountId(), "111111");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getSenderAccountId(), "157485");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getSenderBank(), "Bank1");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getSenderBankBic(), "043876396");
    Assert.assertNull(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getRecipientAccountId());
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getRecipientBank(), "Bank2");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getRecipientBankBic(), "043876166");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getSource(), "top secret");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .isClientTypeFk(), true);
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getOrganisationNameFk(), "Secret organization");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getPersonalAccountId(), "333");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCurrencyType(), "RUB");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCashSymbols().size(), 1);
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCashSymbols().get(0).getCode(), "02");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCashSymbols().get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCard().getNumber(), "4894123569871254");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCard().getExpiryDate(), date(2017, 9, 17, 0, 0));
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCard().getIssuingNetworkCode(), CardNetworkCode.MASTER_CARD);
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCard().getType(), CardType.CREDIT);
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCard().getOwnerFirstName(), "PETR");
    Assert.assertEquals(((OperationTaskCardDeposit) operationPackage.getToCardDeposits().get(0))
        .getCard().getOwnerLastName(), "PETROV");
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getChangedDate(),
        date(2017, 4, 29, 12, 30));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getStatusChangedDate(),
        date(2017, 4, 29, 13, 30));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getToCardDeposits().get(0).getReceiveDate());

    Assert.assertEquals(operationPackage.getFromCardWithdraws().size(), 1);
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getClass(),
        OperationTaskCardWithdraw.class);
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getId(), Long.valueOf(1598));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getStatus(),
        OperationTaskStatus.ON_APPROVAL);
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getCreatedDate(),
        date(2017, 4, 28, 14, 0));
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getRepFio(), "Иванов Иван Никифорович");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getLegalEntityShortName(), "short name2");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getAmount(), BigDecimal.valueOf(300));
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getInn(), "123345667889");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getAccountId(), "112");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getSenderBank(), "Bank1");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getSenderBankBic(), "043876396");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getReason(), "I want this");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .isClientTypeFk(), true);
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getOrganisationNameFk(), "Secret organization2");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getPersonalAccountId(), "333");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getCurrencyType(), "RUB");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getCause(), "cause1");
    Assert.assertNotNull(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws()
        .get(0)).getIdentityDocument());
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getIdentityDocument().getType(), IdentityDocumentType.INTERNAL_PASSPORT);
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getIdentityDocument().getSeries(), "0825");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getIdentityDocument().getNumber(), "954745");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getIdentityDocument().getIssuedBy(), "OVD");
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getIdentityDocument().getIssuedDate(), date(2000, 1, 15, 0, 0));
    Assert.assertEquals(((OperationTaskCardWithdraw) operationPackage.getFromCardWithdraws().get(0))
        .getCardNumber(), "4894123566371254");
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getChangedDate(),
        date(2017, 4, 29, 13, 30));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getStatusChangedDate(),
        date(2017, 4, 29, 14, 30));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getFromCardWithdraws().get(0).getReceiveDate());

    Assert.assertEquals(operationPackage.getToAccountDeposits().size(), 1);
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getClass(),
        OperationTaskAccountDeposit.class);
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getId(), Long.valueOf(1599));
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getStatus(),
        OperationTaskStatus.ACTIVE);
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getCreatedDate(),
        date(2017, 4, 28, 13, 45));
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getOvnUid(), "112234");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getOvnNum(), Long.valueOf(5));
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getOvnStatus(), OvnStatus.NEW);
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getRepresentativeId(), "98971461998295679");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getLegalEntityShortName(), "short name");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getInn(), "123345667899");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getKpp(), "44762383");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getAccountId(), "111111");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getSenderAccountId(), "157485");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getSenderBank(), "Bank1");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getSenderBankBic(), "043876396");
    Assert.assertNull(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getRecipientAccountId());
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getRecipientBank(), "Bank2");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getRecipientBankBic(), "043876166");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getSource(), "top secret");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).isClientTypeFk(), true);
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getOrganisationNameFk(), "Secret organization");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getPersonalAccountId(), "333");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getCurrencyType(), "RUB");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getCashSymbols().size(), 1);
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getCashSymbols().get(0).getCode(), "02");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getCashSymbols().get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getCause(), "cause1");
    Assert.assertTrue(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).isAuthCaporMutFund());
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getStockholders().size(), 1);
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getStockholders().get(0).getName(), "Некто");
    Assert.assertEquals(((OperationTaskAccountDeposit) operationPackage.getToAccountDeposits()
        .get(0)).getStockholders().get(0).getShare(), BigDecimal.valueOf(51));
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getChangedDate(),
        date(2017, 4, 29, 12, 30));
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getStatusChangedDate(),
        date(2017, 4, 29, 13, 30));
    Assert.assertEquals(operationPackage.getToAccountDeposits().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getToAccountDeposits().get(0).getReceiveDate());

    Assert.assertEquals(operationPackage.getFromAccountWithdraws().size(), 1);
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getClass(),
        OperationTaskAccountWithdraw.class);
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getId(),
        Long.valueOf(1600));
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getStatus(),
        OperationTaskStatus.ON_APPROVAL);
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getCreatedDate(),
        date(2017, 4, 28, 14, 0));
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getRepFio(), "Иванов Иван Никифорович");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getAmount(), BigDecimal.valueOf(300));
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getInn(), "123345667889");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getAccountId(), "112");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getSenderBank(), "Bank1");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getSenderBankBic(), "043876396");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getReason(), "I want this");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).isClientTypeFk(), true);
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getOrganisationNameFk(), "Secret organization2");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getPersonalAccountId(), "333");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getCurrencyType(), "RUB");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getCause(), "cause1");
    Assert.assertNotNull(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getIdentityDocument());
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getIdentityDocument().getType(), IdentityDocumentType.INTERNAL_PASSPORT);
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getIdentityDocument().getSeries(), "0825");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getIdentityDocument().getNumber(), "954745");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getIdentityDocument().getIssuedBy(), "OVD");
    Assert.assertEquals(((OperationTaskAccountWithdraw) operationPackage.getFromAccountWithdraws()
        .get(0)).getIdentityDocument().getIssuedDate(), date(2000, 1, 15, 0, 0));
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getChangedDate(),
        date(2017, 4, 29, 13, 30));
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getStatusChangedDate(),
        date(2017, 4, 29, 14, 30));
    Assert.assertEquals(operationPackage.getFromAccountWithdraws().get(0).getRequestUid(),
        FIX_UUID);
    Assert.assertNotNull(operationPackage.getFromAccountWithdraws().get(0).getReceiveDate());

    Assert.assertEquals(operationPackage.getCheckbookIssuing().size(), 1);
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getClass(),
        OperationTaskCheckbookIssuing.class);
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getId(),
        Long.valueOf(1601));
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getStatus(),
        OperationTaskStatus.ON_APPROVAL);
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getCreatedDate(),
        date(2017, 4, 28, 14, 0));
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getRepFio(), "Иванов Иван Никифорович");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getInn(), "123345667889");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getAccountId(), "112");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getBankName(), "Bank1");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getBankBic(), "043876396");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getSubbranchCompositeCode(), "8463/13587");
    Assert.assertNotNull(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getIdentityDocument());
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getIdentityDocument().getType(), IdentityDocumentType.INTERNAL_PASSPORT);
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getIdentityDocument().getSeries(), "0825");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getIdentityDocument().getNumber(), "954745");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getIdentityDocument().getIssuedBy(), "OVD");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getIdentityDocument().getIssuedDate(), date(2000, 1, 15, 0, 0));
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getDueDate(), date(2017, 9, 28, 0, 0));
    Assert.assertTrue(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).isContainsCheckbook());
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getCheckbooks().size(), 1);
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getCheckbooks().get(0).getId(), "241546467546");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getCheckbooks().get(0).getFirstCheckId(), "15800");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getCheckbooks().get(0).getCheckCount(), Long.valueOf(100));
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getRequestId(), "65588445522");
    Assert.assertEquals(((OperationTaskCheckbookIssuing) operationPackage.getCheckbookIssuing()
        .get(0)).getRequestStatus(), CheckbookRequestStatus.PENDING);
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getChangedDate(),
        date(2017, 4, 29, 13, 30));
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getStatusChangedDate(),
        date(2017, 4, 29, 14, 30));
    Assert.assertEquals(operationPackage.getCheckbookIssuing().get(0).getRequestUid(),
        FIX_UUID);
    Assert.assertNotNull(operationPackage.getCheckbookIssuing().get(0).getReceiveDate());
  }

  @Test
  public void testRequestUpdateTasks() {
    SrvUpdTaskClOperPkgRq request = OperationPackageAdapter.requestUpdateTasks(operationPackage5);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvUpdTaskClOperPkgRqMessage());
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getPkgId(), 21);

    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getPkgTaskId(), 1597);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getOVNUId(), "112233");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getOVNNum(), BigInteger.valueOf(5));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getOVNStatus(), OVNStatusType.NEW);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRepID(), "98971461998295679");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getLegalEntityShortName(), "short name");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getINN(), "123345667899");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getKPP(), "44762383");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getAccountId(), "111111");
    Assert.assertNotNull(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo());
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardNumber(), "4894123569871254");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardExpDate(), xmlCalendar(2017, 9, 17, 0, 0));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardIssuingNetworkCode(), BigInteger.valueOf(1));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardTypeId(), BigInteger.valueOf(1));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardOwnerFirstName(), "PETR");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCardInfo().getCardOwnerLastName(), "PETROV");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSenderAccountId(), "157485");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRecipientAccountId(), "163556");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRecipientBank(), "Bank2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getRecipientBankBIC(), "043876166");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getSource(), "top secret");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getFDestLEName(), "Secret organization");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getPkgTaskStatus(), PkgTaskStatusType.ACTIVE);
    Assert.assertNotNull(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols());
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols().getCashSymbolItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
        .get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbol(), "02");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()
            .get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbolAmount(),
        BigDecimal.valueOf(200));

    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getPkgTaskId(), 1598);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getRepFIO(), "Иванов Иван Никифорович");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getAmount(), BigDecimal.valueOf(300));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 14, 0));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getINN(), "123345667889");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getAccountId(), "112");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCardNumber(), "4894123566371254");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getReason(), "I want this");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getFDestLEName(), "Secret organization2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getCause(), "cause1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ON_APPROVAL);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getIdentityDocumentType(), IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getSeries(), "0825");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getNumber(), "954745");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getIssuedBy(), "OVD");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw()
        .getTaskItem().get(0).getIssuedDate(), xmlCalendar(2000, 1, 15, 0, 0));

    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getPkgTaskId(), 1599);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getOVNUId(), "112234");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getOVNNum(), BigInteger.valueOf(5));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getOVNStatus(), OVNStatusType.NEW);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getRepID(), "98971461998295679");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 13, 45));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getINN(), "123345667899");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getKPP(), "44762383");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getAccountId(), "111111");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSenderAccountId(), "157485");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getRecipientBank(), "Bank2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getRecipientBankBIC(), "043876166");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getSource(), "top secret");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getFDestLEName(), "Secret organization");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ACTIVE);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCause(), "cause1");
    Assert.assertTrue(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).isIsAuthСaporMutFund());
    Assert.assertNotNull(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols());
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols().getCashSymbolItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbol(), "02");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getCashSymbols().getCashSymbolItem().get(0).getCashSymbolAmount(),
        BigDecimal.valueOf(200));
    Assert.assertNotNull(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo());
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo().getStockholderitem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo().getStockholderitem().get(0).getStockholderName(),
        "Некто");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub()
        .getTaskItem().get(0).getStockholderInfo().getStockholderitem().get(0).getShare(),
        BigDecimal.valueOf(51));

    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getPkgTaskId(), 1600);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getRepFIO(), "Иванов Иван Никифорович");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getAmount(), BigDecimal.valueOf(300));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 14, 0));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getINN(), "123345667889");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getAccountId(), "112");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getSenderBank(), "Bank1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getSenderBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getReason(), "I want this");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).isIsClientTypeFK(), true);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getFDestLEName(), "Secret organization2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getPersonalAccountId(), "333");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getCause(), "cause1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ON_APPROVAL);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getIdentityDocumentType(), IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getSeries(), "0825");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getNumber(), "954745");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getIssuedBy(), "OVD");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub()
        .getTaskItem().get(0).getIssuedDate(), xmlCalendar(2000, 1, 15, 0, 0));

    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getPkgTaskId(), 1601);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCbRequestId(), "65588445522");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getBankName(), "Bank1");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getBankBIC(), "043876396");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getSubbranchCompositeCode(), "8463/13587");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCreatedDttm(), xmlCalendar(2017, 4, 28, 14, 0));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getLegalEntityShortName(), "short name2");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getAccountId(), "112");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getINN(), "123345667889");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getRepFIO(), "Иванов Иван Никифорович");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getPkgTaskStatus(), PkgTaskStatusType.ON_APPROVAL);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getIdentityDocumentType(), IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getSeries(), "0825");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getNumber(), "954745");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getIssuedBy(), "OVD");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getIssuedDate(), xmlCalendar(2000, 1, 15, 0, 0));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getDueDt(), xmlCalendar(2017, 9, 28, 0, 0));
    Assert.assertTrue(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).isContainsCheckbook());
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().size(), 1);
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
            .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().get(0).getCheckbookId(),
        "241546467546");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
            .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().get(0).getFirstCheckId(),
        "15800");
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
            .getTaskItem().get(0).getCheckbookInfo().getCheckbookItem().get(0).getCbPageAmount(),
        BigInteger.valueOf(100));
    Assert.assertEquals(request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing()
        .getTaskItem().get(0).getCbRequestStatus(), CbRequestStatusType.PENDING);
  }

  @Test
  public void testConvertSrvUpdTaskClOperPkgRs() {
    OperationPackage operationPackage = OperationPackageAdapter.convert(response5);
    assertHeaderInfo(operationPackage, FIX_UUID);
    Assert.assertEquals(operationPackage.getId(), Long.valueOf(2121));
    Assert.assertNull(operationPackage.getStatus());

    Assert.assertEquals(operationPackage.getToCardDeposits().size(), 1);
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getId(), Long.valueOf(1597));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getResponseCode(),
        Long.valueOf(1));
    Assert.assertEquals(operationPackage.getToCardDeposits().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getToCardDeposits().get(0).getReceiveDate());
    Assert.assertNull(operationPackage.getToCardDeposits().get(0).getStatus());

    Assert.assertEquals(operationPackage.getFromCardWithdraws().size(), 1);
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getId(), Long.valueOf(1598));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getResponseCode(),
        Long.valueOf(1));
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(operationPackage.getFromCardWithdraws().get(0).getReceiveDate());
    Assert.assertEquals(operationPackage.getFromCardWithdraws().get(0).getClass(),
        OperationTask.class);
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity1 = MultiAdapter.convert(response1);
    Assert.assertNotNull(externalEntity1);
    Assert.assertEquals(externalEntity1.getClass(), OperationPackage.class);
    Assert.assertEquals(((OperationPackage) externalEntity1).getToCardDeposits().size(), 1);

    ExternalEntity externalEntity2 = MultiAdapter.convert(response2);
    Assert.assertNotNull(externalEntity2);
    Assert.assertEquals(externalEntity2.getClass(), OperationPackage.class);
    Assert.assertEquals(((OperationPackage) externalEntity2).getStatus(),
        OperationPackageStatus.PACKED);

    ExternalEntity externalEntity3 = MultiAdapter.convert(response3);
    Assert.assertNotNull(externalEntity3);
    Assert.assertEquals(externalEntity3.getClass(), OperationPackage.class);
    Assert.assertEquals(((OperationPackage) externalEntity3).getResponseCode(), Long.valueOf(1));

    ExternalEntity externalEntity4 = MultiAdapter.convert(response4);
    Assert.assertNotNull(externalEntity4);
    Assert.assertEquals(externalEntity4.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity4).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntity4).getItems().get(0).getClass(),
        OperationPackage.class);
    Assert.assertEquals(((OperationPackage) ((ExternalEntityList) externalEntity4).getItems()
        .get(0)).getFromCardWithdraws().size(), 1);

    ExternalEntity externalEntity5 = MultiAdapter.convert(response5);
    Assert.assertNotNull(externalEntity5);
    Assert.assertEquals(externalEntity5.getClass(), OperationPackage.class);
    Assert.assertEquals(((OperationPackage) externalEntity5).getFromCardWithdraws().size(), 1);
  }
}
