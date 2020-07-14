package ru.philit.ufs.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashOrderTypeModel;
import ru.philit.ufs.model.entity.oper.OverLimitStatus;

/**
 * Объект записи журнала кассовых ордеров.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class CashOrderJournalDto {

  /**
   * Идентификатор кассового ордера.
   */
  private String cashOrderId;
  /**
   * Номер заявки в ЕФС.
   */
  private String cashOrderINum;
  /**
   * Статус кассового ордера.
   */
  private String cashOrderStatus;
  /**
   * Тип кассового ордера.
   */
  private String cashOrderType;
  /**
   * Сумма кассового ордера.
   */
  private String amount;
  /**
   * Сумма кассового ордера.
   */
  private String createdDttm;
  /**
   * Логин пользователя.
   */
  private String userLogin;

}
