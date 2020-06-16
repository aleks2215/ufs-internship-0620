package ru.philit.ufs.model.converter.esb.eks;

import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRq.SrvSeizureByAccountRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs.SrvSeizureByAccountRsMessage.SrvSeizureByAccountItem;

/**
 * Преобразователь между сущностью Seizure и соответствующим транспортным объектом.
 */
public class SeizureAdapter extends EksAdapter {

  private static void map(SrvSeizureByAccountItem messageItem, Seizure seizure) {
    seizure.setId(longValue(messageItem.getSeizureSequenceId()));
    seizure.setType(longValue(messageItem.getSeizureType()));
    seizure.setReason(messageItem.getSeizureReasonDesc());
    seizure.setFromDate(date(messageItem.getDateFrom()));
    seizure.setToDate(date(messageItem.getDateTo()));
    seizure.setAmount(messageItem.getAmount());
    seizure.setInitiatorShortName(messageItem.getInitiatorShortName());
  }

  /**
   * Возвращает объект запроса арестов сумм по номеру счёта.
   */
  public static SrvSeizureByAccountRq requestByAccount(String accountId) {
    SrvSeizureByAccountRq request = new SrvSeizureByAccountRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvSeizureByAccountRqMessage(new SrvSeizureByAccountRqMessage());
    request.getSrvSeizureByAccountRqMessage().setAccountId(accountId);
    return request;
  }

  /**
   * Преобразует транспортный объект перечня арестов сумм во внутреннюю сущность.
   */
  public static ExternalEntityList<Seizure> convert(SrvSeizureByAccountRs response) {
    ExternalEntityList<Seizure> seizureList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), seizureList);

    for (SrvSeizureByAccountItem seizureByAccountItem :
        response.getSrvSeizureByAccountRsMessage().getSrvSeizureByAccountItem()) {
      Seizure seizure = new Seizure();
      map(response.getHeaderInfo(), seizure);
      map(seizureByAccountItem, seizure);
      seizureList.getItems().add(seizure);
    }
    return seizureList;
  }
}
