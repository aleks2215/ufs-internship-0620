package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект журналирования.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class LogDto implements Serializable {

  /**
   * Логин пользователя.
   */
  private String login;
  /**
   * Сообщение события.
   */
  private String event;
  /**
   * Дата и время события.
   */
  private String date;

}
