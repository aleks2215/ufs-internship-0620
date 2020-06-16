package ru.philit.ufs.model.entity.oper;

import java.math.BigDecimal;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность записи расходной операции по картотеке 1.
 */
@EqualsAndHashCode(of = {"docId"}, callSuper = false)
@ToString
@Getter
@Setter
public class PaymentOrderCardIndex1 extends ExternalEntity {

  private Long num;
  private String docId;
  private String docType;
  private String recipientShortName;
  private BigDecimal amount;
  private Date comeInDate;

}
