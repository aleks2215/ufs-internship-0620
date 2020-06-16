package ru.philit.ufs.model.entity.oper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность операции Снятие наличных с карты.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskCardWithdraw extends OperationTaskWithdraw {

  private String cardNumber;

}
