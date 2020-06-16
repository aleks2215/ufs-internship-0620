package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект юридического лица.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class LegalEntityDto implements Serializable {

  /**
   * Идентификатор ЮЛ в мастер-системе.
   */
  private String id;
  /**
   * Сокращенное наименование ЮЛ.
   */
  private String shortName;
  /**
   * Полное наименование ЮЛ.
   */
  private String fullName;
  /**
   * ИНН.
   */
  private String inn;
  /**
   * ОГРН.
   */
  private String ogrn;
  /**
   * КПП.
   */
  private String kpp;
  /**
   * Юридический адрес.
   */
  private String legalAddress;
  /**
   * Фактический адрес.
   */
  private String factAddress;

}
