package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;
import ru.philit.ufs.web.dto.CardDepositDto;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.OperationController#continueWithUrm}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AddTaskToPackageReq extends BaseRequest {

  /**
   * Уникальный номер УРМ/Кассы.
   */
  private String workplaceId;
  /**
   * Взнос наличных на кредитную карту.
   */
  private CardDepositDto deposit;

}
