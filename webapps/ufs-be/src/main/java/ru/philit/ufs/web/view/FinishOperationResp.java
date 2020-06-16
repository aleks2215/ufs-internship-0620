package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.OperationDto;

/**
 * Ответ для операций
 * {@link ru.philit.ufs.web.controller.OperationController#confirmOperation}
 * {@link ru.philit.ufs.web.controller.OperationController#cancelOperation}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class FinishOperationResp extends BaseResponse<OperationDto> {
}
