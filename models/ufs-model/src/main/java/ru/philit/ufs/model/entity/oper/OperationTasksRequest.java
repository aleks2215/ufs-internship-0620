package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект запроса списка операций по клиенту (условия отбора).
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class OperationTasksRequest
    implements Serializable {

  private Long packageId;
  private List<OperationTask> tasks;
  private OperationTaskStatus taskStatusGlobal;
  private Date fromDate;
  private Date toDate;

  public OperationTasksRequest() {
    this.tasks = new ArrayList<>();
  }
}
