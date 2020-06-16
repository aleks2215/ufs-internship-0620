package ru.philit.ufs.model.entity.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность Юридического лица.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class LegalEntity extends ExternalEntity {

  private String id;
  private String shortName;
  private String fullName;
  private String inn;
  private String ogrn;
  private String kpp;
  private String legalAddress;
  private String factAddress;

}
