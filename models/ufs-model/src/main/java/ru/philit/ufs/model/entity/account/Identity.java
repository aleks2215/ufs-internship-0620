package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность какого-то идентификатора счёта.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Identity
    implements Serializable {

  private String type;
  private String id;

}
