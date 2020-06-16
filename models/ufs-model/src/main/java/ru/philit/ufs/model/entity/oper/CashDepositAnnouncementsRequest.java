package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект параметров комиссии (условия отбора).
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CashDepositAnnouncementsRequest
    implements Serializable {

  private String accountId;
  private OvnStatus status;

}
