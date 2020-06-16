package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.UserController#loginUser}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class LoginUserReq extends BaseRequest {

  /**
   * Логин пользователя.
   */
  private String login;
  /**
   * Пароль пользователя.
   */
  private String password;

}
