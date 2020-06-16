package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность операции Взнос на счёт клиента.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskAccountDeposit extends OperationTaskDeposit {

  private String cause;
  private boolean authCaporMutFund;
  private List<Stockholder> stockholders;

  public OperationTaskAccountDeposit() {
    this.stockholders = new ArrayList<>();
  }

  @Getter
  @Setter
  public static class Stockholder implements Serializable {

    private String name;
    private BigDecimal share;

  }
}
