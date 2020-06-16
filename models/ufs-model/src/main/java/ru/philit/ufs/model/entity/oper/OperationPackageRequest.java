package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект запроса пакета операций по клиенту (условия отбора).
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class OperationPackageRequest
    implements Serializable {

  private String workPlaceUid;
  private String inn;
  private String userLogin;

}
