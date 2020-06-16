package ru.philit.ufs.model.cache.hazelcast;

/**
 * Справочник констант для именований коллекций в кеше Hazelcast.
 */
public final class CollectionNames {

  public static final String REQUEST_MAP = "requestMap";
  public static final String REQUEST_QUEUE = "requestQueue";

  public static final String RESPONSE_FLAG_MAP = "responseFlagMap";
  public static final String RESPONSE_QUEUE = "responseQueue";

  public static final String USER_BY_SESSION_MAP = "userBySessionMap";

  public static final String OPERATION_BY_TASK_MAP = "operationByTaskMap";

  public static final String AUDITED_REQUESTS = "auditedRequests";
  public static final String LOGGED_EVENTS = "loggedEvents";

  public static final String ACCOUNT_BY_ID_MAP = "accountByIdMap";
  public static final String ACCOUNT_BY_CARD_NUMBER_MAP = "accountByCardNumberMap";
  public static final String ACCOUNT_RESIDUES_BY_ID_MAP = "accountResiduesByIdMap";
  public static final String ACCOUNTS_BY_LEGAL_ENTITY_MAP = "accountsByLegalEntityMap";
  public static final String LEGAL_ENTITY_BY_ACCOUNT_MAP = "legalEntityByAccountMap";
  public static final String PAY_ORDERS_CARD_INDEX_1_BY_ACCOUNT_MAP =
      "payOrdersCardIndex1ByAccountMap";
  public static final String PAY_ORDERS_CARD_INDEX_2_BY_ACCOUNT_MAP =
      "payOrdersCardIndex2ByAccountMap";
  public static final String SEIZURES_BY_ACCOUNT_MAP = "seizuresByAccountMap";
  public static final String COMMISSION_BY_ACCOUNT_OPERATION_MAP =
      "commissionByAccountOperationMap";
  public static final String CHECK_FRAUD_BY_ACCOUNT_OPERATION_MAP =
      "checkFraudByAccountOperationMap";
  public static final String OPERATION_PACKAGE_INFO_MAP = "operationPackageInfoMap";
  public static final String OPERATION_PACKAGE_MAP = "operationPackageMap";
  public static final String OPERATION_PACKAGE_RESPONSE_MAP = "operationPackageResponseMap";
  public static final String OVN_BY_UID_MAP = "ovnByUidMap";
  public static final String OVNS_MAP = "ovnsMap";
  public static final String OVN_RESPONSE_MAP = "ovnResponseMap";
  public static final String ACCOUNT_20202_BY_WORK_PLACE_MAP = "account20202ByWorkPlaceMap";
  public static final String OPERATION_TYPES_BY_ROLES_MAP = "operationTypesByRolesMap";
  public static final String OPERATION_TYPE_FAVOURITES_BY_USER_MAP =
      "operationTypeFavouritesByUserMap";
  public static final String REPRESENTATIVE_MAP = "representativeMap";

  public static final String REPRESENTATIVE_BY_CARD_MAP = "representativeByCardMap";
  public static final String OPERATOR_BY_USER_MAP = "operatorByUserMap";
  public static final String CASH_SYMBOLS_MAP = "cashSymbolsMap";

  private CollectionNames() {
  }

}
