package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность кассового ордера, хранящая атрибуты для Mock BS.
 */
@EqualsAndHashCode(of = {"cashOrderId"})
@ToString
@Getter
@Setter
public class CashOrder {

  private String responseCode;
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
  private String userLogin;

}
