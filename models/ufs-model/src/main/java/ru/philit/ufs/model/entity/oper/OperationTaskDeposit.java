package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность операции Взнос на счёт/карту.
 */
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class OperationTaskDeposit extends OperationTask {

  private String ovnUid;
  private Long ovnNum;
  private OvnStatus ovnStatus;
  private String representativeId;
  private String repFio;
  private String legalEntityShortName;
  private BigDecimal amount;
  private String inn;
  private String kpp;
  private String accountId;
  private String senderAccountId;
  private String senderBank;
  private String senderBankBic;
  private String recipientAccountId;
  private String recipientBank;
  private String recipientBankBic;
  private String source;
  private boolean clientTypeFk;
  private String organisationNameFk;
  private String personalAccountId;
  private String currencyType;
  private List<CashSymbol> cashSymbols;

  public OperationTaskDeposit() {
    this.cashSymbols = new ArrayList<>();
  }
}
