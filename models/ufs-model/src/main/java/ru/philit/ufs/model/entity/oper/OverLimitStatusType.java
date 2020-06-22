package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;

/**
 * Тип статусе перелимита общего остатка.
 */
public enum OverLimitStatusType {
  LIMIT_ERROR,
  LIMIT_PASSED;

  public static OverLimitStatusType getByCode(String code) {
    return valueOf(code);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("code", code()).toString();
  }

  public String code() {
    return name();
  }
}
