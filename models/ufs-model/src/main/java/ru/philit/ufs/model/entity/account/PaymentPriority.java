package ru.philit.ufs.model.entity.account;

import com.google.common.base.MoreObjects;

/**
 * Приоритеты очередей оплаты.
 */
public enum PaymentPriority {

  I,
  II,
  III,
  IV,
  V;

  public static PaymentPriority getByCode(String code) {
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