package ru.philit.ufs.model.entity.user;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность отделения банка.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Subbranch extends ExternalEntity {

  private String id;
  private String tbCode;
  private String gosbCode;
  private String osbCode;
  private String vspCode;
  private String subbranchCode;
  private Long level;
  private String inn;
  private String bic;
  private String bankName;
  private String correspondentAccount;
  private String locationTitle;
  private String locationType;

}
