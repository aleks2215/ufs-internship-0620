package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.PaymentPriority;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность записи расходной операции по картотеке 2.
 */
@EqualsAndHashCode(of = {"docId"}, callSuper = false)
@ToString
@Getter
@Setter
public class PaymentOrderCardIndex2 extends ExternalEntity {

  private Long num;
  private String docId;
  private String docType;
  private String recipientShortName;
  private BigDecimal totalAmount;
  private boolean paidPartly;
  private BigDecimal partAmount;
  private Date comeInDate;
  private List<PaidPart> paidParts;
  private PaymentPriority paymentPriority;

  public PaymentOrderCardIndex2() {
    this.paidParts = new ArrayList<>();
  }

  @Getter
  @Setter
  public static class PaidPart implements Serializable {

    private Date paidDate;
    private BigDecimal paidAmount;

  }
}
