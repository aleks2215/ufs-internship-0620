package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность договора на обслуживание Счет клиента Банка.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Agreement
    implements Serializable {

  private String id;
  private Date openDate;
  private Date closeDate;

}
