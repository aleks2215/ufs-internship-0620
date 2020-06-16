package ru.philit.ufs.model.entity.account;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;

/**
 * Коллекции записей расходных операций.
 */
@ToString
@Getter
@Setter
public class PaymentOrders extends ExternalEntity {

  private List<PaymentOrderCardIndex1> cardIndexes1;
  private List<PaymentOrderCardIndex2> cardIndexes2;

}
