package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект пользователя.
 */
@EqualsAndHashCode(of = {"login", "sessionId"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class UserDto implements Serializable {

  /**
   * Логин пользователя.
   */
  private String login;
  /**
   * Идентификатор сессии.
   */
  private String sessionId;
  /**
   * Идентификатор роли.
   */
  private String roleId;
  /**
   * Идентификатор сотрудника.
   */
  private String employeeId;
  /**
   * Фамилия пользователя.
   */
  private String lastName;
  /**
   * Имя пользователя.
   */
  private String firstName;
  /**
   * Отчество пользователя.
   */
  private String patronymic;
  /**
   * Электронный адрес.
   */
  private String email;
  /**
   * Должность.
   */
  private String position;

}
