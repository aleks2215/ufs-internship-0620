package ru.philit.ufs.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность оператора.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"workplaceId"}, callSuper = false)
@ToString
@Getter
@Setter
public class Operator extends ExternalEntity {

  private String workplaceId;
  private String operatorFullName;
  private Subbranch subbranch;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String email;
  private String phoneNumMobile;
  private String phoneNumWork;

}
