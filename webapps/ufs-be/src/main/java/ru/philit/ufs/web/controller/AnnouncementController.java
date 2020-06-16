package ru.philit.ufs.web.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.AnnouncementMapper;
import ru.philit.ufs.web.provider.AnnouncementProvider;
import ru.philit.ufs.web.view.GetAccount20202Req;
import ru.philit.ufs.web.view.GetAccount20202Resp;
import ru.philit.ufs.web.view.GetAnnouncementReq;
import ru.philit.ufs.web.view.GetAnnouncementResp;
import ru.philit.ufs.web.view.GetAnnouncementsReq;
import ru.philit.ufs.web.view.GetAnnouncementsResp;
import ru.philit.ufs.web.view.GetCommissionReq;
import ru.philit.ufs.web.view.GetCommissionResp;

/**
 * Контроллер действий с объявлениями на взнос наличных.
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

  private final AnnouncementProvider provider;
  private final AnnouncementMapper mapper;

  @Autowired
  public AnnouncementController(AnnouncementProvider provider, AnnouncementMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Получение списка ОВН по номеру счета.
   *
   * @param request данные о номере счёта и, возможно, статусе объявлений
   * @param clientInfo информация о клиенте
   * @return список ОВН
   */
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public GetAnnouncementsResp getAnnouncements(
      @RequestBody GetAnnouncementsReq request, ClientInfo clientInfo
  ) {
    List<CashDepositAnnouncement> announcements = provider.getAnnouncements(
        request.getAccountId(), request.getStatus(), clientInfo
    );
    return new GetAnnouncementsResp().withSuccess(mapper.asAnnouncementDto(announcements));
  }

  /**
   * Получение экземпляра предоформленного ОВН по уникальному идентификатору ОВН.
   *
   * @param request данные об идентификаторе ОВН
   * @param clientInfo информация о клиенте
   * @return экземпляр предоформленного ОВН
   */
  @RequestMapping(value = "/byId", method = RequestMethod.POST)
  public GetAnnouncementResp getAnnouncementById(
      @RequestBody GetAnnouncementReq request, ClientInfo clientInfo
  ) {
    CashDepositAnnouncement announcement = provider.getAnnouncement(
        request.getAnnouncementId(), clientInfo
    );
    return new GetAnnouncementResp().withSuccess(mapper.asDto(announcement));
  }

  /**
   * Получение информации о комиссии суммы для типа операции.
   *
   * @param request данные о номере счёта, сумме и типе операции
   * @param clientInfo информация о клиенте
   * @return сумма комиссии
   */
  @RequestMapping(value = "/commission", method = RequestMethod.POST)
  public GetCommissionResp getCommission(
      @RequestBody GetCommissionReq request, ClientInfo clientInfo
  ) {
    BigDecimal commission = provider.getCommission(
        request.getAccountId(), request.getAmount(), request.getTypeCode(), clientInfo
    );
    return new GetCommissionResp().withSuccess(mapper.asDto(commission));
  }

  /**
   * Получение номера счета №20202 "Касса кредитных организаций".
   *
   * @param request данные о рабочем месте
   * @param clientInfo информация о клиенте
   * @return номер счета №20202 "Касса кредитных организаций"
   */
  @RequestMapping(value = "/account20202", method = RequestMethod.POST)
  public GetAccount20202Resp getAccount20202(
      @RequestBody GetAccount20202Req request, ClientInfo clientInfo
  ) {
    String number = provider.getAccount20202(request.getWorkplaceId(), clientInfo);
    return new GetAccount20202Resp().withSuccess(number);
  }

}
