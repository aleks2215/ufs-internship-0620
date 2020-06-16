package ru.philit.ufs.model.entity.account;

import java.math.BigDecimal;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сощность записи об аресте суммы на счёте.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Seizure extends ExternalEntity {

  private Long id;
  private Long type;
  private String reason;
  private Date fromDate;
  private Date toDate;
  private BigDecimal amount;
  private String initiatorShortName;

}
