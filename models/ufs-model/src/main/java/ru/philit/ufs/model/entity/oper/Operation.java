package ru.philit.ufs.model.entity.oper;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.OperationTypeCode;

/**
 * Сущность операции.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Operation extends ExternalEntity {

  private String id;
  private OperationStatus status;
  private OperationTypeCode typeCode;
  private String workplaceId;
  private Date createdDate;
  private Date committedDate;
  private String rollbackReason;

}
