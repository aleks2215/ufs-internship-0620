package ru.philit.ufs.model.converter.esb.pprb;

import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRq.SrvGet20202NumRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs.SrvGet20202NumRsMessage;

/**
 * Перобразователь данных о счёте 20202 с транспортными объектами КСШ.
 */
public class Account20202Adapter extends PprbAdapter {

  private static void map(SrvGet20202NumRsMessage message,
      ExternalEntityContainer<String> container) {
    container.setData(message.getAccount20202Num());
  }

  /**
   * Возвращает объект запроса счёта 20202.
   */
  public static SrvGet20202NumRq requestByWorkPlace(String workPlaceUid) {
    SrvGet20202NumRq request = new SrvGet20202NumRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGet20202NumRqMessage(new SrvGet20202NumRqMessage());
    request.getSrvGet20202NumRqMessage().setWorkPlaceUId(workPlaceUid);
    return request;
  }

  /**
   * Преобразует транспортный объект счета 20202 во внутреннюю сущность.
   */
  public static ExternalEntityContainer<String> convert(SrvGet20202NumRs response) {
    ExternalEntityContainer<String> container = new ExternalEntityContainer<>();
    map(response.getHeaderInfo(), container);
    map(response.getSrvGet20202NumRsMessage(), container);
    return container;
  }
}
