package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Тип счета бухучета.
 */
public enum AccountancyType {

  ACTIVE(0, "Активный"),
  PASSIVE(1, "Пассивный"),
  BOTH(2, "Активно-пассивный");

  private static final ImmutableMap<Integer, AccountancyType> CODES_MAP;

  static {
    Map<Integer, AccountancyType> mapCodes = new HashMap<>();
    for (AccountancyType item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static AccountancyType getByCode(Integer code) {
    return CODES_MAP.get(code);
  }

  private final int code;
  private final String value;

  AccountancyType(int code, String value) {
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
