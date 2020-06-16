package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Статусы операций.
 */
public enum OperationStatus {

  NEW("New", "Новая"),
  PENDING("AdvanceReservation", "Ожидает подтверждения"),
  COMMITTED("Committed", "Подтверждена"),
  CANCELLED("Cancelled", "Отменена");

  private static final ImmutableMap<String, OperationStatus> CODES_MAP;

  static {
    Map<String, OperationStatus> mapCodes = new HashMap<>();
    for (OperationStatus item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static OperationStatus getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  OperationStatus(String code, String value) {
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
