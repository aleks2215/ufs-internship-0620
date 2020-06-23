package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Сущность кассового ордера.
 */
@EqualsAndHashCode(of = {"cashOrderId"}, callSuper = false)
@ToString
@Getter
@Setter
public class CashOrder extends ExternalEntity {

  private String responseCode;
  protected OverLimitStatus status;

  private String responseMsg;
  private String cashOrderId;
  private String cashOrderINum;
  private CashOrderStatus cashOrderStatus;
  private CashOrderTypeModel cashOrderTypeModel;

  private Date createdDttm;
  private String operationId;
  private String repFio;
  private String legalEntityShortName;
  private String inn;

  private String userLogin;
  private boolean tobeIncreased;
  private BigDecimal amount;

  private String accountId;
  private List<CashSymbol> cashSymbols;
  private String senderBank;
  private String senderBankBic;
  private String recipientBank;
  private String recipientBankBic;
  private Boolean clientTypeFk;
  private String organisationNameFk;
  private String operatorPosition;
  private String userFullName;
  private String userPosition;

  private OperationTypeCode operationType;
  private String amountInWords;
  private String currencyType;
  private String workPlaceUId;

  private String repId;
  private String address;
  private Date dateOfBirth;
  private List<IdentityDocument> identityDocuments;
  private String placeOfBirth;
  private boolean resident;

  private Subbranch subbranch;
  private String comment;
  private String account20202Num;
}
