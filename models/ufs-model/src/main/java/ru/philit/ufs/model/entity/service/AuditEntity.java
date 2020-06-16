package ru.philit.ufs.model.entity.service;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность аудита.
 */
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"initialObject", "requestObject", "responseObject"})
@Getter
@Setter
@SuppressWarnings("serial")
public class AuditEntity implements Serializable {

  private String id;
  private String userLogin;
  private String actionType;
  private Date actionDate;
  private Serializable initialObject;
  private Serializable requestObject;
  private Serializable responseObject;
  private String clientHost;
  private String serverHost;

  /**
   * Конструктор сущности с предзаполнением даты действия текущим временем.
   */
  public AuditEntity() {
    actionDate = new Date();
  }

}
