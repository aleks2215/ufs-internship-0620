package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Статус заявления на выдачу чековых книжек.
 */
public enum CheckbookRequestStatus {

  NEW("New", "Новое"),
  PENDING("Pending", "В ожидании"),
  READY("Ready", "Готово"),
  COMPLETED("Completed", "Выполнено"),
  DELETED("Deleted", "Удалено");

  private static final ImmutableMap<String, CheckbookRequestStatus> CODES_MAP;

  static {
    Map<String, CheckbookRequestStatus> mapCodes = new HashMap<>();
    for (CheckbookRequestStatus item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static CheckbookRequestStatus getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  CheckbookRequestStatus(String code, String value) {
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
