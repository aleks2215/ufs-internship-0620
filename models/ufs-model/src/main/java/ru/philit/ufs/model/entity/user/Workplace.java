package ru.philit.ufs.model.entity.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;

/**
 * Сущность рабочего места.
 */
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Workplace extends ExternalEntity {

  private String id;
  private WorkplaceType type;
  private boolean cashboxOnBoard;
  private String subbranchCode;
  private String cashboxDeviceId;
  private String cashboxDeviceType;
  private String currencyType;
  private BigDecimal amount;
  private BigDecimal limit;
  private List<OperationTypeLimit> categoryLimits;

  public Workplace() {
    categoryLimits = new ArrayList<>();
  }

}
