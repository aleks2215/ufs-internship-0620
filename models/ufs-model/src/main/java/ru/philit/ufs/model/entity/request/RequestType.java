package ru.philit.ufs.model.entity.request;

/**
 * Тип запросов сущностей из Мастер-систем.
 */
public class RequestType {

  public static final String ACCOUNT_BY_ID = "ACCOUNT_BY_ID";
  public static final String ACCOUNT_BY_CARD_NUMBER = "ACCOUNT_BY_CARD_NUMBER";
  public static final String ACCOUNT_RESIDUES_BY_ID = "ACCOUNT_RESIDUES_BY_ID";
  public static final String LEGAL_ENTITY_BY_ACCOUNT = "LEGAL_ENTITY_BY_ACCOUNT";
  public static final String ACCOUNTS_BY_LEGAL_ENTITY = "ACCOUNTS_BY_LEGAL_ENTITY";
  public static final String CARD_INDEX_ELEMENTS_BY_ACCOUNT = "CARD_INDEX_ELEMENTS_BY_ACCOUNT";
  public static final String COUNT_COMMISSION = "COUNT_COMMISSION";
  public static final String CHECK_WITH_FRAUD = "CHECK_WITH_FRAUD";
  public static final String SEIZURES_BY_ACCOUNT = "SEIZURE_BY_ACCOUNT";
  public static final String CHECK_OPER_PACKAGE = "CHECK_OPER_PACKAGE";
  public static final String CREATE_OPER_PACKAGE = "CREATE_OPER_PACKAGE";
  public static final String GET_OPER_TASKS = "GET_OPER_TASKS";
  public static final String ADD_OPER_TASK = "ADD_OPER_TASK";
  public static final String UPDATE_OPER_TASK = "UPDATE_OPER_TASK";
  public static final String GET_OVN_LIST = "GET_OVN_LIST";
  public static final String GET_OVN = "GET_OVN";
  public static final String CREATE_OVN = "CREATE_OVN";
  public static final String UPDATE_OVN = "UPDATE_OVN";
  public static final String ACCOUNT_20202 = "ACCOUNT_20202";
  public static final String SEARCH_REPRESENTATIVE = "SEARCH_REPRESENTATIVE";
  public static final String GET_REPRESENTATIVE_BY_CARD = "GET_REPRESENTATIVE_BY_CARD";
  public static final String OPER_TYPES_BY_ROLE = "OPER_TYPES_BY_ROLE";
  public static final String OPERATOR_BY_USER = "OPERATOR_BY_USER";
  public static final String CASH_SYMBOL = "CASH_SYMBOL";

  private RequestType() {}

}
