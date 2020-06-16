package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект документа, удостоверяющего личность.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class IdentityDocumentDto implements Serializable {

  /**
   * Тип документа.
   */
  private String type;
  /**
   * Серия документа.
   */
  private String series;
  /**
   * Номер документа.
   */
  private String number;
  /**
   * Кем выдан документа.
   */
  private String issuedBy;
  /**
   * Дата выдачи документа.
   */
  private String issuedDate;

}
