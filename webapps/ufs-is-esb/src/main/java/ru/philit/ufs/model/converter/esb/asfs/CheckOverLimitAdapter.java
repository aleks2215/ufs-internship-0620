package ru.philit.ufs.model.converter.esb.asfs;

import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;
import ru.philit.ufs.model.entity.oper.OverLimitStatus;

public class CheckOverLimitAdapter extends AsfsAdapter {

  //******** Mappers *******

  private static void map(CheckOverLimitRequest checkOverLimitRequest,
      SrvCheckOverLimitRqMessage message) {
    message.setUserLogin(checkOverLimitRequest.getUserLogin());
    message.setTobeIncreased(checkOverLimitRequest.isTobeIncreased());
    message.setAmount(checkOverLimitRequest.getAmount());
  }

  private static void map(SrvCheckOverLimitRsMessage message,
      ExternalEntityContainer<Boolean> container) {
    container.setData(OverLimitStatus.getByName(message.getStatus().value()).value());
    container.setResponseCode(message.getResponseCode());
  }

  /**
   * Возвращает объект запроса перелимита общего остатка.
   */
  public static SrvCheckOverLimitRq requestCheckOverLimit(
      CheckOverLimitRequest checkOverLimitRequestRequest) {
    SrvCheckOverLimitRq request = new SrvCheckOverLimitRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCheckOverLimitRqMessage(new SrvCheckOverLimitRqMessage());
    map(checkOverLimitRequestRequest, request.getSrvCheckOverLimitRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект ответа о перелимите общего остатка во внутреннюю сущность.
   */
  public static ExternalEntityContainer<Boolean> convert(SrvCheckOverLimitRs response) {
    ExternalEntityContainer<Boolean> container = new ExternalEntityContainer<>();
    map(response.getHeaderInfo(), container);
    map(response.getSrvCheckOverLimitRsMessage(), container);
    return container;
  }

}
