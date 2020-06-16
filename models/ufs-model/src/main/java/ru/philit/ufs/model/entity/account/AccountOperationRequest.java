package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.OperationTypeCode;

/**
 * Объект параметров комиссии (условия отбора).
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class AccountOperationRequest
    implements Serializable {

  private String accountId;
  private BigDecimal amount;
  private OperationTypeCode operationTypeCode;

}
