package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Статусы задач пекета операций.
 */
public enum OperationTaskStatus {

  ACTIVE("Active", "Активна"),
  ON_APPROVAL("OnApproval", "На утверждении"),
  DECLINED("Declined", "Отклонена"),
  APPROVED("Approved", "Утверждена"),
  FORWARDED("Forwarded", "Переадресована"),
  ON_SEC_HAND_APPROVAL("OnSecHandApproval", "На утверждении 2"),
  SEC_HAND_APPROVED("SecHandApproved", "Утверждена 2"),
  SEC_HAND_DECLINED("SecHandDeclined", "Отклонена 2"),
  COMPLETED("Completed", "Выполнена");

  private static final ImmutableMap<String, OperationTaskStatus> CODES_MAP;

  static {
    Map<String, OperationTaskStatus> mapCodes = new HashMap<>();
    for (OperationTaskStatus item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static OperationTaskStatus getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  OperationTaskStatus(String code, String value) {
    this.code = code;
    this.value = value;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("code", code()).add("value", value()).toString();
  }

  public String code() {
    return code;
  }

  public String value() {
    return value;
  }

}
