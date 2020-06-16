package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.AccountController#getAccountData}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetAccountDataReq extends BaseRequest {

  /**
   * Номер карты.
   */
  private String cardNumber;

}
