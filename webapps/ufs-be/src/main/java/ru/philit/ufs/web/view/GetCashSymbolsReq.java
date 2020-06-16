package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.OperationTypeController#getCashSymbols}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetCashSymbolsReq extends BaseRequest {

  /**
   * Код кассового символа.
   */
  private String code;

}
