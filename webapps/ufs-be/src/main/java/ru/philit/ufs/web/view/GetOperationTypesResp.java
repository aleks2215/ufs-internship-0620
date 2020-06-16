package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.OperationTypeDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.OperationTypeController#getOperationTypes}
 */
@SuppressWarnings("serial")
public class GetOperationTypesResp extends BaseResponse<List<OperationTypeDto>> {
}
