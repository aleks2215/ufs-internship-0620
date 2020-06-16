package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Объявление на взнос наличными.
 */
@EqualsAndHashCode(of = {"uid"}, callSuper = false)
@ToString(callSuper = true)
@Getter
@Setter
public class CashDepositAnnouncement extends ExternalEntity {

  private String uid;
  private Long num;
  private OvnStatus status;
  private String repFio;
  private String legalEntityShortName;
  private BigDecimal amount;
  private String amountInWords;
  private Integer amountCop;
  private String inn;
  private String kpp;
  private String accountId;
  private String senderAccountId;
  private String cardNumber;
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
  private Date createdDate;
  private Date operationDate;
  private String responseCode;
  protected List<CashSymbol> cashSymbols;
  private String accountantPosition;
  private String accountantFullName;
  private String userPosition;
  private String userFullName;

}
