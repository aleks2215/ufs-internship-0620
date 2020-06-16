package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Типы документа удостоверения личности.
 */
public enum IdentityDocumentType {

  PASSPORT("passport", "Паспорт"),
  INTERNAL_PASSPORT("internpassport", "Внутренний паспорт"),
  MILITARY_ID("militaryID", "Военный билет"),
  SEAMEN_ID("seamenId", "Паспорт моряка");

  private static final ImmutableMap<String, IdentityDocumentType> CODES_MAP;

  static {
    Map<String, IdentityDocumentType> mapCodes = new HashMap<>();
    for (IdentityDocumentType item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static IdentityDocumentType getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  IdentityDocumentType(String code, String value) {
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