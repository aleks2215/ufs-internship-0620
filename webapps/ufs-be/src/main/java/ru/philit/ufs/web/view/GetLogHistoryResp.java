package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.LogDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.ServiceController#getLogHistory}
 */
@SuppressWarnings("serial")
public class GetLogHistoryResp extends BaseResponse<List<LogDto>> {
}
