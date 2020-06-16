package ru.philit.ufs.model.converter.esb.eks;

import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.eks.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRq.SrvCheckWithFraudRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs.SrvCheckWithFraudRsMessage;

/**
 * Перобразователь данных проверки операции на Fraud с транспортными объектами КСШ.
 */
public class CheckFraudAdapter extends EksAdapter {

  private static OperTypeLabel operTypeLabel(OperationTypeCode operationTypeCode) {
    return (operationTypeCode != null) ? OperTypeLabel.fromValue(operationTypeCode.code()) : null;
  }

  private static void map(AccountOperationRequest params, SrvCheckWithFraudRqMessage message) {
    message.setAccountId(params.getAccountId());
    message.setAmount(params.getAmount());
    message.setOperationType(operTypeLabel(params.getOperationTypeCode()));
  }

  private static void map(SrvCheckWithFraudRsMessage message,
      ExternalEntityContainer<Boolean> container) {
    container.setData(message.isIsWithFraud());
    container.setResponseCode(message.getResponseCode());
  }

  /**
   * Возвращает объект запроса проверки операции.
   */
  public static SrvCheckWithFraudRq requestByParams(AccountOperationRequest params) {
    SrvCheckWithFraudRq request = new SrvCheckWithFraudRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCheckWithFraudRqMessage(new SrvCheckWithFraudRqMessage());
    map(params, request.getSrvCheckWithFraudRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект ответа во внутреннюю сущность.
   */
  public static ExternalEntityContainer<Boolean> convert(SrvCheckWithFraudRs response) {
    ExternalEntityContainer<Boolean> container = new ExternalEntityContainer<>();
    map(response.getHeaderInfo(), container);
    map(response.getSrvCheckWithFraudRsMessage(), container);
    return container;
  }
}
