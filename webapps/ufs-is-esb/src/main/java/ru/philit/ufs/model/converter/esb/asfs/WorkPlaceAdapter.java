package ru.philit.ufs.model.converter.esb.asfs;

import java.math.BigInteger;
import java.util.ArrayList;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRq.SrvGetWorkPlaceInfoRqMessage;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.as_fs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;

/**
 * Преобразователь между сущностью WorkPlace и соответствующим транспортным объектом.
 */
public class WorkPlaceAdapter extends AsfsAdapter {

  //******** Converters ********

  private static WorkplaceType workplaceType(BigInteger workplaceTypeId) {
    return (workplaceTypeId != null) ? WorkplaceType.getByCode(workplaceTypeId.intValue()) : null;
  }

  //******** Mappers *******

  private static void map(SrvGetWorkPlaceInfoRsMessage message, Workplace workplace) {
    workplace.setType(workplaceType(message.getWorkPlaceType()));
    workplace.setCashboxOnBoard(message.isCashboxOnBoard());
    workplace.setSubbranchCode(message.getSubbranchCode());
    workplace.setCashboxDeviceId(message.getCashboxOnBoardDevice());
    workplace.setCashboxDeviceType(message.getCashboxDeviceType());
    workplace.setCurrencyType(message.getCurrentCurrencyType());
    workplace.setAmount(message.getAmount());
    workplace.setLimit(message.getWorkPlaceLimit());
    workplace.setCategoryLimits(new ArrayList<OperationTypeLimit>());
    if (message.getWorkPlaceOperationTypeLimit() != null) {
      for (OperationTypeLimitItem operationTypeLimitItem : message.getWorkPlaceOperationTypeLimit()
          .getOperationTypeLimitItem()) {
        OperationTypeLimit operationTypeLimit = new OperationTypeLimit();
        map(operationTypeLimitItem, operationTypeLimit);
        workplace.getCategoryLimits().add(operationTypeLimit);
      }
    }
  }

  private static void map(
      SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit
          .OperationTypeLimitItem operationTypeItem,
      OperationTypeLimit operationTypeLimit) {
    operationTypeLimit.setCategoryId(operationTypeItem.getOperationCategory().toString());
    operationTypeLimit.setLimit(operationTypeItem.getOperationLimit());
  }

  //******** Methods *******

  /**
   * Возвращает объект запроса рабочего места по workPlaceUId.
   */

  public static SrvGetWorkPlaceInfoRq requestByWorkPlaceUId(String workPlaceUId) {
    SrvGetWorkPlaceInfoRq request = new SrvGetWorkPlaceInfoRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetWorkPlaceInfoRqMessage(new SrvGetWorkPlaceInfoRqMessage());
    request.getSrvGetWorkPlaceInfoRqMessage().setWorkPlaceUId(workPlaceUId);
    return request;
  }

  /**
   * Преобразует транспортный объект рабочего места во внутреннюю сущность.
   */
  public static Workplace convert(SrvGetWorkPlaceInfoRs response) {
    Workplace workplace = new Workplace();
    map(response.getHeaderInfo(), workplace);
    map(response.getSrvGetWorkPlaceInfoRsMessage(), workplace);
    return workplace;
  }
}
