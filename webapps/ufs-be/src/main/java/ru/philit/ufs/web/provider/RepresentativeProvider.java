package ru.philit.ufs.web.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.RepresentativeCache;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с данными по представителю.
 */
@Service
public class RepresentativeProvider {

  private final RepresentativeCache cache;

  @Autowired
  public RepresentativeProvider(RepresentativeCache cache) {
    this.cache = cache;
  }

  /**
   * Получение информации о представителе.
   *
   * @param cardNumber номер карты
   * @param clientInfo информация о клиенте
   * @return информация о представителе
   */
  public Representative getRepresentativeByCardNumber(String cardNumber, ClientInfo clientInfo) {
    Representative representative = cache.getRepresentativeByCardNumber(cardNumber, clientInfo);
    if (representative == null) {
      throw new InvalidDataException("Представитель по карте не найден");
    }
    return representative;
  }

  /**
   * Получение информации о представителе.
   *
   * @param id идентификатор представителя
   * @param clientInfo информация о клиенте
   * @return информация о представителе
   */
  public Representative getRepresentativeById(String id, ClientInfo clientInfo) {
    return !StringUtils.isEmpty(id)
        ? cache.getRepresentativeByCriteria(new RepresentativeRequest(id), clientInfo)
        : null;
  }
}
