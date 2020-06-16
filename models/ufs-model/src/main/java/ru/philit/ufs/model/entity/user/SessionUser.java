package ru.philit.ufs.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность пользователя в рамках одной сессии.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"sessionId"}, callSuper = false)
@ToString
@Getter
@Setter
public class SessionUser extends User {

  private String sessionId;

  /**
   * Конструктор для копирования данных из пользователя.
   */
  public SessionUser(User user) {
    super(
        user.getLogin(), user.getRoleId(), user.getEmployeeId(), user.getLastName(),
        user.getFirstName(), user.getPatronymic(), user.getEmail(), user.getPosition()
    );
  }
}
