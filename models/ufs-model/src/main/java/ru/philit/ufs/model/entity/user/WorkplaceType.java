package ru.philit.ufs.model.entity.user;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Тип рабочего места.
 */
public enum WorkplaceType {

  CASHBOX(0, "Касса"),
  UWP(1, "УРМ в операционном зале"),
  OTHER(2, "Другое");

  private static final ImmutableMap<Integer, WorkplaceType> CODES_MAP;

  static {
    Map<Integer, WorkplaceType> mapCodes = new HashMap<>();
    for (WorkplaceType item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static WorkplaceType getByCode(Integer code) {
    return CODES_MAP.get(code);
  }

  private final int code;
  private final String value;

  WorkplaceType(int code, String value) {
    this.code = code;
    this.value = value;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("code", code()).add("value", value()).toString();
  }

  public int code() {
    return code;
  }

  public String value() {
    return value;
  }

}
