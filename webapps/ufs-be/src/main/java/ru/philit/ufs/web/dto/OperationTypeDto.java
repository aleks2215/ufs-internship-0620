package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект типа операции.
 */
@EqualsAndHashCode(of = {"id", "categoryId"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperationTypeDto implements Serializable {

  /**
   * Идентификатор типа операции.
   */
  private String id;
  /**
   * Наименование типа операции.
   */
  private String name;
  /**
   * Код типа операции.
   */
  private String code;
  /**
   * Идентификатор категории типа операции.
   */
  private String categoryId;
  /**
   * Наименование категории типа операции.
   */
  private String categoryName;
  /**
   * Флаг разрешенности действий для данного типа операций.
   */
  private boolean enabled;

}
