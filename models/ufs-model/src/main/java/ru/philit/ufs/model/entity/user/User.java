package ru.philit.ufs.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность пользователя.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login"}, callSuper = false)
@ToString
@Getter
@Setter
public class User extends ExternalEntity {

  private String login;
  private String roleId;
  private String employeeId;
  private String lastName;
  private String firstName;
  private String patronymic;
  private String email;
  private String position;

  /**
   * Конструктор сущности с параметром логина.
   */
  public User(String login) {
    this.login = login;
  }

}
