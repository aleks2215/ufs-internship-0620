package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект лимита суммы для типа операции.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperationTypeLimitDto implements Serializable {

  /**
   * Код категории операции.
   */
  private String categoryId;
  /**
   * Лимит для категории операции.
   */
  private String limit;

}
