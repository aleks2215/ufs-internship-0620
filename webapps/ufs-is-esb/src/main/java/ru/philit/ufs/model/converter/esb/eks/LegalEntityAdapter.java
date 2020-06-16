package ru.philit.ufs.model.converter.esb.eks;

import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRq.SrvLegalEntityByAccountRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs.SrvLegalEntityByAccountRsMessage;

/**
 * Преобразователь между сущностью LegalEntity и соответствующим транспортным объектом.
 */
public class LegalEntityAdapter extends EksAdapter {

  private static void map(SrvLegalEntityByAccountRsMessage message, LegalEntity legalEntity) {
    legalEntity.setId(message.getLegalEntityId());
    legalEntity.setShortName(message.getLegalEntityShortName());
    legalEntity.setFullName(message.getLegalEntityFullName());
    legalEntity.setInn(message.getINN());
    legalEntity.setOgrn(message.getOGRN());
    legalEntity.setKpp(message.getKPP());
    legalEntity.setLegalAddress(message.getLegalAddress());
    legalEntity.setFactAddress(message.getFactAddress());
  }

  /**
   * Возвращает объект запроса Юр. лица по номеру счёта.
   */
  public static SrvLegalEntityByAccountRq requestByAccount(String accountId) {
    SrvLegalEntityByAccountRq request = new SrvLegalEntityByAccountRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvLegalEntityByAccountRqMessage(new SrvLegalEntityByAccountRqMessage());
    request.getSrvLegalEntityByAccountRqMessage().setAccountId(accountId);
    return request;
  }

  /**
   * Преобразует транспортный объект юр.лица во внутреннюю сущность.
   */
  public static LegalEntity convert(SrvLegalEntityByAccountRs response) {
    LegalEntity legalEntity = new LegalEntity();
    map(response.getHeaderInfo(), legalEntity);
    map(response.getSrvLegalEntityByAccountRsMessage(), legalEntity);
    return legalEntity;
  }
}
