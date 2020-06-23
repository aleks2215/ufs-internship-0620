package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;

/**
 * Тип статуса перелимита общего остатка.
 */
public enum OverLimitStatus {
  LIMIT_ERROR(false),
  LIMIT_PASSED(true);

  public static OverLimitStatus getByName(String name) {
    return valueOf(name);
  }

  private final Boolean value;

  OverLimitStatus(Boolean value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name()).toString();
  }

  public Boolean value() {
    return value;
  }



}
