package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Статусы пакетов операций.
 */
public enum OperationPackageStatus {

  NEW("New", "Новый"),
  PACKED("Packed", "Отправлен"),
  COMPLETED("Completed", "Выполнен");

  private static final ImmutableMap<String, OperationPackageStatus> CODES_MAP;

  static {
    Map<String, OperationPackageStatus> mapCodes = new HashMap<>();
    for (OperationPackageStatus item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static OperationPackageStatus getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  OperationPackageStatus(String code, String value) {
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