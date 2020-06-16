package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект запроса поиска Представителя корпоративного бизнеса.
 */
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class RepresentativeRequest
    implements Serializable {

  protected String id;
  private String inn;
  protected String firstName;
  protected String lastName;
  protected String patronymic;
  protected String phoneNumWork;
  protected String phoneNumMobile;
  protected String email;
  protected Date birthDate;
  private Sex sex;
  private String placeOfBirth;
  protected IdentityDocument identityDocument;
  private LegalEntity legalEntity;

  public RepresentativeRequest(String id) {
    this.id = id;
  }
}
