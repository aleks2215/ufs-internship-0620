package ru.philit.ufs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.SessionUser;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.web.mapping.UserMapper;
import ru.philit.ufs.web.provider.UserProvider;
import ru.philit.ufs.web.view.GetOperatorResp;
import ru.philit.ufs.web.view.GetWorkplaceResp;
import ru.philit.ufs.web.view.LoginUserReq;
import ru.philit.ufs.web.view.LoginUserResp;
import ru.philit.ufs.web.view.LogoutUserResp;

/**
 * Контроллер действий с пользователем.
 */
@RestController
@RequestMapping("/")
public class UserController {

  private final UserProvider provider;
  private final UserMapper mapper;

  @Autowired
  public UserController(UserProvider provider, UserMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Регистрация пользователя в системе.
   *
   * @param request логин пользователя и пароль
   * @return данные пользователя и идентификатор его сессии
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public LoginUserResp loginUser(@RequestBody LoginUserReq request) {
    SessionUser sessionUser = provider.loginUser(request.getLogin(), request.getPassword());
    return new LoginUserResp().withSuccess(mapper.asDto(sessionUser));
  }

  /**
   * Завершение сеанса пользователя.
   *
   * @param token идентификатор сессии
   * @return результат завершения сеанса
   */
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  public LogoutUserResp logoutUser(@RequestHeader(value = "etoken") String token) {
    return new LogoutUserResp().withSuccess(provider.logoutUser(token));
  }

  /**
   * Получение данных об операторе.
   *
   * @param clientInfo информация о клиенте
   */
  @RequestMapping(value = "/operator", method = RequestMethod.POST)
  public GetOperatorResp getOperator(ClientInfo clientInfo) {
    Operator operator = provider.getOperator(clientInfo);
    return new GetOperatorResp().withSuccess(mapper.asDto(operator));
  }

  /**
   * Получение данных по рабочему месту и лимитов по операциям.
   *
   * @param clientInfo информация о клиенте
   */
  @RequestMapping(value = "/workplace", method = RequestMethod.POST)
  public GetWorkplaceResp getWorkplace(ClientInfo clientInfo) {
    Workplace workplace = provider.getWorkplace(clientInfo);
    return new GetWorkplaceResp().withSuccess(mapper.asDto(workplace));
  }

}
