package ru.philit.ufs.model.entity.oper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.OperationTypeCode;

/**
 * Типы операции, доступные пользователю.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class OperationType extends ExternalEntity {

  private String id;
  private OperationTypeCode code;
  private String name;
  private Long categoryId;
  private String categoryName;
  private boolean visible;
  private boolean enabled;

}
