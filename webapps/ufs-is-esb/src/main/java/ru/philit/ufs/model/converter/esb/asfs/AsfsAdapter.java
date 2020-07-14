package ru.philit.ufs.model.converter.esb.asfs;

import java.util.Date;
import java.util.UUID;
import ru.philit.ufs.model.converter.esb.CommonAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.asfs.HeaderInfoType;


/**
 * Базовый класс для конвертеров Сущностей и транспортных объектов Мастер-системы АС ФС.
 */
public class AsfsAdapter extends CommonAdapter {

  public static final String REQUEST_SYSTEM = "ufs";
  public static final String RESPONSE_SYSTEM = "asfs";

  /**
   * Создаёт HeaderInfo для транспортного объекта интеграции с ЕКС.
   */
  public static HeaderInfoType headerInfo() {
    HeaderInfoType headerInfo = new HeaderInfoType();
    headerInfo.setRqUID(UUID.randomUUID().toString());
    headerInfo.setRqTm(xmlCalendar(new Date()));
    headerInfo.setSpName(REQUEST_SYSTEM);
    headerInfo.setSystemId(RESPONSE_SYSTEM);
    return headerInfo;
  }

  protected static void map(HeaderInfoType headerInfo, ExternalEntity entity) {
    entity.setRequestUid(headerInfo.getRqUID());
    entity.setReceiveDate(new Date());
  }
}
