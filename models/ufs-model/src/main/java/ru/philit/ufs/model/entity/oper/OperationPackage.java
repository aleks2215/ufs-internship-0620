package ru.philit.ufs.model.entity.oper;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность пакета операций по клиенту.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class OperationPackage extends ExternalEntity {

  private Long id;
  private OperationPackageStatus status;
  private String userLogin;
  private Long responseCode;
  private List<OperationTask> toCardDeposits;
  private List<OperationTask> fromCardWithdraws;
  private List<OperationTask> toAccountDeposits;
  private List<OperationTask> fromAccountWithdraws;
  private List<OperationTask> checkbookIssuing;

  /**
   * Конструктор пакета операций.
   */
  public OperationPackage() {
    this.toCardDeposits = new ArrayList<>();
    this.fromCardWithdraws = new ArrayList<>();
    this.toAccountDeposits = new ArrayList<>();
    this.fromAccountWithdraws = new ArrayList<>();
    this.checkbookIssuing = new ArrayList<>();
  }

  /**
   * Конструктор пакета операций.
   *
   * @param id пакета операций
   */
  public OperationPackage(long id) {
    this();
    this.id = id;
  }
}
