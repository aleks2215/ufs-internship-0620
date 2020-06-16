package ru.philit.ufs.model.entity.oper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.Checkbook;
import ru.philit.ufs.model.entity.account.IdentityDocument;

/**
 * Сущность операции Выпуск чековой книжки.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskCheckbookIssuing extends OperationTask {

  private String repFio;
  private String legalEntityShortName;
  private String inn;
  private String accountId;
  private String bankName;
  private String bankBic;
  private String subbranchCompositeCode;
  private IdentityDocument identityDocument;
  private Date dueDate;
  private boolean containsCheckbook;
  private List<Checkbook> checkbooks;
  private String requestId;
  private CheckbookRequestStatus requestStatus;

  public OperationTaskCheckbookIssuing() {
    this.checkbooks = new ArrayList<>();
  }
}
