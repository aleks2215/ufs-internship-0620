package ru.philit.ufs.model.converter.esb.asfs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.ConverterBaseTest;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.IDDtype;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashOrderTypeModel;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OverLimitStatus;
import ru.philit.ufs.model.entity.user.Subbranch;

public class CashOrderAdapterTest extends AsfsAdapterBaseTest {

  public static final String FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";

  private SrvCreateCashOrderRs createCashOrderRs;
  private SrvUpdStCashOrderRs updStCashOrderRs;
  private CashOrder cashOrder;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    createCashOrderRs = new SrvCreateCashOrderRs();
    createCashOrderRs.setHeaderInfo(headerInfo(FIX_UUID));
    createCashOrderRs.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    createCashOrderRs.getSrvCreateCashOrderRsMessage().setKO1(new KO1());
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderId("101");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderINum("50");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setCashOrderStatus(CashOrderStatusType.CREATED);
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setCashOrderType(CashOrderType.KO_1);
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setCreatedDttm(xmlCalendar(2020, 6, 24, 14, 52));
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setOperationId("1");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setRepFIO("Петров Петр Петрович");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setLegalEntityShortName("CO");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setINN("7703040404");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setAmount(BigDecimal.valueOf(100000));
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setAccountId("1001");
    CashSymbols cashSymbols = new CashSymbols();
    CashSymbols.CashSymbolItem cashSymbolItem = new CashSymbols.CashSymbolItem();
    cashSymbolItem.setCashSymbol("02");
    cashSymbolItem.setCashSymbolAmount(BigDecimal.valueOf(100000));
    cashSymbols.getCashSymbolItem().add(cashSymbolItem);
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setCashSymbols(cashSymbols);
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setSenderBank("ПАО \"Сбербанк\"");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setSenderBankBIC("044525225");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setRecipientBank("ПАО \"Сбербанк\"");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBankBIC("044525225");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setClientTypeFK(false);
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setFDestLEName("ООО \"Два Альбатроса\"");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setOperatorPosition("Оператор зала");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1()
        .setUserFullName("Иванов Иван Иванович");
    createCashOrderRs.getSrvCreateCashOrderRsMessage().getKO1().setUserPosition("Программист");

    updStCashOrderRs = new SrvUpdStCashOrderRs();
    updStCashOrderRs.setHeaderInfo(headerInfo(FIX_UUID));
    updStCashOrderRs.setSrvUpdCashOrderRsMessage(new SrvUpdCashOrderRsMessage());
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setResponseCode("0");
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setResponseMsg("resp_msg");
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setCashOrderId("101");
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setCashOrderINum("50");
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setCashOrderStatus(CashOrderStatusType.CREATED);
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setCashOrderType(CashOrderType.KO_1);

    cashOrder = new CashOrder();
    cashOrder.setResponseCode("0");
    cashOrder.setResponseMsg("resp_msg");
    cashOrder.setStatus(OverLimitStatus.LIMIT_PASSED);
    cashOrder.setCashOrderId("101");
    cashOrder.setCashOrderINum("50");
    cashOrder.setCashOrderStatus(CashOrderStatus.CREATED);
    cashOrder.setCashOrderTypeModel(CashOrderTypeModel.KO_1);
    cashOrder.setCreatedDttm(ConverterBaseTest.date(2020, 6, 24, 14, 52));
    cashOrder.setOperationId("1");
    cashOrder.setRepFio("Петров Петр Петрович");
    cashOrder.setLegalEntityShortName("СО");
    cashOrder.setInn("7703040404");

    cashOrder.setUserLogin("aleks_login");
    cashOrder.setTobeIncreased(true);
    cashOrder.setAmount(BigDecimal.valueOf(100000));

    cashOrder.setAccountId("1001");
    cashOrder.setCashSymbols(new ArrayList<CashSymbol>());
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setCode("02");
    cashSymbol.setAmount(BigDecimal.valueOf(70500));
    cashOrder.getCashSymbols().add(cashSymbol);

