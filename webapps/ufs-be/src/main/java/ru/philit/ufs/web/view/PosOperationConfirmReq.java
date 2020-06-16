package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.PosController#operationConfirm}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class PosOperationConfirmReq extends BaseRequest {

  /**
   * Идентификатор задачи.
   */
  private String taskId;
  /**
   * Флаг подтверждения.
   */
  private boolean confirm;

}
