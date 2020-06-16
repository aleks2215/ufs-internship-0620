package ru.philit.ufs.model.cache;

import java.util.List;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.user.ClientInfo;

/**
 * Интерфейс доступа к кешу данных для счетов.
 */
public interface AccountCache {

  Account getAccount(String cardNumber, ClientInfo clientInfo);

  LegalEntity getLegalEntity(String accountId, ClientInfo clientInfo);

  AccountResidues getAccountResidues(String accountId, ClientInfo clientInfo);

  List<PaymentOrderCardIndex1> getCardIndexes1(String accountId, ClientInfo clientInfo);

  List<PaymentOrderCardIndex2> getCardIndexes2(String accountId, ClientInfo clientInfo);

  List<Seizure> getSeizures(String accountId, ClientInfo clientInfo);

}
