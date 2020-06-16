package ru.philit.ufs.model.entity.oper;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Базовая сущность операций клиента.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class OperationTask extends ExternalEntity {

  protected Long id;
  protected OperationTaskStatus status;
  protected Long responseCode;
  private Date createdDate;
  private Date changedDate;
  private Date statusChangedDate;
  /**
   * Id пакета задач, заполняется при спечиальной выборке даннных.
   */
  private Long packageId;

}
