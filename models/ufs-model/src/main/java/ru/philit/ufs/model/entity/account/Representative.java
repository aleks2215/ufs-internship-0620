package ru.philit.ufs.model.entity.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Представитель корпоративного бизнеса.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Representative extends ExternalEntity {

  private String id;
  private String inn;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String phoneNumWork;
  private String phoneNumMobile;
  private String email;
  private String address;
  private String postindex;
  private Date birthDate;
  private Sex sex;
  private String placeOfBirth;
  private boolean resident;
  private List<IdentityDocument> identityDocuments;
  private List<LegalEntity> legalEntities;

  public Representative() {
    this.identityDocuments = new ArrayList<>();
    this.legalEntities = new ArrayList<>();
  }
}
