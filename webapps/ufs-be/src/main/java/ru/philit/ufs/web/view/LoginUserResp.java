package ru.philit.ufs.web.view;

import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.UserDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.UserController#loginUser}
 */
@SuppressWarnings("serial")
public class LoginUserResp extends BaseResponse<UserDto> {
}
