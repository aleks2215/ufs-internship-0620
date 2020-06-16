package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.esb.eks.PkgStatusType;

/**
 * Сущность пакета операций, хранящая атрибуты для Mock BS.
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
public class OperationPackageInfo
    implements Serializable {

  private Long id;
  private String workPlaceUid;
  private String inn;
  private String userLogin;
  private PkgStatusType status;
  private Date createdDate;

}
