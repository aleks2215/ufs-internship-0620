package ru.philit.ufs.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект подразделения.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class SubbranchDto {

  /**
   * Код ТБ.
   */
  private String tbCode;
  /**
   * Код ГОСБ.
   */
  private String gosbCode;
  /**
   * Код ОСБ.
   */
  private String osbCode;
  /**
   * Код ВСП.
   */
  private String vspCode;
  /**
   * Код подразделения.
   */
  private String code;
  /**
   * Уровень.
   */
  private Long level;
  /**
   * ИНН.
   */
  private String inn;
  /**
   * БИК.
   */
  private String bic;
  /**
   * Наименование банка.
   */
  private String bankName;

}
