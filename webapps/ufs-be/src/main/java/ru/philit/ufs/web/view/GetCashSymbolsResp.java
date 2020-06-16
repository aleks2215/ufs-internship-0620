package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.CashSymbolDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.OperationTypeController#getCashSymbols}
 */
@SuppressWarnings("serial")
public class GetCashSymbolsResp extends BaseResponse<List<CashSymbolDto>> {
}