    cashOrder.setSenderBank("ПАО \"Сбербанк\"");
    cashOrder.setSenderBankBic("044525225");
    cashOrder.setRecipientBank("ПАО \"Сбербанк\"");
    cashOrder.setRecipientBankBic("044525225");
    cashOrder.setClientTypeFk(false);
    cashOrder.setOrganisationNameFk("ООО \"Два Альбатроса\"");
    cashOrder.setOperatorPosition("Оператор зала");
    cashOrder.setUserFullName("Иванов Иван Иванович");
    cashOrder.setUserPosition("Программист");

    cashOrder.setOperationType(OperationTypeCode.TO_CARD_DEPOSIT);
    cashOrder.setAmountInWords("Сто тысяч");
    cashOrder.setCurrencyType("RUB");
    cashOrder.setWorkPlaceUId("12");

    cashOrder.setRepId("500");
    cashOrder.setRepFio("Петров Петр Петрович");
    cashOrder.setAddress("Ул. Титова");
    cashOrder.setDateOfBirth(ConverterBaseTest.date(1980, 9, 30, 12, 0));
    cashOrder.setIdentityDocuments(new ArrayList<IdentityDocument>());
    IdentityDocument identityDocument = new IdentityDocument();
    identityDocument.setType(IdentityDocumentType.PASSPORT);
    identityDocument.setSeries("44 33");
    identityDocument.setNumber("444666");
    identityDocument.setIssuedDate(ConverterBaseTest.date(1991, 1, 1, 17, 30));
    identityDocument.setIssuedBy("МВД");
    cashOrder.getIdentityDocuments().add(identityDocument);

    cashOrder.setPlaceOfBirth("Москва");
    cashOrder.setResident(true);

    cashOrder.setSubbranch(new Subbranch());
    cashOrder.getSubbranch().setSubbranchCode("1385930102");
    cashOrder.getSubbranch().setVspCode("0102");
    cashOrder.getSubbranch().setOsbCode(null);
    cashOrder.getSubbranch().setGosbCode("8593");
    cashOrder.getSubbranch().setTbCode("13");

