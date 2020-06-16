package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Статусы объявлений на взнос наличными.
 */
public enum OvnStatus {

  NEW("New", "Новый"),
  PENDING("Pending", "В ожидании"),
  COMPLETED("Completed", "Выполнен"),
  DELETED("Deleted", "Удален");

  private static final ImmutableMap<String, OvnStatus> CODES_MAP;
  private static final ImmutableMap<String, OvnStatus> VALUES_MAP;

  static {
    Map<String, OvnStatus> mapCodes = new HashMap<>();
    Map<String, OvnStatus> mapValues = new HashMap<>();
    for (OvnStatus item : values()) {
      mapCodes.put(item.code(), item);
      mapValues.put(item.value(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
    VALUES_MAP = ImmutableMap.copyOf(mapValues);
  }

  public static OvnStatus getByCode(String code) {
    return CODES_MAP.get(code);
  }

  public static OvnStatus getByValue(String value) {
    return VALUES_MAP.get(value);
  }

  private final String code;
  private final String value;

  OvnStatus(String code, String value) {
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
