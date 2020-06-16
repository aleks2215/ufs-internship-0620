package ru.philit.ufs.model.entity.account;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность чековой книжки.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
public class Checkbook
    implements Serializable {

  private String id;
  protected String firstCheckId;
  protected Long checkCount;

}
