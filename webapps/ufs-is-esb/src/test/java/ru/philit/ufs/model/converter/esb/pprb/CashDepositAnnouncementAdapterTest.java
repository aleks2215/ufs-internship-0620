package ru.philit.ufs.model.converter.esb.pprb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.pprb.OVNStatusType;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs.SrvCashDepAnmntListByAccountIdRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs.SrvCashDepAnmntListByAccountIdRsMessage.CashDepAnmntList;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs.SrvCreateCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs.SrvGetCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs.SrvGetCashDepAnmntItemRsMessage.CashSymbols;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs.SrvGetCashDepAnmntItemRsMessage.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs.SrvUpdCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OvnStatus;

public class CashDepositAnnouncementAdapterTest extends PprbAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-41f7-916c-4c17ca79e969";

  private CashDepositAnnouncementsRequest ovnRequest;
  private SrvCashDepAnmntListByAccountIdRs response1;
  private CashDepositAnnouncement ovn;
  private SrvCreateCashDepAnmntItemRs response2;
  private SrvGetCashDepAnmntItemRs response3;
  private SrvUpdCashDepAnmntItemRs response4;
  private SrvCashSymbolsListRs response5;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    ovnRequest = new CashDepositAnnouncementsRequest();
    ovnRequest.setAccountId("40702810638050013199");
    ovnRequest.setStatus(OvnStatus.PENDING);

    response1 = new SrvCashDepAnmntListByAccountIdRs();
    response1.setHeaderInfo(headerInfo(FIX_UUID));
    response1.setSrvCashDepAnmntListByAccountIdRsMessage(
        new SrvCashDepAnmntListByAccountIdRsMessage());
    CashDepAnmntList cashDepAnmnt = new CashDepAnmntList();
    cashDepAnmnt.setOVNUId("FD1D833A431F498DB556E91998C8C8A5");
    cashDepAnmnt.setOVNNum(BigInteger.valueOf(1));
    cashDepAnmnt.setOVNStatus(OVNStatusType.PENDING);
    cashDepAnmnt.setAmount(BigDecimal.valueOf(10000));
    cashDepAnmnt.setCreatedDttm(xmlCalendar(2017, 5, 4, 9, 30));
    cashDepAnmnt.setINN("0278000222");
    cashDepAnmnt.setAccountId("40702810638050013199");
    cashDepAnmnt.setLegalEntityShortName("ООО \"ДНК\"");
    cashDepAnmnt.setRepFIO("Петров Петр Петрович");
    response1.getSrvCashDepAnmntListByAccountIdRsMessage().getCashDepAnmntList().add(cashDepAnmnt);

    ovn = new CashDepositAnnouncement();
    ovn.setNum(101L);
    ovn.setStatus(OvnStatus.COMPLETED);
    ovn.setRepFio("Петров Петр Петрович");
    ovn.setLegalEntityShortName("ООО \"ДНК\"");
    ovn.setAmount(BigDecimal.valueOf(10000));
    ovn.setAmountInWords("Десять тысяч рублей");
    ovn.setAmountCop(99);
    ovn.setCreatedDate(date(2017, 5, 4, 9, 30));
    ovn.setInn("0278000222");
    ovn.setKpp("0278000222");
    ovn.setAccountId("40702810638050013199");
    ovn.setSenderAccountId("40702810638050013199");
    ovn.setSenderBank("ПАО \"Сбербанк\"");
    ovn.setSenderBankBic("044525225");
    ovn.setRecipientBank("ПАО \"Сбербанк\"");
    ovn.setRecipientAccountId("40702810638050013199");
    ovn.setRecipientBankBic("044525225");
    ovn.setSource("Торговая выручка");
    ovn.setClientTypeFk(true);
    ovn.setOrganisationNameFk("ООО \"Синстройопт\"");
    ovn.setPersonalAccountId("40502987865430001897");
    ovn.setCurrencyType("RUB");
    ovn.setCreatedDate(date(2017, 5, 4, 9, 30));
    ovn.setOperationDate(date(2017, 5, 4, 9, 30));
    ovn.setResponseCode("0");
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setCode("A");
    cashSymbol.setAmount(BigDecimal.TEN);
    cashSymbol.setDescription("Описание А");
    cashSymbol.setRequestUid("EAFDE44F5F664A4A95E9CFF71448FA12");
    cashSymbol.setReceiveDate(date(2017, 5, 4, 9, 30));
    List<CashSymbol> cashSymbols = new ArrayList<>();
    cashSymbols.add(cashSymbol);
    ovn.setCashSymbols(cashSymbols);
    ovn.setAccountantPosition("Бухгалтер");
    ovn.setAccountantFullName("Петров Петр Петрович");
    ovn.setUserPosition("Кассир");
    ovn.setUserFullName("Петров Петр Петрович");

    response2 = new SrvCreateCashDepAnmntItemRs();
    response2.setHeaderInfo(headerInfo(FIX_UUID));
    response2.setSrvCreateCashDepAnmntItemRsMessage(new SrvCreateCashDepAnmntItemRsMessage());
    response2.getSrvCreateCashDepAnmntItemRsMessage().setOVNUId("EAFDE44F5F664A4A95E9CFF71448FA12");
    response2.getSrvCreateCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(1));
    response2.getSrvCreateCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.COMPLETED);
    response2.getSrvCreateCashDepAnmntItemRsMessage().setResponseCode("0");

    response3 = new SrvGetCashDepAnmntItemRs();
    response3.setHeaderInfo(headerInfo(FIX_UUID));
    response3.setSrvGetCashDepAnmntItemRsMessage(new SrvGetCashDepAnmntItemRsMessage());
    response3.getSrvGetCashDepAnmntItemRsMessage().setOVNUId("FD1D833A431F498DB556E91998C8C8A5");
    response3.getSrvGetCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(1));
    response3.getSrvGetCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.PENDING);
    response3.getSrvGetCashDepAnmntItemRsMessage().setRepFIO("Петров Петр Петрович");
    response3.getSrvGetCashDepAnmntItemRsMessage().setLegalEntityShortName("ООО \"ДНК\"");
    response3.getSrvGetCashDepAnmntItemRsMessage().setAmount(BigDecimal.valueOf(10000));
    response3.getSrvGetCashDepAnmntItemRsMessage().setCreatedDttm(xmlCalendar(2017, 5, 4, 9, 30));
    response3.getSrvGetCashDepAnmntItemRsMessage().setINN("0278000222");
    response3.getSrvGetCashDepAnmntItemRsMessage().setKPP("0278000222");
    response3.getSrvGetCashDepAnmntItemRsMessage().setAccountId("40702810638050013199");
    response3.getSrvGetCashDepAnmntItemRsMessage().setSenderAccountId("40702810638050013199");
    response3.getSrvGetCashDepAnmntItemRsMessage().setSenderBank("ПАО \"Сбербанк\"");
    response3.getSrvGetCashDepAnmntItemRsMessage().setSenderBankBIC("044525225");
    response3.getSrvGetCashDepAnmntItemRsMessage().setRecipientBank("ПАО \"Сбербанк\"");
    response3.getSrvGetCashDepAnmntItemRsMessage().setRecipientBankBIC("044525225");
    response3.getSrvGetCashDepAnmntItemRsMessage().setSource("Торговая выручка");
    response3.getSrvGetCashDepAnmntItemRsMessage().setIsClientTypeFK(true);
    response3.getSrvGetCashDepAnmntItemRsMessage().setFDestLEName("ООО \"Синстройопт\"");
    response3.getSrvGetCashDepAnmntItemRsMessage().setPersonalAccountId("40502987865430001897");
    response3.getSrvGetCashDepAnmntItemRsMessage().setCurrencyType("RUB");
    CashSymbols cashSymbols1 = new CashSymbols();
    CashSymbolItem cashSymbolItem1 = new CashSymbolItem();
    cashSymbolItem1.setCashSymbol("A");
    cashSymbolItem1.setCashSymbolAmount(BigDecimal.TEN);
    cashSymbols1.getCashSymbolItem().add(cashSymbolItem1);
    response3.getSrvGetCashDepAnmntItemRsMessage().setCashSymbols(cashSymbols1);

    response4 = new SrvUpdCashDepAnmntItemRs();
    response4.setHeaderInfo(headerInfo(FIX_UUID));
    response4.setSrvUpdCashDepAnmntItemRsMessage(new SrvUpdCashDepAnmntItemRsMessage());
    response4.getSrvUpdCashDepAnmntItemRsMessage().setOVNUId("FD1D833A431F498DB556E91998C8C8A5");
    response4.getSrvUpdCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(1));
    response4.getSrvUpdCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.COMPLETED);
    response4.getSrvUpdCashDepAnmntItemRsMessage().setResponseCode("0");
  }

  @Test
  public void testRequestOvnList() {
    SrvCashDepAnmntListByAccountIdRq request =
        CashDepositAnnouncementAdapter.requestOvnList(ovnRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCashDepAnmntListByAccountIdRqMessage());
    Assert.assertEquals(request.getSrvCashDepAnmntListByAccountIdRqMessage().getAccountId(),
        "40702810638050013199");
    Assert.assertEquals(request.getSrvCashDepAnmntListByAccountIdRqMessage().getOVNStatus(),
        OVNStatusType.PENDING);
  }

  @Test
  public void testConvertSrvCashDepAnmntListByAccountIdRs() {
    ExternalEntityList<CashDepositAnnouncement> ovnList =
        CashDepositAnnouncementAdapter.convert(response1);
    assertHeaderInfo(ovnList, FIX_UUID);
    Assert.assertEquals(ovnList.getItems().size(), 1);
    Assert.assertEquals(ovnList.getItems().get(0).getUid(), "FD1D833A431F498DB556E91998C8C8A5");
    Assert.assertEquals(ovnList.getItems().get(0).getNum(), Long.valueOf(1));
    Assert.assertEquals(ovnList.getItems().get(0).getStatus(), OvnStatus.PENDING);
    Assert.assertEquals(ovnList.getItems().get(0).getAmount(), BigDecimal.valueOf(10000));
    Assert.assertEquals(ovnList.getItems().get(0).getCreatedDate(), date(2017, 5, 4, 9, 30));
    Assert.assertEquals(ovnList.getItems().get(0).getInn(), "0278000222");
    Assert.assertEquals(ovnList.getItems().get(0).getAccountId(), "40702810638050013199");
    Assert.assertEquals(ovnList.getItems().get(0).getLegalEntityShortName(), "ООО \"ДНК\"");
    Assert.assertEquals(ovnList.getItems().get(0).getRepFio(), "Петров Петр Петрович");
    Assert.assertNull(ovnList.getItems().get(0).getPersonalAccountId());
  }

  @Test
  public void testRequestCreateOvn() {
    SrvCreateCashDepAnmntItemRq request = CashDepositAnnouncementAdapter.requestCreateOvn(ovn);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCreateCashDepAnmntItemRqMessage());
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getOVNStatus(),
        OVNStatusType.COMPLETED);
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getRepFIO(),
        "Петров Петр Петрович");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getLegalEntityShortName(),
        "ООО \"ДНК\"");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getAmount(),
        BigDecimal.valueOf(10000));
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getCreatedDttm(),
        xmlCalendar(2017, 5, 4, 9, 30));
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getINN(),
        "0278000222");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getAccountId(),
        "40702810638050013199");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getSenderBank(),
        "ПАО \"Сбербанк\"");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getSenderBankBIC(),
        "044525225");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getRecipientBank(),
        "ПАО \"Сбербанк\"");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getRecipientBankBIC(),
        "044525225");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getSource(),
        "Торговая выручка");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().isIsClientTypeFK(),
        true);
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getFDestLEName(),
        "ООО \"Синстройопт\"");
    Assert.assertEquals(request.getSrvCreateCashDepAnmntItemRqMessage().getPersonalAccountId(),
        "40502987865430001897");
  }

  @Test
  public void testConvertSrvCreateCashDepAnmntItemRs() {
    CashDepositAnnouncement ovn = CashDepositAnnouncementAdapter.convert(response2);
    assertHeaderInfo(ovn, FIX_UUID);
    Assert.assertEquals(ovn.getUid(), "EAFDE44F5F664A4A95E9CFF71448FA12");
    Assert.assertEquals(ovn.getNum(), Long.valueOf(1));
    Assert.assertEquals(ovn.getStatus(), OvnStatus.COMPLETED);
    Assert.assertEquals(ovn.getResponseCode(), "0");
  }

  @Test
  public void testRequestGetOvn() {
    SrvGetCashDepAnmntItemRq request =
        CashDepositAnnouncementAdapter.requestGetOvn("FD1D833A431F498DB556E91998C8C8A5");
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetCashDepAnmntItemRqMessage());
    Assert.assertEquals(request.getSrvGetCashDepAnmntItemRqMessage().getOVNUId(),
        "FD1D833A431F498DB556E91998C8C8A5");
  }

  @Test
  public void testConvertSrvGetCashDepAnmntItemRs() {
    CashDepositAnnouncement ovn = CashDepositAnnouncementAdapter.convert(response3);
    assertHeaderInfo(ovn, FIX_UUID);
    Assert.assertNull(ovn.getResponseCode());
    Assert.assertEquals(ovn.getUid(), "FD1D833A431F498DB556E91998C8C8A5");
    Assert.assertEquals(ovn.getNum(), Long.valueOf(1));
    Assert.assertEquals(ovn.getStatus(), OvnStatus.PENDING);
    Assert.assertEquals(ovn.getRepFio(), "Петров Петр Петрович");
    Assert.assertEquals(ovn.getLegalEntityShortName(), "ООО \"ДНК\"");
    Assert.assertEquals(ovn.getAmount(), BigDecimal.valueOf(10000));
    Assert.assertEquals(ovn.getCreatedDate(), date(2017, 5, 4, 9, 30));
    Assert.assertEquals(ovn.getInn(), "0278000222");
    Assert.assertEquals(ovn.getKpp(), "0278000222");
    Assert.assertEquals(ovn.getAccountId(), "40702810638050013199");
    Assert.assertEquals(ovn.getSenderAccountId(), "40702810638050013199");
    Assert.assertEquals(ovn.getSenderBank(), "ПАО \"Сбербанк\"");
    Assert.assertEquals(ovn.getSenderBankBic(), "044525225");
    Assert.assertEquals(ovn.getRecipientBank(), "ПАО \"Сбербанк\"");
    Assert.assertEquals(ovn.getRecipientBankBic(), "044525225");
    Assert.assertEquals(ovn.getSource(), "Торговая выручка");
    Assert.assertEquals(ovn.isClientTypeFk(), true);
    Assert.assertEquals(ovn.getOrganisationNameFk(), "ООО \"Синстройопт\"");
    Assert.assertEquals(ovn.getPersonalAccountId(), "40502987865430001897");
    Assert.assertEquals(ovn.getCurrencyType(), "RUB");
    Assert.assertNotNull(ovn.getCashSymbols());
    Assert.assertEquals(ovn.getCashSymbols().size(), 1);
    Assert.assertEquals(ovn.getCashSymbols().get(0).getCode(), "A");
    Assert.assertEquals(ovn.getCashSymbols().get(0).getAmount(), BigDecimal.TEN);

  }

  @Test
  public void testRequestUpdateOvn() {
    ovn.setUid("FD1D833A431F498DB556E91998C8C8A5");

    SrvUpdCashDepAnmntItemRq request = CashDepositAnnouncementAdapter.requestUpdateOvn(ovn);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvUpdCashDepAnmntItemRqMessage());
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getOVNUId(),
        "FD1D833A431F498DB556E91998C8C8A5");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getOVNNum(),
        BigInteger.valueOf(101));
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getOVNStatus(),
        OVNStatusType.COMPLETED);
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getRepFIO(),
        "Петров Петр Петрович");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getLegalEntityShortName(),
        "ООО \"ДНК\"");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getAmount(),
        BigDecimal.valueOf(10000));
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getAmountInWords(),
        "Десять тысяч рублей");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getAmountCop(),
        Integer.valueOf(99));
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getCreatedDttm(),
        xmlCalendar(2017, 5, 4, 9, 30));
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getOperationDttm(),
        xmlCalendar(2017, 5, 4, 9, 30));
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getINN(),
        "0278000222");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getKPP(),
        "0278000222");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getAccountId(),
        "40702810638050013199");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getSenderAccountId(),
        "40702810638050013199");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getSenderBank(),
        "ПАО \"Сбербанк\"");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getSenderBankBIC(),
        "044525225");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getRecipientAccountId(),
        "40702810638050013199");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getRecipientBank(),
        "ПАО \"Сбербанк\"");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getRecipientBankBIC(),
        "044525225");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getSource(),
        "Торговая выручка");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().isIsClientTypeFK(),
        true);
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getFDestLEName(),
        "ООО \"Синстройопт\"");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getPersonalAccountId(),
        "40502987865430001897");
    Assert.assertEquals(request.getSrvUpdCashDepAnmntItemRqMessage().getCurrencyType(),
        "RUB");
    Assert.assertNotNull(request.getSrvUpdCashDepAnmntItemRqMessage().getCashSymbols());
    Assert.assertNotNull(
        request.getSrvUpdCashDepAnmntItemRqMessage().getCashSymbols().getCashSymbolItem());
    Assert.assertEquals(
        request.getSrvUpdCashDepAnmntItemRqMessage().getCashSymbols().getCashSymbolItem().size(),
        1);
    Assert.assertEquals(
        request.getSrvUpdCashDepAnmntItemRqMessage().getCashSymbols().getCashSymbolItem().get(0)
            .getCashSymbol(), "A");
    Assert.assertEquals(
        request.getSrvUpdCashDepAnmntItemRqMessage().getCashSymbols().getCashSymbolItem().get(0)
            .getCashSymbolAmount(), BigDecimal.TEN);
  }

  @Test
  public void testConvertSrvUpdCashDepAnmntItemRs() {
    CashDepositAnnouncement ovn = CashDepositAnnouncementAdapter.convert(response4);
    assertHeaderInfo(ovn, FIX_UUID);
    Assert.assertEquals(ovn.getUid(), "FD1D833A431F498DB556E91998C8C8A5");
    Assert.assertEquals(ovn.getNum(), Long.valueOf(1));
    Assert.assertEquals(ovn.getStatus(), OvnStatus.COMPLETED);
    Assert.assertEquals(ovn.getResponseCode(), "0");
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity1 = MultiAdapter.convert(response1);
    Assert.assertNotNull(externalEntity1);
    Assert.assertEquals(externalEntity1.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity1).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntity1).getItems().get(0).getClass(),
        CashDepositAnnouncement.class);

    ExternalEntity externalEntity2 = MultiAdapter.convert(response2);
    Assert.assertNotNull(externalEntity2);
    Assert.assertEquals(externalEntity2.getClass(), CashDepositAnnouncement.class);
    Assert.assertEquals(((CashDepositAnnouncement) externalEntity2).getUid(),
        "EAFDE44F5F664A4A95E9CFF71448FA12");

    ExternalEntity externalEntity3 = MultiAdapter.convert(response3);
    Assert.assertNotNull(externalEntity3);
    Assert.assertEquals(externalEntity3.getClass(), CashDepositAnnouncement.class);
    Assert.assertEquals(((CashDepositAnnouncement) externalEntity3).getAccountId(),
        "40702810638050013199");

    ExternalEntity externalEntity4 = MultiAdapter.convert(response4);
    Assert.assertNotNull(externalEntity4);
    Assert.assertEquals(externalEntity4.getClass(), CashDepositAnnouncement.class);
    Assert.assertEquals(((CashDepositAnnouncement) externalEntity4).getResponseCode(), "0");
  }
}
