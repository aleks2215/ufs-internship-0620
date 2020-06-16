package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.OperationJournalDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.ReportController#getOperationJournal}
 */
@SuppressWarnings("serial")
public class GetOperationJournalResp extends BaseResponse<List<OperationJournalDto>> {

}
