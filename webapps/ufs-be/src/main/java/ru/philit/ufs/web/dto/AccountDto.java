package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект счёта.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AccountDto implements Serializable {

  /**
   * Идентификатор счета.
   */
  private String id;
  /**
   * Статус счета.
   */
  private String status;
  /**
   * Комментарий/основание изменения статуса счета.
   */
  private String changeStatusDesc;
  /**
   * Тип счета.
   */
  private String type;
  /**
   * Тип счета бухучета.
   */
  private String accountancyType;
  /**
   * Валюта счета. Символьный код
   */
  private String currencyType;
  /**
   * Валюта счета. Цифровой код
   */
  private String currencyCode;
  /**
   * Договор на обслуживание счета.
   */
  private AgreementDto agreement;
  /**
   * Информация о банке.
   */
  private SubbranchDto subbranch;
  /**
   * Дата последней операции.
   */
  private String lastTransactionDate;

}
