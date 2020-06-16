package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.AuditDto;
import ru.philit.ufs.web.dto.BaseResponse;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.ServiceController#getAuditHistory}
 */
@SuppressWarnings("serial")
public class GetAuditHistoryResp extends BaseResponse<List<AuditDto>> {
}
