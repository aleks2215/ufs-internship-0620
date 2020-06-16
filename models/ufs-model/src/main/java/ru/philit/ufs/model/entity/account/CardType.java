package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Тип карты.
 */
public enum CardType {

  DEBIT(0, "Дебетовая"),
  CREDIT(1, "Кредитная");

  private static final ImmutableMap<Integer, CardType> CODES_MAP;
  private static final ImmutableMap<String, CardType> VALUES_MAP;

  static {
    Map<Integer, CardType> mapCodes = new HashMap<>();
    Map<String, CardType> mapValues = new HashMap<>();
    for (CardType item : values()) {
      mapCodes.put(item.code(), item);
      mapValues.put(item.value(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
    VALUES_MAP = ImmutableMap.copyOf(mapValues);
  }

  public static CardType getByCode(Integer code) {
    return CODES_MAP.get(code);
  }

  public static CardType getByValue(String value) {
    return VALUES_MAP.get(value);
  }

  private final int code;
  private final String value;

  CardType(int code, String value) {
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
