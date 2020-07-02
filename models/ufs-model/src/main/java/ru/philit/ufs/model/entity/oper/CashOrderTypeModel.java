package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public enum CashOrderTypeModel {
  KO_1("KO1"),
  KO_2("KO2");

  private static final ImmutableMap<String, CashOrderTypeModel> CODES_MAP;

  static {
    Map<String, CashOrderTypeModel> mapCodes = new HashMap<>();
    for (CashOrderTypeModel item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static CashOrderTypeModel getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;

  CashOrderTypeModel(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("code", code()).toString();
  }

  public String code() {
    return code;
  }
}
