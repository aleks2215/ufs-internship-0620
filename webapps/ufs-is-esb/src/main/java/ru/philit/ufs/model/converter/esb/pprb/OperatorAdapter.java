package ru.philit.ufs.model.converter.esb.pprb;

import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRq.SrvGetOperatorInfoByUserRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage.OperatorInfoItem.SubbranchInfoItem;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Преобразователь между сущностью Operator и соответствующим транспортным объектом.
 */
public class OperatorAdapter extends PprbAdapter {

  private static void map(SrvGetOperatorInfoByUserRsMessage.OperatorInfoItem operatorInfoItem,
      Operator operator) {
    operator.setWorkplaceId(operatorInfoItem.getWorkPlaceUId());
    operator.setOperatorFullName(operatorInfoItem.getOperatorFullName());
    map(operatorInfoItem.getSubbranchInfoItem(), operator);
    operator.setFirstName(operatorInfoItem.getFirstName());
    operator.setLastName(operatorInfoItem.getLastName());
    operator.setPatronymic(operatorInfoItem.getPatronymic());
    operator.setEmail(operatorInfoItem.getEmail());
    operator.setPhoneNumMobile(operatorInfoItem.getPhoneNumMobile());
    operator.setPhoneNumWork(operatorInfoItem.getPhoneNumWork());
  }

  private static void map(SubbranchInfoItem subbranchInfoItem, Operator operator) {
    operator.setSubbranch(new Subbranch());
    operator.getSubbranch().setTbCode(subbranchInfoItem.getTBCode());
    operator.getSubbranch().setGosbCode(subbranchInfoItem.getGOSBCode());
    operator.getSubbranch().setOsbCode(subbranchInfoItem.getOSBCode());
    operator.getSubbranch().setVspCode(subbranchInfoItem.getVSPCode());
    operator.getSubbranch().setSubbranchCode(subbranchInfoItem.getSubbranchCode());
    operator.getSubbranch().setLevel(longValue(subbranchInfoItem.getSubbranchLevel()));
    operator.getSubbranch().setInn(subbranchInfoItem.getINN());
    operator.getSubbranch().setBic(subbranchInfoItem.getBIC());
    operator.getSubbranch().setBankName(subbranchInfoItem.getBankName());
  }

  /**
   * Преобразует транспортный объект Оператор во внутреннюю сущность Operator.
   */
  public static Operator convert(SrvGetOperatorInfoByUserRs response) {
    Operator operator = new Operator();
    map(response.getHeaderInfo(), operator);
    map(response.getSrvGetOperatorInfoByUserRsMessage().getOperatorInfoItem(), operator);
    return operator;
  }

  /**
   * Возвращает объект запроса Оператор.
   */
  public static SrvGetOperatorInfoByUserRq requestByUser(String userLogin) {
    SrvGetOperatorInfoByUserRq request = new SrvGetOperatorInfoByUserRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetOperatorInfoByUserRqMessage(new SrvGetOperatorInfoByUserRqMessage());
    request.getSrvGetOperatorInfoByUserRqMessage().setUserLogin(userLogin);
    return request;
  }
}
