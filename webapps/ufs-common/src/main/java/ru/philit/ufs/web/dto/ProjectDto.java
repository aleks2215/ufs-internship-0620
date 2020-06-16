package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект для передачи на клиентскую часть информации о проекте.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class ProjectDto implements Serializable {

  /**
   * Наименование проекта.
   */
  private String name;
  /**
   * Версия проекта.
   */
  private String version;

}
