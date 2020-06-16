package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Сетевой код выдачи карты.
 */
public enum CardNetworkCode {

  VISA(0, "Visa"),
  MASTER_CARD(1, "MasterCard"),
  MAESTRO(2, "Maestro"),
  AMERICAN_EXPRESS(3, "American Express");

  private static final ImmutableMap<Integer, CardNetworkCode> CODES_MAP;
  private static final ImmutableMap<String, CardNetworkCode> VALUES_MAP;

  static {
    Map<Integer, CardNetworkCode> mapCodes = new HashMap<>();
    Map<String, CardNetworkCode> mapValues = new HashMap<>();
    for (CardNetworkCode item : values()) {
      mapCodes.put(item.code(), item);
      mapValues.put(item.value(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
    VALUES_MAP = ImmutableMap.copyOf(mapValues);
  }

  public static CardNetworkCode getByCode(Integer code) {
    return CODES_MAP.get(code);
  }

  public static CardNetworkCode getByValue(String value) {
    return VALUES_MAP.get(value);
  }

  private final int code;
  private final String value;

  CardNetworkCode(int code, String value) {
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
