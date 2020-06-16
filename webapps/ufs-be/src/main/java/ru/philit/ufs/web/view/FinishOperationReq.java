package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операций
 * {@link ru.philit.ufs.web.controller.OperationController#confirmOperation}
 * {@link ru.philit.ufs.web.controller.OperationController#cancelOperation}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class FinishOperationReq extends BaseRequest {

  /**
   * Идентификатор пакета задач.
   */
  private String packageId;
  /**
   * Идентификатор задачи.
   */
  private String taskId;
  /**
   * Уникальный номер УРМ/Кассы.
   */
  private String workplaceId;
  /**
   * Код типа операции.
   */
  private String operationTypeCode;

}
