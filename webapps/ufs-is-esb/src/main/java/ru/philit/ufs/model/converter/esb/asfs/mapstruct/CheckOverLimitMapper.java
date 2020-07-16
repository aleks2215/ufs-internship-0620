package ru.philit.ufs.model.converter.esb.asfs.mapstruct;

import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.philit.ufs.model.converter.esb.asfs.AsfsAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.oper.CheckOverLimitRequest;
import ru.philit.ufs.model.entity.oper.OverLimitStatus;

@Mapper(componentModel = "spring", imports = {Date.class})
public abstract class CheckOverLimitMapper extends AsfsAdapter {

  /**
   * Возвращает объект запроса перелимита общего остатка.
   */
  @Mappings({
      @Mapping(target = "headerInfo", expression = "java(headerInfo())"),
      @Mapping(target = "srvCheckOverLimitRqMessage.userLogin", source = "userLogin"),
      @Mapping(target = "srvCheckOverLimitRqMessage.tobeIncreased", source = "tobeIncreased"),
      @Mapping(target = "srvCheckOverLimitRqMessage.amount", source = "amount")
  })
  public abstract SrvCheckOverLimitRq requestCheckOverLimit(
      CheckOverLimitRequest checkOverLimitRequestRequest);

  /**
   * Преобразует транспортный объект ответа о перелимите общего остатка во внутреннюю сущность.
   */
  @Mappings({
      @Mapping(target = "requestUid", source = "headerInfo.rqUID"),
      @Mapping(target = "receiveDate", expression = "java(new Date())"),
      @Mapping(target = "data", source = "srvCheckOverLimitRsMessage.status"),
      @Mapping(target = "responseCode", source = "srvCheckOverLimitRsMessage.responseCode")
  })
  public abstract ExternalEntityContainer<Boolean> convert(SrvCheckOverLimitRs response);

  Boolean limitStatus(LimitStatusType limitStatusType) {
    return (limitStatusType != null)
        ? OverLimitStatus.getByName(limitStatusType.value()).value() : null;
  }
}
