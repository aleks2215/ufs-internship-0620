package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;

public enum CashOrderTypeModel {
  KO_1("KO1"),
  KO_2("KO2");

  public static CashOrderTypeModel getByCode(String code) {
    return valueOf(code);
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
