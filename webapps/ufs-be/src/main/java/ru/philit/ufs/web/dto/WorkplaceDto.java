package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект рабочего места.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class WorkplaceDto implements Serializable {

  /**
   * Код подразделения, к которому принадлежит рабочее место.
   */
  private String subbranchCode;
  /**
   * Идентификатор кассового модуля в рамках рабочего места.
   */
  private String cashboxDeviceId;
  /**
   * Тип кассового модуля.
   */
  private String cashboxDeviceType;
  /**
   * Валюта счета.
   */
  private String currencyType;
  /**
   * Сумма.
   */
  private String amount;
  /**
   * Лимит суммы на рабочем месте.
   */
  private String limit;
  /**
   * Лимиты для категорий операций.
   */
  private List<OperationTypeLimitDto> categoryLimits;

}
