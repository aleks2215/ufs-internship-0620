package ru.philit.ufs.web.provider;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.UserCache;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.SessionUser;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;
import ru.philit.ufs.util.UuidUtils;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с пользователями.
 */
@Service
public class UserProvider {

  private final UserCache cache;
  private final MockCache mockCache;

  @Autowired
  public UserProvider(UserCache cache, MockCache mockCache) {
    this.cache = cache;
    this.mockCache = mockCache;
  }

  /**
   * Создание сеанса пользователя.
   *
   * @param login логин пользователя
   * @param password пароль пользователя
   * @return информация о пользователе и идентификатор сессии
   */
  public SessionUser loginUser(String login, String password) {
    User user = mockCache.getUser(login, password);
    if (user == null) {
      throw new InvalidDataException("Запрашиваемый пользователь не найден в системе");
    }

    String sessionId = UuidUtils.getRandomUuid();
    cache.addUser(sessionId, user);

    SessionUser sessionUser = new SessionUser(user);
    sessionUser.setSessionId(sessionId);
    return sessionUser;
  }

  /**
   * Завершение сеанса пользователя.
   *
   * @param sessionId идентификатор сессии
   * @return результат завершения сеанса
   */
  public boolean logoutUser(String sessionId) {
    return cache.removeUser(sessionId);
  }

  /**
   * Получение данных о пользователе.
   *
   * @param sessionId идентификатор сессии
   * @return информация о пользователе
   */
  public User getUser(String sessionId) {
    return cache.getUser(sessionId);
  }

  /**
   * Получение данных об операторе.
   *
   * @param clientInfo информация о клиенте
   * @return информация об операторе
   */
  public Operator getOperator(ClientInfo clientInfo) {
    User user = getUser(clientInfo.getSessionId());
    Operator operator = cache.getOperator(user.getLogin(), clientInfo);
    if (operator == null) {
      throw new InvalidDataException("Запрашиваемый оператор не найден в системе");
    }
    if (StringUtils.isEmpty(operator.getWorkplaceId())) {
      throw new InvalidDataException("Рабочее место оператора не определено");
    }
    return operator;
  }

  /**
   * Получение данных по рабочему месту и лимитов по операциям.
   *
   * @param clientInfo информация о клиенте
   * @return информация о рабочем месте
   */
  public Workplace getWorkplace(ClientInfo clientInfo) {
    Operator operator = getOperator(clientInfo);
    Workplace workplace = mockCache.getWorkplace(operator.getWorkplaceId());
    if (workplace == null) {
      throw new InvalidDataException("Запрашиваемое рабочее место не найдено в системе");
    }
    if ((workplace.getType() == WorkplaceType.UWP) && !workplace.isCashboxOnBoard()) {
      throw new InvalidDataException("Данное рабочее место не оборудовано кассовым модулем");
    }
    if (!ObjectUtils.nullSafeEquals(workplace.getCurrencyType(), "RUB")) {
      throw new InvalidDataException(
          "Кассовый модуль может быть использован только для операций в рублях");
    }
    if (workplace.getAmount() == null) {
      throw new InvalidDataException("Отсутствует общий остаток по кассе");
    }
    if (!mockCache.checkOverLimit(workplace.getAmount())) {
      throw new InvalidDataException("Превышен лимит общего остатка по кассе");
    }
    return workplace;
  }

  /**
   * Проверка возможности добавления некоторой суммы в кассу.
   *
   * @param amount сумма для добавления
   * @param clientInfo информация о клиенте
   */
  public void checkWorkplaceIncreasedAmount(BigDecimal amount, ClientInfo clientInfo) {
    Operator operator = getOperator(clientInfo);
    Workplace workplace = mockCache.getWorkplace(operator.getWorkplaceId());
    if (workplace == null) {
      throw new InvalidDataException("Запрашиваемое рабочее место не найдено в системе");
    }
    if (workplace.getAmount() == null) {
      throw new InvalidDataException("Отсутствует общий остаток по кассе");
    }
    if (!mockCache.checkOverLimit(amount.add(workplace.getAmount()))) {
      throw new InvalidDataException("Превышен лимит общего остатка по кассе");
    }
  }
}
