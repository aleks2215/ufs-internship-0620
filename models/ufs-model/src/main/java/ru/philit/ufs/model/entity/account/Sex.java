package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;

/**
 * Пол человека.
 */
public enum Sex {

  MALE("Мужской"),
  FEMALE("Женский");

  private final String value;

  Sex(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("value", value()).toString();
  }

  public String value() {
    return value;
  }
}
