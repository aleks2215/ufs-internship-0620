package ru.philit.ufs.model.cache.hazelcast;

import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_20202_BY_WORK_PLACE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_BY_CARD_NUMBER_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_RESIDUES_BY_ID_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.AUDITED_REQUESTS;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.CASH_SYMBOLS_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.COMMISSION_BY_ACCOUNT_OPERATION_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.LEGAL_ENTITY_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.LOGGED_EVENTS;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATION_BY_TASK_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATION_PACKAGE_INFO_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATION_PACKAGE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATION_PACKAGE_RESPONSE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATION_TYPES_BY_ROLES_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATION_TYPE_FAVOURITES_BY_USER_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OPERATOR_BY_USER_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OVNS_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OVN_BY_UID_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.PAY_ORDERS_CARD_INDEX_1_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.PAY_ORDERS_CARD_INDEX_2_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REPRESENTATIVE_BY_CARD_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REPRESENTATIVE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REQUEST_QUEUE;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.RESPONSE_FLAG_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.SEIZURES_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.USER_BY_SESSION_MAP;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.config.property.HazelcastClientBeProperties;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.common.LocalKey;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;

/**
 * Клиент доступа к кешу Hazelcast.
 */
@Service
public class HazelcastBeClient {

  private static final Logger logger = LoggerFactory.getLogger(HazelcastBeClient.class);

  private final HazelcastInstance instance;
  private final int responseTimeout;

  private IQueue<ExternalEntityRequest> requestQueue;
  private IMap<ExternalEntityRequest, String> responseFlagMap;

  @Getter
  private IMap<String, User> userBySessionMap;

  @Getter
  private IMap<Long, Operation> operationByTaskMap;

  @Getter
  private IList<AuditEntity> auditedRequests;
  @Getter
  private IList<LogEntity> loggedEvents;

  @Getter
  private IMap<LocalKey<Serializable>, List<OperationType>> operationTypesByRolesMap;
  @Getter
  private IMap<String, List<OperationTypeFavourite>> operationTypeFavouritesByUserMap;
  @Getter
  private IMap<LocalKey<CashSymbolRequest>, List<CashSymbol>> cashSymbolsMap;

  @Getter
  private IMap<LocalKey<String>, Account> accountByCardNumberMap;
  @Getter
  private IMap<LocalKey<String>, LegalEntity> legalEntityByAccountMap;
  @Getter
  private IMap<LocalKey<String>, AccountResidues> accountResiduesByAccountMap;
  @Getter
  private IMap<LocalKey<String>, List<PaymentOrderCardIndex1>> payOrdersCardIndex1ByAccountMap;
  @Getter
  private IMap<LocalKey<String>, List<PaymentOrderCardIndex2>> payOrdersCardIndex2ByAccountMap;
  @Getter
  private IMap<LocalKey<String>, List<Seizure>> seizuresByAccountMap;

  @Getter
  private IMap<LocalKey<String>, CashDepositAnnouncement> ovnByUidMap;
  @Getter
  private IMap<LocalKey<CashDepositAnnouncementsRequest>, List<CashDepositAnnouncement>> ovnsMap;
  @Getter
  private IMap<LocalKey<AccountOperationRequest>, ExternalEntityContainer<BigDecimal>>
      commissionByAccountOperationMap;
  @Getter
  private IMap<LocalKey<String>, ExternalEntityContainer<String>> account20202ByWorkPlaceMap;

  @Getter
  private IMap<LocalKey<OperationPackageRequest>, OperationPackage> operationPackageInfoMap;
  @Getter
  private IMap<LocalKey<OperationTasksRequest>, List<OperationPackage>> operationPackageMap;
  @Getter
  private IMap<LocalKey<OperationPackage>, OperationPackage> operationPackageResponseMap;

  @Getter
  private IMap<LocalKey<RepresentativeRequest>, List<Representative>> representativeMap;
  @Getter
  private IMap<LocalKey<String>, Representative> representativeByCardNumberMap;

  @Getter
  private IMap<LocalKey<String>, Operator> operatorByUserMap;

