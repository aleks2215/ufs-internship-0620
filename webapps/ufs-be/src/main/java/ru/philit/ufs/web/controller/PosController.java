package ru.philit.ufs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.web.mapping.PosMapper;
import ru.philit.ufs.web.provider.PosProvider;
import ru.philit.ufs.web.view.PosOperationConfirmReq;
import ru.philit.ufs.web.view.PosOperationConfirmResp;
import ru.philit.ufs.web.view.PosVerifyReq;
import ru.philit.ufs.web.view.PosVerifyResp;

/**
 * Контроллер действий POS-терминала.
 */
@RestController
@RequestMapping("/pos")
public class PosController {

  private final PosProvider provider;
  private final PosMapper mapper;

  @Autowired
  public PosController(PosProvider provider, PosMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Верификация карты.
   *
   * @param request номер карты и введённый пин-код
   * @return данные о карте
   */
  @RequestMapping(value = "/verify", method = RequestMethod.POST)
  public PosVerifyResp verify(@RequestBody PosVerifyReq request) {
    Card card = provider.verifyCreditCard(request.getNumber(), request.getPin());
    return new PosVerifyResp().withSuccess(mapper.asDto(card));
  }

  /**
   * Подтверждение операции.
   *
   * @param request номер задачи
   */
  @RequestMapping(value = "/operationConfirm", method = RequestMethod.POST)
  @SuppressWarnings("unused")
  public PosOperationConfirmResp operationConfirm(@RequestBody PosOperationConfirmReq request) {
    return new PosOperationConfirmResp().withSuccess("");
  }

}
