package ru.philit.ufs.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.dto.AccountDataDto;
import ru.philit.ufs.web.mapping.AccountMapper;
import ru.philit.ufs.web.provider.AccountProvider;
import ru.philit.ufs.web.view.GetAccountDataReq;
import ru.philit.ufs.web.view.GetAccountDataResp;

/**
 * Контроллер действий с данными по карте.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

  private final AccountProvider provider;
  private final AccountMapper mapper;

  @Autowired
  public AccountController(AccountProvider provider, AccountMapper mapper) {
    this.provider = provider;
    this.mapper = mapper;
  }

  /**
   * Получение информации о счёте по номеру карты.
   *
   * @param request данные о номере карты
   * @param clientInfo информация о клиенте
   * @return данные о счёте
   */
  @RequestMapping(value = "/byCardNumber", method = RequestMethod.POST)
  public GetAccountDataResp getAccountData(
      @RequestBody GetAccountDataReq request, ClientInfo clientInfo
  ) {
    Account account = provider.getAccount(request.getCardNumber(), clientInfo);

    String accountId = account.getId();
    LegalEntity legalEntity = provider.getLegalEntity(accountId, clientInfo);
    AccountResidues accountResidues = provider.getAccountResidues(accountId, clientInfo);

    AccountDataDto accountDataDto = new AccountDataDto()
        .withAccount(mapper.asDto(account))
        .withLegalEntity(mapper.asDto(legalEntity))
        .withAccountResidues(mapper.asDto(accountResidues));

    if (accountResidues.isCardIndex1Flag()) {
      List<PaymentOrderCardIndex1> cardIndexes1 = provider.getCardIndexes1(accountId, clientInfo);
      accountDataDto.withCardIndexes1(mapper.asIndex1Dto(cardIndexes1));
    }
    if (accountResidues.isCardIndex2Flag()) {
      List<PaymentOrderCardIndex2> cardIndexes2 = provider.getCardIndexes2(accountId, clientInfo);
      accountDataDto.withCardIndexes2(mapper.asIndex2Dto(cardIndexes2));
    }
    if (accountResidues.isSeizureFlag()) {
      List<Seizure> seizures = provider.getSeizures(accountId, clientInfo);
      accountDataDto.withSeizures(mapper.asSeizureDto(seizures));
    }
    return new GetAccountDataResp().withSuccess(accountDataDto);
  }

}
