package ru.philit.ufs.web.view;

import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.CreditCardDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.PosController#verify}
 */
@SuppressWarnings("serial")
public class PosVerifyResp extends BaseResponse<CreditCardDto> {
}
