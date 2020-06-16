package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.PosController#verify}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class PosVerifyReq extends BaseRequest {

  /**
   * Номер карты.
   */
  private String number;
  /**
   * Пин-код, введённый пользователем.
   */
  private String pin;

}
