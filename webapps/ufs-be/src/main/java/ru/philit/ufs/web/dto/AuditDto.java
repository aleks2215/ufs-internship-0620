package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект аудита.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AuditDto implements Serializable {

  /**
   * Логин пользователя.
   */
  private String login;
  /**
   * Тип действия.
   */
  private String type;
  /**
   * Дата и время действия.
   */
  private String date;
  /**
   * Данные до действия.
   */
  private Serializable initialObject;
  /**
   * Данные, переданные перед действием.
   */
  private Serializable requestObject;
  /**
   * Данные, полученные после действия.
   */
  private Serializable responseObject;
  /**
   * Интернет-адрес клиента.
   */
  private String client;
  /**
   * Интернет-адрес сервера.
   */
  private String server;

}
