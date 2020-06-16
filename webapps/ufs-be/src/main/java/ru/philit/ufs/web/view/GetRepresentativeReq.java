package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции
 * {@link ru.philit.ufs.web.controller.RepresentativeController#getRepresentative}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetRepresentativeReq extends BaseRequest {

  /**
   * Номер карты.
   */
  private String cardNumber;

}
