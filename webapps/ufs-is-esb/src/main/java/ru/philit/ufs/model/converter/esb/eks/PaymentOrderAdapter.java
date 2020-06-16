package ru.philit.ufs.model.converter.esb.eks;

import ru.philit.ufs.model.entity.account.PaymentPriority;
import ru.philit.ufs.model.entity.common.ExternalEntityList2;
import ru.philit.ufs.model.entity.esb.eks.PaymentQueueType;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRq.SrvCardIndexElementsByAccountRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes1.CardIndex1Item;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item.PaymentOrderAmountPaidByDt.PaidByDtItem;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2.PaidPart;

/**
 * Преобразователь между сущностями PaymentOrderCardIndex и соответствующим транспортным объектом.
 */
public class PaymentOrderAdapter extends EksAdapter {

  private static PaymentPriority paymentPriority(PaymentQueueType queueType) {
    return (queueType != null) ? PaymentPriority.getByCode(queueType.name()) : null;
  }

  private static void map(CardIndex1Item cardIndex1Item, PaymentOrderCardIndex1 paymentOrder) {
    paymentOrder.setNum(longValue(cardIndex1Item.getDocSeqId()));
    paymentOrder.setDocId(cardIndex1Item.getPaymentOrderDocId());
    paymentOrder.setDocType(cardIndex1Item.getPaymentOrderDocType());
    paymentOrder.setRecipientShortName(cardIndex1Item.getRecipientLegEntShortName());
    paymentOrder.setAmount(cardIndex1Item.getPaymentOrderAmount());
    paymentOrder.setComeInDate(date(cardIndex1Item.getComeInDate()));
  }

  private static void map(CardIndex2Item cardIndex2Item, PaymentOrderCardIndex2 paymentOrder) {
    paymentOrder.setNum(longValue(cardIndex2Item.getDocSeqId()));
    paymentOrder.setDocId(cardIndex2Item.getPaymentOrderDocId());
    paymentOrder.setDocType(cardIndex2Item.getPaymentOrderDocType());
    paymentOrder.setRecipientShortName(cardIndex2Item.getRecipientLegEntShortName());
    paymentOrder.setTotalAmount(cardIndex2Item.getPaymentOrderAmountTotal());
    paymentOrder.setPaidPartly(cardIndex2Item.isPaymentOrderPaidPartly());
    paymentOrder.setPartAmount(cardIndex2Item.getPaymentOrderAmountPaid());
    paymentOrder.setComeInDate(date(cardIndex2Item.getComeInDate()));
    paymentOrder.setPaymentPriority(paymentPriority(cardIndex2Item.getQueuePriority()));

    if (cardIndex2Item.getPaymentOrderAmountPaidByDt() != null) {
      for (PaidByDtItem paidItem : cardIndex2Item.getPaymentOrderAmountPaidByDt()
          .getPaidByDtItem()) {
        PaidPart paidPart = new PaidPart();
        map(paidItem, paidPart);
        paymentOrder.getPaidParts().add(paidPart);
      }
    }
  }

  private static void map(PaidByDtItem paidItem, PaidPart paidPart) {
    paidPart.setPaidAmount(paidItem.getPaidAmount());
    paidPart.setPaidDate(date(paidItem.getPaidDt()));
  }

  /**
   * Возвращает объект запроса РКО по номеру счёта.
   */
  public static SrvCardIndexElementsByAccountRq requestByAccount(String accountId) {
    SrvCardIndexElementsByAccountRq request = new SrvCardIndexElementsByAccountRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCardIndexElementsByAccountRqMessage(new SrvCardIndexElementsByAccountRqMessage());
    request.getSrvCardIndexElementsByAccountRqMessage().setAccountId(accountId);
    return request;
  }

  /**
   * Преобразует транспортный объект списка РКО во внутреннюю сущность.
   */
  public static ExternalEntityList2<PaymentOrderCardIndex1, PaymentOrderCardIndex2> convert(
      SrvCardIndexElementsByAccountRs response) {
    ExternalEntityList2<PaymentOrderCardIndex1, PaymentOrderCardIndex2> paymentOrderLists =
        new ExternalEntityList2<>();
    map(response.getHeaderInfo(), paymentOrderLists);

    for (CardIndex1Item cardIndex1Item :
        response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes1()
            .getCardIndex1Item()) {
      PaymentOrderCardIndex1 paymentOrder = new PaymentOrderCardIndex1();
      map(response.getHeaderInfo(), paymentOrder);
      map(cardIndex1Item, paymentOrder);
      paymentOrderLists.getItems().add(paymentOrder);
    }

    for (CardIndex2Item cardIndex2Item :
        response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes2()
            .getCardIndex2Item()) {
      PaymentOrderCardIndex2 paymentOrder = new PaymentOrderCardIndex2();
      map(response.getHeaderInfo(), paymentOrder);
      map(cardIndex2Item, paymentOrder);
      paymentOrderLists.getItems2().add(paymentOrder);
    }

    return paymentOrderLists;
  }
}
