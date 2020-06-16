package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект оператора.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperatorDto implements Serializable {

  /**
   * Уникальный номер УРМ/Кассы.
   */
  private String workplaceId;
  /**
   * Подразделение оператора.
   */
  private SubbranchDto subbranch;
  /**
   * Полное имя оператора.
   */
  private String fullName;
  /**
   * Фамилия оператора.
   */
  private String lastName;
  /**
   * Имя оператора.
   */
  private String firstName;
  /**
   * Отчество оператора.
   */
  private String patronymic;
  /**
   * Электронный адрес.
   */
  private String email;
  /**
   * Номер мобильного телефона.
   */
  private String phoneMobile;
  /**
   * Номер рабочего телефона.
   */
  private String phoneWork;

}
