package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект договора на обслуживание счета.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AgreementDto implements Serializable {

  /**
   * Идентификатор договора на обслуживание счета.
   */
  private String id;
  /**
   * Дата заключения договора.
   */
  private String openDate;
  /**
   * Дата окончания обслуживания по договору.
   */
  private String closeDate;

}
