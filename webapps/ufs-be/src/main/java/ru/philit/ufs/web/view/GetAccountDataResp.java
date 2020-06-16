package ru.philit.ufs.web.view;

import ru.philit.ufs.web.dto.AccountDataDto;
import ru.philit.ufs.web.dto.BaseResponse;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.AccountController#getAccountData}
 */
@SuppressWarnings("serial")
public class GetAccountDataResp extends BaseResponse<AccountDataDto> {
}
