package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.PaymentPriority;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList2;
import ru.philit.ufs.model.entity.esb.eks.PaymentQueueType;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes1;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes1.CardIndex1Item;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item.PaymentOrderAmountPaidByDt;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item.PaymentOrderAmountPaidByDt.PaidByDtItem;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;

public class PaymentOrderAdapterTest extends EksAdapterBaseTest {

  private static final String ACCOUNT_ID = "111";
  private static final String FIX_UUID = "a55ed415-3976-41f7-912c-4c26ca79e969";

  private SrvCardIndexElementsByAccountRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvCardIndexElementsByAccountRs();
    response.setHeaderInfo(headerInfo(FIX_UUID));
    response.setSrvCardIndexElementsByAccountRsMessage(
        new SrvCardIndexElementsByAccountRsMessage());

    response.getSrvCardIndexElementsByAccountRsMessage().setCardIndexes1(new CardIndexes1());
    CardIndex1Item cardIndex1Item = new CardIndex1Item();
    cardIndex1Item.setDocSeqId(BigInteger.valueOf(1));
    cardIndex1Item.setPaymentOrderDocId("267");
    cardIndex1Item.setPaymentOrderDocType("type1");
    cardIndex1Item.setRecipientLegEntShortName("short name");
    cardIndex1Item.setPaymentOrderAmount(BigDecimal.valueOf(200));
    cardIndex1Item.setComeInDate(xmlCalendar(2017, 4, 17, 17, 0));
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes1().getCardIndex1Item()
        .add(cardIndex1Item);

    response.getSrvCardIndexElementsByAccountRsMessage().setCardIndexes2(new CardIndexes2());
    CardIndex2Item cardIndex2Item = new CardIndex2Item();
    cardIndex2Item.setDocSeqId(BigInteger.valueOf(11));
    cardIndex2Item.setPaymentOrderDocId("269");
    cardIndex2Item.setPaymentOrderDocType("type2");
    cardIndex2Item.setRecipientLegEntShortName("short name");
    cardIndex2Item.setPaymentOrderAmountTotal(BigDecimal.valueOf(300));
    cardIndex2Item.setPaymentOrderPaidPartly(true);
    cardIndex2Item.setPaymentOrderAmountPaid(BigDecimal.valueOf(100));
    cardIndex2Item.setComeInDate(xmlCalendar(2017, 4, 17, 18, 0));
    cardIndex2Item.setQueuePriority(PaymentQueueType.I);
    cardIndex2Item.setPaymentOrderAmountPaidByDt(new PaymentOrderAmountPaidByDt());
    PaidByDtItem paidByDtItem1 = new PaidByDtItem();
    paidByDtItem1.setPaidAmount(BigDecimal.valueOf(20));
    paidByDtItem1.setPaidDt(xmlCalendar(2017, 4, 18, 12, 0));
    cardIndex2Item.getPaymentOrderAmountPaidByDt().getPaidByDtItem().add(paidByDtItem1);
    PaidByDtItem paidByDtItem2 = new PaidByDtItem();
    paidByDtItem2.setPaidAmount(BigDecimal.valueOf(80));
    paidByDtItem2.setPaidDt(xmlCalendar(2017, 4, 19, 12, 0));
    cardIndex2Item.getPaymentOrderAmountPaidByDt().getPaidByDtItem().add(paidByDtItem2);
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes2().getCardIndex2Item()
        .add(cardIndex2Item);
  }

  @Test
  public void testRequestByAccount() {
    SrvCardIndexElementsByAccountRq request = PaymentOrderAdapter.requestByAccount(ACCOUNT_ID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCardIndexElementsByAccountRqMessage());
    Assert.assertEquals(request.getSrvCardIndexElementsByAccountRqMessage().getAccountId(),
        ACCOUNT_ID);
  }

  @Test
  public void testConvertSrvCardIndexElementsByAccountRs() {
    ExternalEntityList2<PaymentOrderCardIndex1, PaymentOrderCardIndex2> paymentOrderList2 =
        PaymentOrderAdapter.convert(response);
    assertHeaderInfo(paymentOrderList2, FIX_UUID);

    Assert.assertEquals(paymentOrderList2.getItems().size(), 1);
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getNum(), Long.valueOf(1));
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getDocId(), "267");
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getDocType(), "type1");
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getRecipientShortName(), "short name");
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getAmount(), BigDecimal.valueOf(200));
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getComeInDate(),
        date(2017, 4, 17, 17, 0));
    Assert.assertEquals(paymentOrderList2.getItems().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(paymentOrderList2.getItems().get(0).getReceiveDate());

    Assert.assertEquals(paymentOrderList2.getItems2().size(), 1);
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getNum(), Long.valueOf(11));
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getDocId(), "269");
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getDocType(), "type2");
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getRecipientShortName(), "short name");
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getTotalAmount(),
        BigDecimal.valueOf(300));
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).isPaidPartly(), true);
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getPartAmount(),
        BigDecimal.valueOf(100));
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getComeInDate(),
        date(2017, 4, 17, 18, 0));
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getPaymentPriority(),
        PaymentPriority.I);
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getPaidParts().size(), 2);
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getPaidParts().get(0).getPaidAmount(),
        BigDecimal.valueOf(20));
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getPaidParts().get(0).getPaidDate(),
        date(2017, 4, 18, 12, 0));
    Assert.assertEquals(paymentOrderList2.getItems2().get(0).getRequestUid(), FIX_UUID);
    Assert.assertNotNull(paymentOrderList2.getItems2().get(0).getReceiveDate());
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityList2.class);
    Assert.assertEquals(((ExternalEntityList2) externalEntity).getItems2().size(), 1);
    Assert.assertEquals(((ExternalEntityList2) externalEntity).getItems2().get(0).getClass(),
        PaymentOrderCardIndex2.class);
  }
}
