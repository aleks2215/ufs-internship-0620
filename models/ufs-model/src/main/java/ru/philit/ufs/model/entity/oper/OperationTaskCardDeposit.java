package ru.philit.ufs.model.entity.oper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.Card;

/**
 * Сущность операции Взнос на корпоративную карту.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskCardDeposit extends OperationTaskDeposit {

  private Card card;

  public OperationTaskCardDeposit() {
    this.card = new Card();
  }
}