    cashOrder.setComment("Коммент");
    cashOrder.setAccount20202Num("20202897357400146292");
  }

  @Test
  public void testRequestCreateCashOrder() {
    SrvCreateCashOrderRq request = CashOrderAdapter.requestCreateCashOrder(cashOrder);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getCashOrderId(), "101");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getOperationType(),
        OperTypeLabel.TO_CARD_DEPOSIT);
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getCashOrderINum(), "50");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAccountId(), "1001");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAmount(),
        BigDecimal.valueOf(100000));
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAmountInWords(),
        "Сто тысяч");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getCurrencyType(), "RUB");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getCashOrderStatus(),
        CashOrderStatusType.CREATED);
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getWorkPlaceUId(), "12");
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage().getRepData());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData().getRepID(),
        "500");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData().getRepFIO(),
        "Петров Петр Петрович");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData().getAddress(),
        "Ул. Титова");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData().getDateOfBirth(),
        xmlCalendar(1980, 9, 30, 12, 0));
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType());
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType().size(), 1);
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType().get(0).getValue(), IDDtype.PASSPORT);
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType().get(0).getSeries(), "44 33");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType().get(0).getNumber(), "444666");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData()
            .getIdentityDocumentType().get(0).getIssuedDate(),
        xmlCalendar(1991, 1, 1, 17, 30));
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData()
        .getIdentityDocumentType().get(0).getIssuedBy(), "МВД");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData().getPlaceOfBirth(),
        "Москва");
    assertTrue(request.getSrvCreateCashOrderRqMessage().getRepData().isResident());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getRepData().getINN(),
        "7703040404");

    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo());
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getCashSymbols());
    Assert.assertNotNull(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getCashSymbols().getCashSymbolItem());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getCashSymbols().getCashSymbolItem().get(0).getCashSymbol(), "02");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
            .getCashSymbols().getCashSymbolItem().get(0).getCashSymbolAmount(),
        BigDecimal.valueOf(70500));
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getSubbranchCode(), "1385930102");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getVSPCode(), "0102");
    assertNull(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getOSBCode());
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getGOSBCode(), "8593");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getTBCode(), "13");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getComment(), "Коммент");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getAccount20202Num(), "20202897357400146292");
    Assert.assertEquals(request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .getUserLogin(), "aleks_login");
  }

  @Test
  public void testRequestUpdStCashOrder() {
    SrvUpdStCashOrderRq request = CashOrderAdapter.requestUpdStCashOrder(cashOrder);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvUpdCashOrderRqMessage());
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderId(), "101");
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderStatus(),
        CashOrderStatusType.CREATED);
  }

  @Test
  public void testConvertSrvCreateCashOrderRs() {
    CashOrder testCashOrder = CashOrderAdapter.convert(createCashOrderRs);
    assertHeaderInfo(testCashOrder,FIX_UUID);
    Assert.assertEquals(testCashOrder.getCashOrderId(), "101");
    Assert.assertEquals(testCashOrder.getCashOrderINum(), "50");
    Assert.assertEquals(testCashOrder.getCashOrderStatus(), CashOrderStatus.CREATED);
    Assert.assertEquals(testCashOrder.getCashOrderTypeModel(), CashOrderTypeModel.KO_1);
    Assert.assertEquals(testCashOrder.getCreatedDttm(),
        date(2020,6,24,14,52));
    Assert.assertEquals(testCashOrder.getOperationId(), "1");
    Assert.assertEquals(testCashOrder.getRepFio(), "Петров Петр Петрович");
    Assert.assertEquals(testCashOrder.getLegalEntityShortName(), "CO");
    Assert.assertEquals(testCashOrder.getInn(), "7703040404");
    Assert.assertEquals(testCashOrder.getAmount(), BigDecimal.valueOf(100000));
    Assert.assertEquals(testCashOrder.getAccountId(), "1001");
    Assert.assertNotNull(testCashOrder.getCashSymbols());
    Assert.assertEquals(testCashOrder.getCashSymbols().get(0).getCode(), "02");
    Assert.assertEquals(testCashOrder.getCashSymbols().get(0).getAmount(),
        BigDecimal.valueOf(100000));
    Assert.assertEquals(testCashOrder.getSenderBank(), "ПАО \"Сбербанк\"");
    Assert.assertEquals(testCashOrder.getSenderBankBic(), "044525225");
    Assert.assertEquals(testCashOrder.getRecipientBank(), "ПАО \"Сбербанк\"");
    Assert.assertEquals(testCashOrder.getRecipientBankBic(), "044525225");
    Assert.assertEquals(testCashOrder.getClientTypeFk(), false);
    Assert.assertEquals(testCashOrder.getOrganisationNameFk(), "ООО \"Два Альбатроса\"");
    Assert.assertEquals(testCashOrder.getOperatorPosition(), "Оператор зала");
    Assert.assertEquals(testCashOrder.getUserFullName(), "Иванов Иван Иванович");
    Assert.assertEquals(testCashOrder.getUserPosition(), "Программист");
  }

  @Test
  public void testConvertSrvUpdStCashOrderRs() {
    CashOrder testCashOrder = CashOrderAdapter.convert(updStCashOrderRs);
    assertHeaderInfo(testCashOrder,FIX_UUID);
    Assert.assertEquals(testCashOrder.getResponseCode(), "0");
    Assert.assertEquals(testCashOrder.getResponseMsg(), "resp_msg");
    Assert.assertEquals(testCashOrder.getCashOrderId(), "101");
    Assert.assertEquals(testCashOrder.getCashOrderINum(), "50");
    Assert.assertEquals(testCashOrder.getCashOrderStatus(), CashOrderStatus.CREATED);
    Assert.assertEquals(testCashOrder.getCashOrderTypeModel(), CashOrderTypeModel.KO_1);
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity1 = MultiAdapter.convert(createCashOrderRs);
    Assert.assertNotNull(externalEntity1);
    Assert.assertEquals(externalEntity1.getClass(), CashOrder.class);

    ExternalEntity externalEntity2 = MultiAdapter.convert(updStCashOrderRs);
    Assert.assertNotNull(externalEntity2);
    Assert.assertEquals(externalEntity2.getClass(), CashOrder.class);
  }

}