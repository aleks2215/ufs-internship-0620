package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.ServiceController#logEvent}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class LogEventReq extends BaseRequest {

  /**
   * Событие.
   */
  private String event;
  /**
   * Дата и время совершения.
   */
  private String date;

}
