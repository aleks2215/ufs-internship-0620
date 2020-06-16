package ru.philit.ufs.model.entity.service;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность журналирования.
 */
@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class LogEntity implements Serializable {

  private String id;
  private String userLogin;
  private String eventMessage;
  private Date eventDate;

}
