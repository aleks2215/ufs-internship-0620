package ru.philit.ufs.model.entity.common;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Типы операций.
 */
public enum OperationTypeCode {

  TO_CARD_DEPOSIT("ToCardDeposit", "Взнос наличных на счет корпоративной/бюджетной карты"),
  FROM_CARD_WITHDRAW("FromCardWithdraw", "Выдача наличных со счета корпоративной/бюджетной карты"),
  TO_ACCOUNT_DEPOSIT_RUB("ToAccountDepositRub", "Депозит на счёт"),
  FROM_ACCOUNT_WITHDRAW_RUB("FromAccountWithdrawRub", "Снятие со счёта"),
  CHECKBOOK_ISSUING("CheckbookIssuing", "Выпуск чековой книжки");

  private static final ImmutableMap<String, OperationTypeCode> CODES_MAP;

  static {
    Map<String, OperationTypeCode> mapCodes = new HashMap<>();
    for (OperationTypeCode item : values()) {
      mapCodes.put(item.code(), item);
    }
    CODES_MAP = ImmutableMap.copyOf(mapCodes);
  }

  public static OperationTypeCode getByCode(String code) {
    return CODES_MAP.get(code);
  }

  private final String code;
  private final String value;

  OperationTypeCode(String code, String value) {
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
