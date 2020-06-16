package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.IdentityDocument;

/**
 * Сущность операции Снятие наличных со счёта/карты.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskWithdraw extends OperationTask {

  private String repFio;
  private String legalEntityShortName;
  private BigDecimal amount;
  private String inn;
  private String accountId;
  private String senderBank;
  private String senderBankBic;
  private String reason;
  private IdentityDocument identityDocument;
  private boolean clientTypeFk;
  private String organisationNameFk;
  private String personalAccountId;
  private String currencyType;
  private String cause;

}
