package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект данных о представителе.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class RepresentativeDto implements Serializable {

  /**
   * Идентификатор представителя.
   */
  private String id;
  /**
   * ИНН.
   */
  private String inn;
  /**
   * Полное имя представителя.
   */
  private String fullName;
  /**
   * Фамилия представителя.
   */
  private String lastName;
  /**
   * Имя представителя.
   */
  private String firstName;
  /**
   * Отчество представителя.
   */
  private String patronymic;
  /**
   * Номер рабочего телефона.
   */
  private String phoneWork;
  /**
   * Номер мобильного телефона.
   */
  private String phoneMobile;
  /**
   * Электронный адрес.
   */
  private String email;
  /**
   * Фактический адрес.
   */
  private String address;
  /**
   * Почтовый индекс.
   */
  private String postcode;
  /**
   * Дата рождения.
   */
  private String birthDate;
  /**
   * Место рождения.
   */
  private String birthPlace;
  /**
   * Признак резидента.
   */
  private boolean resident;
  /**
   * Удостоверение личности.
   */
  private IdentityDocumentDto document;

}
