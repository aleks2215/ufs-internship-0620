package ru.philit.ufs.model.cache;

import java.util.List;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Интерфейс доступа к кэшу операций с системой АСФС.
 */
public interface AsfsCache {

  CashOrder createCashOrder(CashOrder request, ClientInfo clientInfo);

  CashOrder updateStatusCashOrder(CashOrder request, ClientInfo clientInfo);

  Workplace getWorkplace(String workplaceId, ClientInfo clientInfo);

  boolean checkOverLimit(CheckOverLimitRequest request, ClientInfo clientInfo);

  void addConfirmedCashOrder(CashOrder cashOrder);

  List<CashOrder> getConfirmedCashOrders();
}
