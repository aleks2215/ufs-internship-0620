package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public enum CashOrderStatus {
  CREATED("Created", "Создан"),
  COMMITTED("Committed", "Подтвержден");

  private static final ImmutableMap<String, CashOrderStatus> CODES_MAP;

  static {
    Map<String, CashOrderStatus> mapCodes = new HashMap<>();
    for (CashOrderStatus item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static CashOrderStatus getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  CashOrderStatus(String code, String value) {
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
