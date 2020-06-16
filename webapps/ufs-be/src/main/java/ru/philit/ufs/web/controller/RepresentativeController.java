package ru.philit.ufs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.RepresentativeMapper;
import ru.philit.ufs.web.provider.RepresentativeProvider;
import ru.philit.ufs.web.view.GetRepresentativeReq;
import ru.philit.ufs.web.view.GetRepresentativeResp;

/**
 * Контроллер действий с представителями.
 */
@RestController
@RequestMapping("/representative")
public class RepresentativeController {

  private final RepresentativeProvider provider;
  private final RepresentativeMapper mapper;

  @Autowired
  public RepresentativeController(RepresentativeProvider provider, RepresentativeMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Поиск представителя по номеру карты.
   *
   * @param request номер карты
   * @param clientInfo информация о клиенте
   * @return информация о представителе
   */
  @RequestMapping(value = "/byCardNumber", method = RequestMethod.POST)
  public GetRepresentativeResp getRepresentative(
      @RequestBody GetRepresentativeReq request, ClientInfo clientInfo
  ) {
    Representative representative = provider.getRepresentativeByCardNumber(request.getCardNumber(),
        clientInfo);
    return new GetRepresentativeResp().withSuccess(mapper.asDto(representative));
  }

}
