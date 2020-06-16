package ru.philit.ufs.model.cache;

import java.util.List;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;

/**
 * Интерфейс доступа к кэшу данных для представителей.
 */
public interface RepresentativeCache {

  Representative getRepresentativeByCardNumber(String cardNumber, ClientInfo clientInfo);

  Representative getRepresentativeByCriteria(RepresentativeRequest request, ClientInfo clientInfo);

  List<Representative> getRepresentativesByCriteria(RepresentativeRequest request,
      ClientInfo clientInfo);

}
