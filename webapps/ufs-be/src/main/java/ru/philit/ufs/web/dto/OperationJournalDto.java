package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект записи журнала операций.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class OperationJournalDto implements Serializable {

  /**
   * Оператор.
   */
  private OperatorDto operator;
  /**
   * Пользователь.
   */
  private UserDto user;
  /**
   * Представитель.
   */
  private RepresentativeDto representative;
  /**
   * Операция.
   */
  private OperationDto operation;
  /**
   * Взнос наличных.
   */
  private CardDepositDto deposit;
  /**
   * Сумма комиссии.
   */
  private String commission;

  /**
   * Добавление информации об операторе.
   */
  public OperationJournalDto withOperator(OperatorDto operator) {
    setOperator(operator);
    return this;
  }

  /**
   * Добавление информации о пользователе.
   */
  public OperationJournalDto withUser(UserDto user) {
    setUser(user);
    return this;
  }

  /**
   * Добавление информации о представителе.
   */
  public OperationJournalDto withRepresentative(RepresentativeDto representative) {
    setRepresentative(representative);
    return this;
  }

  /**
   * Добавление информации об операции.
   */
  public OperationJournalDto withOperation(OperationDto operation) {
    setOperation(operation);
    return this;
  }

  /**
   * Добавление информации о взносе наличных.
   */
  public OperationJournalDto withDeposit(CardDepositDto deposit) {
    setDeposit(deposit);
    return this;
  }

  /**
   * Добавление информации о сумме комиссии.
   */
  public OperationJournalDto withCommission(String commission) {
    setCommission(commission);
    return this;
  }
}
