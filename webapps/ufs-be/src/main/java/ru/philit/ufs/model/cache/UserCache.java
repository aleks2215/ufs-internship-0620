package ru.philit.ufs.model.cache;

import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;

/**
 * Интерфейс доступа к кешу данных для пользователей.
 */
public interface UserCache {

  User getUser(String sessionId);

  Operator getOperator(String userLogin, ClientInfo clientInfo);

  void addUser(String sessionId, User user);

  boolean removeUser(String sessionId);

}
