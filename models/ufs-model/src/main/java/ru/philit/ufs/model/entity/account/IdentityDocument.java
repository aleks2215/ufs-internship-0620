package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность документа, удостоверяющего личность.
 */
@EqualsAndHashCode(of = {"type", "series", "number"})
@ToString
@Getter
@Setter
public class IdentityDocument
    implements Serializable, Comparable<IdentityDocument> {

  private IdentityDocumentType type;
  private String series;
  private String number;
  private String issuedBy;
  private Date issuedDate;

  @Override
  public int compareTo(IdentityDocument other) {
    return type.compareTo(other.type);
  }
}
