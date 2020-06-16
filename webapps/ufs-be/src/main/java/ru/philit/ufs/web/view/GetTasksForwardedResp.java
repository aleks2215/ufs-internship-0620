package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.CardDepositDto;

/**
 * Ответ для операции.
 * {@link ru.philit.ufs.web.controller.OperationController#getTasksForwarded(ClientInfo)}
 */
@SuppressWarnings("serial")
public class GetTasksForwardedResp extends BaseResponse<List<CardDepositDto>> {
}