  @Autowired
  public HazelcastBeClient(
      HazelcastInstance hazelcastClient, HazelcastClientBeProperties properties
  ) {
    this.instance = hazelcastClient;
    this.responseTimeout = properties.getResponse().getTimeout();
  }

  /**
   * Инициализатор бина.
   */
  @PostConstruct
  public void init() {
    requestQueue = instance.getQueue(REQUEST_QUEUE);
    responseFlagMap = instance.getMap(RESPONSE_FLAG_MAP);

    userBySessionMap = instance.getMap(USER_BY_SESSION_MAP);

    operationByTaskMap = instance.getMap(OPERATION_BY_TASK_MAP);

    auditedRequests = instance.getList(AUDITED_REQUESTS);
    loggedEvents = instance.getList(LOGGED_EVENTS);

    operationTypesByRolesMap = instance.getMap(OPERATION_TYPES_BY_ROLES_MAP);
    operationTypeFavouritesByUserMap = instance.getMap(OPERATION_TYPE_FAVOURITES_BY_USER_MAP);
    cashSymbolsMap = instance.getMap(CASH_SYMBOLS_MAP);

    accountByCardNumberMap = instance.getMap(ACCOUNT_BY_CARD_NUMBER_MAP);
    legalEntityByAccountMap = instance.getMap(LEGAL_ENTITY_BY_ACCOUNT_MAP);
    accountResiduesByAccountMap = instance.getMap(ACCOUNT_RESIDUES_BY_ID_MAP);
    payOrdersCardIndex1ByAccountMap = instance.getMap(PAY_ORDERS_CARD_INDEX_1_BY_ACCOUNT_MAP);
    payOrdersCardIndex2ByAccountMap = instance.getMap(PAY_ORDERS_CARD_INDEX_2_BY_ACCOUNT_MAP);
    seizuresByAccountMap = instance.getMap(SEIZURES_BY_ACCOUNT_MAP);

    ovnByUidMap = instance.getMap(OVN_BY_UID_MAP);
    ovnsMap = instance.getMap(OVNS_MAP);
    commissionByAccountOperationMap = instance.getMap(COMMISSION_BY_ACCOUNT_OPERATION_MAP);
    account20202ByWorkPlaceMap = instance.getMap(ACCOUNT_20202_BY_WORK_PLACE_MAP);

    operationPackageInfoMap = instance.getMap(OPERATION_PACKAGE_INFO_MAP);
    operationPackageMap = instance.getMap(OPERATION_PACKAGE_MAP);
    operationPackageResponseMap = instance.getMap(OPERATION_PACKAGE_RESPONSE_MAP);

    representativeMap = instance.getMap(REPRESENTATIVE_MAP);
    representativeByCardNumberMap = instance.getMap(REPRESENTATIVE_BY_CARD_MAP);

    operatorByUserMap = instance.getMap(OPERATOR_BY_USER_MAP);

    logger.info("{} started", this.getClass().getSimpleName());
  }

  /**
   * Деструктор бина.
   */
  @PreDestroy
  public void destroy() {
    if (instance != null) {
      HazelcastClient.shutdown(instance);
      logger.info("{} is shut down", this.getClass().getSimpleName());
    }
  }

  /**
   * Отправляет запрос данных в Мастер-системы.
   * Ожидает поступления ответа в течение времени ожидания.
   *
   * @param entityType код запроса сущности
   * @param key локальный id запрашиваемой сущности
   * @return true is response is got
   */
  public <T extends Serializable> boolean requestExternalEntity(String entityType,
      LocalKey<T> key) {
    ExternalEntityRequest entityRequest = new ExternalEntityRequest();
    entityRequest.setEntityType(entityType);
    entityRequest.setSessionId(key.getSessionId());
    entityRequest.setRequestData(key.getKey());
    requestQueue.add(entityRequest);

    int responseTimeoutInSeconds = responseTimeout;
    try {
      do {
        String responseUid = responseFlagMap.get(entityRequest);
        if (responseUid != null) {
          return true;
        }
        Thread.sleep(1000);
        responseTimeoutInSeconds--;
      } while (responseTimeoutInSeconds > 0);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return false;
  }
}
