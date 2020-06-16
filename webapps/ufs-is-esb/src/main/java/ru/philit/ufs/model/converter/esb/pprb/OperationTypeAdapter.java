package ru.philit.ufs.model.converter.esb.pprb;

import java.util.List;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.pprb.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRq.SrvGetUserOperationsByRoleRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs.SrvGetUserOperationsByRoleRsMessage;
import ru.philit.ufs.model.entity.oper.OperationType;

/**
 * Преобразователь между сущностью  OperationType и соответствующим транспортным объектом.
 */
public class OperationTypeAdapter extends PprbAdapter {

  private static OperationTypeCode operationTypeCode(OperTypeLabel operTypeLabel) {
    return (operTypeLabel != null) ? OperationTypeCode.getByCode(operTypeLabel.value()) : null;
  }

  private static void map(SrvGetUserOperationsByRoleRsMessage.OperationTypeItem operationTypeItem,
      OperationType operationType) {
    operationType.setId(operationTypeItem.getOperationTypeId());
    operationType.setCode(operationTypeCode(operationTypeItem.getOperationType()));
    operationType.setName(operationTypeItem.getOperationTypeName());
    operationType.setCategoryId(longValue(operationTypeItem.getOperationCatId()));
    operationType.setCategoryName(operationTypeItem.getOperationCatName());
    operationType.setVisible(operationTypeItem.isVisibleFlg());
    operationType.setEnabled(operationTypeItem.isEnabledFlg());
  }

  /**
   * Возвращает объект запроса типов операций согласно ролям пользователя.
   */
  public static SrvGetUserOperationsByRoleRq requestByRoles(List<String> roles) {
    SrvGetUserOperationsByRoleRq request = new SrvGetUserOperationsByRoleRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetUserOperationsByRoleRqMessage(new SrvGetUserOperationsByRoleRqMessage());
    request.getSrvGetUserOperationsByRoleRqMessage().getRole().addAll(roles);
    return request;
  }

  /**
   * Преобразует транспортный объект списка типов операций во внутреннюю сущность.
   */
  public static ExternalEntityList<OperationType> convert(SrvGetUserOperationsByRoleRs response) {
    ExternalEntityList<OperationType> operationTypeList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), operationTypeList);

    for (SrvGetUserOperationsByRoleRsMessage.OperationTypeItem operationTypeItem :
        response.getSrvGetUserOperationsByRoleRsMessage().getOperationTypeItem()) {
      OperationType operationType = new OperationType();
      map(response.getHeaderInfo(), operationType);
      map(operationTypeItem, operationType);
      operationTypeList.getItems().add(operationType);
    }
    return operationTypeList;
  }
}
