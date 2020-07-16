package ru.philit.ufs.model.converter.esb.asfs.mapstruct;

import java.math.BigInteger;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.philit.ufs.model.converter.esb.asfs.AsfsAdapter;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;

@Mapper(componentModel = "spring", imports = {Date.class})
public abstract class WorkPlaceMapper extends AsfsAdapter {

  /**
   * Возвращает объект запроса рабочего места по workPlaceUId.
   */
  @Mappings({
      @Mapping(target = "headerInfo", expression = "java(headerInfo())"),
      @Mapping(target = "srvGetWorkPlaceInfoRqMessage.workPlaceUId", source = "workPlaceUId")
  })
  abstract SrvGetWorkPlaceInfoRq requestByWorkPlaceUId(String workPlaceUId);

  /**
   * Преобразует транспортный объект рабочего места во внутреннюю сущность.
   */
  @Mappings({
      @Mapping(target = "requestUid", source = "headerInfo.rqUID"),
      @Mapping(target = "receiveDate", expression = "java(new Date())"),
      @Mapping(target = "type", source = "srvGetWorkPlaceInfoRsMessage.workPlaceType"),
      @Mapping(target = "subbranchCode", source = "srvGetWorkPlaceInfoRsMessage.subbranchCode"),
      @Mapping(target = "cashboxOnBoard", source = "srvGetWorkPlaceInfoRsMessage.cashboxOnBoard"),
      @Mapping(target = "cashboxDeviceId",
          source = "srvGetWorkPlaceInfoRsMessage.cashboxOnBoardDevice"),
      @Mapping(target = "cashboxDeviceType",
          source = "srvGetWorkPlaceInfoRsMessage.cashboxDeviceType"),
      @Mapping(target = "currencyType",
          source = "srvGetWorkPlaceInfoRsMessage.currentCurrencyType"),
      @Mapping(target = "amount", source = "srvGetWorkPlaceInfoRsMessage.amount"),
      @Mapping(target = "limit", source = "srvGetWorkPlaceInfoRsMessage.workPlaceLimit"),
      @Mapping(target = "categoryLimits",
          source = "srvGetWorkPlaceInfoRsMessage"
              + ".workPlaceOperationTypeLimit.operationTypeLimitItem")
  })
  abstract Workplace convert(SrvGetWorkPlaceInfoRs response);

  WorkplaceType workplaceType(BigInteger workplaceTypeId) {
    return (workplaceTypeId != null) ? WorkplaceType.getByCode(workplaceTypeId.intValue()) : null;
  }

  @Mappings({
      @Mapping(target = "categoryId", source = "operationTypeItem.operationCategory"),
      @Mapping(target = "limit", source = "operationTypeItem.operationLimit")
  })
  abstract OperationTypeLimit operationTypeLimit(SrvGetWorkPlaceInfoRsMessage
      .WorkPlaceOperationTypeLimit.OperationTypeLimitItem operationTypeItem);

}
