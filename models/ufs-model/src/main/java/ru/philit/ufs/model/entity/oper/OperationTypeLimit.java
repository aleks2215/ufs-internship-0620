package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Ограничение по категории типа операции.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"categoryId"}, callSuper = false)
@ToString
@Getter
@Setter
public class OperationTypeLimit extends ExternalEntity {

  private String categoryId;
  private BigDecimal limit;

}
