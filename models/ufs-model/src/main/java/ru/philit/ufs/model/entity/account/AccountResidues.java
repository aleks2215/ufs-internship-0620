package ru.philit.ufs.model.entity.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Развёрнутое описание остатков и параметров счёта.
 */
@EqualsAndHashCode(of = {"accountId", "operationalDate", "residuesDate"}, callSuper = false)
@ToString
@Getter
@Setter
public class AccountResidues extends ExternalEntity {

  private String accountId;
  private Date operationalDate;
  private Date residuesDate;
  private boolean active;
  private Date openDate;
  private Date closeDate;
  private String currencyType;
  private String currencyCode;
  private CurrentBalance currentBalance;
  private BigDecimal fixedBalance;
  private BigDecimal expectedBalance;
  private BigDecimal expectedBalanceDif;
  private BigDecimal availOverdraftLimit;
  private BigDecimal overdraftLimit;
  private BigDecimal availDebetLimit;
  private boolean seizureFlag;
  private Long seizureCount;
  private boolean cardIndex1Flag;
  private Long cardIndex1Count;
  private BigDecimal cardIndex1Amount;
  private boolean cardIndex2Flag;
  private Long cardIndex2Count;
  private BigDecimal cardIndex2Amount;
  private BigDecimal targetLoanAmount;
  private BigDecimal loanRepaymentAmount;
  private Agreement additionalAgreement;
  private List<ServiceTariff> serviceTariffs;
  private boolean compProdMemberFlg;

  /**
   * Конструктор остатков и параметров счёта.
   */
  public AccountResidues() {
    this.currentBalance = new CurrentBalance();
    this.additionalAgreement = new Agreement();
    this.serviceTariffs = new ArrayList<>();
  }
}
