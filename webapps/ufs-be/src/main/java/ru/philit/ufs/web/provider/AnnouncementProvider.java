package ru.philit.ufs.web.provider;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.AnnouncementCache;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.OvnStatus;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с ОВН.
 */
@Service
public class AnnouncementProvider {

  private final AnnouncementCache cache;
  private final UserProvider userProvider;

  @Autowired
  public AnnouncementProvider(AnnouncementCache cache, UserProvider userProvider) {
    this.cache = cache;
    this.userProvider = userProvider;
  }

  /**
   * Получение списка ОВН по критериям.
   *
   * @param accountId номер счёта
   * @param status статус ОВН
   * @param clientInfo информация о клиенте
   * @return список ОВН
   */
  public List<CashDepositAnnouncement> getAnnouncements(String accountId, String status,
      ClientInfo clientInfo) {
    CashDepositAnnouncementsRequest request = new CashDepositAnnouncementsRequest();

    request.setAccountId(accountId);
    if (status != null) {
      request.setStatus(OvnStatus.getByCode(status));
    }

    List<CashDepositAnnouncement> announcements = cache.getAnnouncements(request, clientInfo);

    if (CollectionUtils.isEmpty(announcements)) {
      announcements = Collections.emptyList();
    }
    return announcements;
  }

  /**
   * Получение ОВН по номеру.
   *
   * @param announcementId уникальный номер ОВН
   * @param clientInfo информация о клиенте
   * @return ОВН
   */
  public CashDepositAnnouncement getAnnouncement(String announcementId, ClientInfo clientInfo) {
    if (StringUtils.isEmpty(announcementId)) {
      throw new InvalidDataException("Отсутствует запрашиваемый номер ОВН");
    }
    return cache.getAnnouncementById(announcementId, clientInfo);
  }

  /**
   * Расчёт комиссии по критериям.
   *
   * @param accountId номер счёта
   * @param amount сумма операции
   * @param typeCode код типа операции
   * @param clientInfo информация о клиенте
   * @return значение комиссии
   */
  public BigDecimal getCommission(String accountId, String amount, String typeCode,
      ClientInfo clientInfo) {
    if (StringUtils.isEmpty(accountId)) {
      throw new InvalidDataException("Отсутствует запрашиваемый номер счёта");
    }
    if (StringUtils.isEmpty(amount)) {
      throw new InvalidDataException("Отсутствует запрашиваемая сумма операции");
    }
    if (StringUtils.isEmpty(typeCode)) {
      throw new InvalidDataException("Отсутствует запрашиваемый код типа операции");
    }
    AccountOperationRequest operationRequest = new AccountOperationRequest();

    operationRequest.setAccountId(accountId);
    operationRequest.setAmount(new BigDecimal(amount));
    operationRequest.setOperationTypeCode(OperationTypeCode.getByCode(typeCode));

    userProvider.checkWorkplaceIncreasedAmount(operationRequest.getAmount(), clientInfo);

    return cache.getCommission(operationRequest, clientInfo);
  }

  /**
   * Получение номера счета №20202 "Касса кредитных организаций".
   *
   * @param workplaceId уникальный номер УРМ/кассы
   * @param clientInfo информация о клиенте
   * @return номер счета №20202
   */
  public String getAccount20202(String workplaceId, ClientInfo clientInfo) {
    if (StringUtils.isEmpty(workplaceId)) {
      throw new InvalidDataException("Отсутствует запрашиваемый номер УРМ/кассы");
    }
    return cache.getAccount20202(workplaceId, clientInfo);
  }

}
