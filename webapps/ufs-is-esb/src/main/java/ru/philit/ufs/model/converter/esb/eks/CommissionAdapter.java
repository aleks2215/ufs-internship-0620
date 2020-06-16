package ru.philit.ufs.model.converter.esb.eks;

import java.math.BigDecimal;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.eks.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRq.SrvCountCommissionRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs.SrvCountCommissionRsMessage;

/**
 * Перобразователь данных о комиссии с транспортными объектами КСШ.
 */
public class CommissionAdapter extends EksAdapter {

  private static OperTypeLabel operTypeLabel(OperationTypeCode operationTypeCode) {
    return (operationTypeCode != null) ? OperTypeLabel.fromValue(operationTypeCode.code()) : null;
  }

  private static void map(AccountOperationRequest params, SrvCountCommissionRqMessage message) {
    message.setAccountId(params.getAccountId());
    message.setAmount(params.getAmount());
    message.setOperationType(operTypeLabel(params.getOperationTypeCode()));
  }

  private static void map(SrvCountCommissionRsMessage message,
      ExternalEntityContainer<BigDecimal> container) {
    container.setData(message.isIsCommission() ? message.getCommissionAmount() : null);
  }

  /**
   * Возвращает объект запроса расчёта комиссии.
   */
  public static SrvCountCommissionRq requestByParams(AccountOperationRequest params) {
    SrvCountCommissionRq request = new SrvCountCommissionRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCountCommissionRqMessage(new SrvCountCommissionRqMessage());
    map(params, request.getSrvCountCommissionRqMessage());
    return request;
  }

  /**
   * Преобразует транспортный объект ответа во внутреннюю сущность.
   */
  public static ExternalEntityContainer<BigDecimal> convert(SrvCountCommissionRs response) {
    ExternalEntityContainer<BigDecimal> container = new ExternalEntityContainer<>();
    map(response.getHeaderInfo(), container);
    map(response.getSrvCountCommissionRsMessage(), container);
    return container;
  }
}
