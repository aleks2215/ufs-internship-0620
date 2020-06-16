package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.AnnouncementController#getCommission}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetCommissionReq extends BaseRequest {

  /**
   * Номер счета.
   */
  private String accountId;
  /**
   * Сумма операции.
   */
  private String amount;
  /**
   * Код типа операции.
   */
  private String typeCode;

}
