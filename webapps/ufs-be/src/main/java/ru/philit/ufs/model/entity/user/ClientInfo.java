package ru.philit.ufs.model.entity.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность клиента.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class ClientInfo implements Serializable {

  /**
   * Идентификатор сессии.
   */
  private String sessionId;
  /**
   * Пользователь.
   */
  private User user;
  /**
   * Интернет-адрес клиента.
   */
  private String host;

}
