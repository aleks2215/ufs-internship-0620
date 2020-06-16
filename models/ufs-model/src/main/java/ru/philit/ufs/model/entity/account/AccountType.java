package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Тип счета.
 */
public enum AccountType {

  PAYMENT("01", "Расчётный"),
  BUDGET("03", "Бюджетный"),
  TRANSIT("04", "Транзитный"),
  LOAN("05", "Ссудный");

  private static final ImmutableMap<String, AccountType> CODES_MAP;

  static {
    Map<String, AccountType> mapCodes = new HashMap<>();
    for (AccountType item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static AccountType getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  AccountType(String code, String value) {
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
