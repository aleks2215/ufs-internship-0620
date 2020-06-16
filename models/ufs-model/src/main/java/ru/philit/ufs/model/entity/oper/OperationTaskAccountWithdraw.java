package ru.philit.ufs.model.entity.oper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность операции Снятие наличных со счёта.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskAccountWithdraw extends OperationTaskWithdraw {

}
