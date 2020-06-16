package ru.philit.ufs.model.entity.account;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Сущность Счет клиента Банка.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
@Getter
@Setter
public class Account extends ExternalEntity {

  private String id;
  private String status;
  private String changeStatusDescription;
  private AccountType type;
  private AccountancyType accountancyType;
  private String currencyType;
  private String currencyCode;
  private Agreement agreement;
  private Subbranch subbranch;
  private Identity identity;
  private String title;
  private Date lastTransactionDate;

  /**
   * Конструктор Счёта клиента.
   */
  public Account() {
    this.agreement = new Agreement();
    this.subbranch = new Subbranch();
    this.identity = new Identity();
  }
}
