package ru.philit.ufs.model.server;

import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNTS_BY_LEGAL_ENTITY_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_20202_BY_WORK_PLACE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_BY_CARD_NUMBER_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_BY_ID_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.ACCOUNT_RESIDUES_BY_ID_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.AUDITED_REQUESTS;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.CASH_SYMBOLS_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.CHECK_FRAUD_BY_ACCOUNT_OPERATION_MAP;
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
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.OVN_RESPONSE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.PAY_ORDERS_CARD_INDEX_1_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.PAY_ORDERS_CARD_INDEX_2_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REPRESENTATIVE_BY_CARD_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REPRESENTATIVE_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REQUEST_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REQUEST_QUEUE;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.RESPONSE_FLAG_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.RESPONSE_QUEUE;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.SEIZURES_BY_ACCOUNT_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.USER_BY_SESSION_MAP;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
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
import org.springframework.util.StringUtils;
import ru.philit.ufs.config.property.HazelcastServerProperties;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.common.ExternalEntity;
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
 * Контейнер коллекций распределённого кеша.
 */
@Service
public class HazelcastServer {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final String localAddress;
  private final int localPort;
  private final List<String> membersAddresses;
  private final String groupName;
  private final String groupPassword;

  private HazelcastInstance instance;

  /**
   * Очередь для поступления запросов данных.
   */
  @Getter private IQueue<ExternalEntityRequest> requestQueue;
  /**
   * Очередь для поступления ответов на запросы данных.
   */
  @Getter private IQueue<ExternalEntity> responseQueue;

  /**
   * Коллекция запросов данных.
   */
  @Getter private IMap<String, ExternalEntityRequest> requestMap;
  /**
   * Коллекция регистрации ответов на запросы данных.
   */
  @Getter private IMap<ExternalEntityRequest, String> responseFlagMap;

  /**
   * Коллекция активных сессий пользователей.
   */
  @Getter private IMap<String, User> userBySessionMap;

  /**
   * Коллекция операций задач.
   */
  @Getter private IMap<Long, Operation> operationByTaskMap;

  /**
   * Коллекция запросов аудита.
   */
  @Getter private IList<AuditEntity> auditedRequests;
  /**
   * Коллекция событий лога.
   */
  @Getter private IList<LogEntity> loggedEvents;

  /*
   * Коллекции данных, полученных на запросы из Мастер-систем.
   */

  @Getter private IMap<LocalKey<String>, Account> accountByIdMap;
  @Getter private IMap<LocalKey<String>, Account> accountByCardNumberMap;
  @Getter private IMap<LocalKey<String>, AccountResidues> accountResiduesByIdMap;
  @Getter private IMap<LocalKey<String>, List<Account>> accountsByLegalEntityMap;
  @Getter private IMap<LocalKey<String>, LegalEntity> legalEntityByAccountMap;
  @Getter private IMap<LocalKey<String>, List<Seizure>> seizuresByAccountMap;
  @Getter private IMap<LocalKey<String>, List<PaymentOrderCardIndex1>>
      payOrdersCardIndex1ByAccountMap;
  @Getter private IMap<LocalKey<String>, List<PaymentOrderCardIndex2>>
      payOrdersCardIndex2ByAccountMap;
  @Getter private IMap<LocalKey<AccountOperationRequest>, ExternalEntityContainer<BigDecimal>>
      commissionByAccountOperationMap;
  @Getter private IMap<LocalKey<AccountOperationRequest>, ExternalEntityContainer<Boolean>>
      checkFraudByAccountOperationMap;

  @Getter private IMap<LocalKey<OperationPackageRequest>, OperationPackage> operationPackageInfoMap;
  @Getter private IMap<LocalKey<OperationTasksRequest>, List<OperationPackage>> operationPackageMap;
  @Getter private IMap<LocalKey<OperationPackage>, OperationPackage> operationPackageResponseMap;

  @Getter private IMap<LocalKey<String>, CashDepositAnnouncement> ovnByUidMap;
  @Getter private IMap<LocalKey<CashDepositAnnouncementsRequest>, List<CashDepositAnnouncement>>
      ovnsMap;
  @Getter private IMap<LocalKey<CashDepositAnnouncement>, CashDepositAnnouncement> ovnResponseMap;

  @Getter private IMap<LocalKey<String>, ExternalEntityContainer<String>>
      account20202ByWorkPlaceMap;
  @Getter private IMap<LocalKey<Serializable>, List<OperationType>> operationTypesByRolesMap;
  @Getter private IMap<LocalKey<RepresentativeRequest>, List<Representative>> representativeMap;

  @Getter private IMap<String, List<OperationTypeFavourite>> operationTypeFavouritesByUserMap;

  @Getter private IMap<LocalKey<String>, Representative> representativeByCardMap;

  @Getter private IMap<LocalKey<String>, Operator> operatorByUserMap;

  @Getter private IMap<LocalKey<CashSymbolRequest>, List<CashSymbol>> cashSymbolsMap;

  /**
   * Конструктор бина.
   */
  @Autowired
  public HazelcastServer(HazelcastServerProperties properties) {
    this.localAddress = properties.getInstance().getHost();
    this.localPort = properties.getInstance().getPort();
    this.membersAddresses = properties.getMembersAddresses();
    this.groupName = properties.getGroup().getName();
    this.groupPassword = properties.getGroup().getPassword();
  }

  /**
   * Инициализатор бина.
   */
  @PostConstruct
  public void init() {
    logger.debug("Start up hazelcast cluster member on {}:{}. The other members: {}",
        localAddress, localPort, membersAddresses);
    Config config = new Config();
    if (!StringUtils.isEmpty(localAddress)) {
      config.setProperty("hazelcast.local.localAddress", localAddress);
      config.getNetworkConfig().setPublicAddress(localAddress + ":" + localPort);
    }
    config.getNetworkConfig().setPort(localPort);
    config.getNetworkConfig().setPortAutoIncrement(true);
    config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
    config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
    config.getNetworkConfig().getJoin().getTcpIpConfig().setMembers(membersAddresses);
    config.getNetworkConfig().getInterfaces().setEnabled(false);
    config.getGroupConfig().setName(groupName).setPassword(groupPassword);

    for (String mapName : new String[]{REQUEST_MAP}) {
      MapConfig mapConfig = new MapConfig();
      mapConfig.setName(mapName);
      mapConfig.setTimeToLiveSeconds(86400);
      config.addMapConfig(mapConfig);
    }

    for (String mapName : new String[]{RESPONSE_FLAG_MAP,
        OPERATION_PACKAGE_INFO_MAP, OPERATION_PACKAGE_MAP, OPERATION_PACKAGE_RESPONSE_MAP}) {
      MapConfig mapConfig = new MapConfig();
      mapConfig.setName(mapName);
      mapConfig.setTimeToLiveSeconds(1800);
      config.addMapConfig(mapConfig);
    }

    for (String mapName : new String[]{USER_BY_SESSION_MAP, OPERATION_BY_TASK_MAP}) {
      MapConfig mapConfig = new MapConfig();
      mapConfig.setName(mapName);
      mapConfig.setTimeToLiveSeconds(86400);
      mapConfig.setMaxIdleSeconds(7200);
      config.addMapConfig(mapConfig);
    }

    for (String mapName : new String[]{AUDITED_REQUESTS, LOGGED_EVENTS}) {
      MapConfig mapConfig = new MapConfig();
      mapConfig.setName(mapName);
      config.addMapConfig(mapConfig);
    }

    for (String mapName : new String[]{ACCOUNT_BY_ID_MAP, ACCOUNT_BY_CARD_NUMBER_MAP,
        ACCOUNT_RESIDUES_BY_ID_MAP, ACCOUNTS_BY_LEGAL_ENTITY_MAP, LEGAL_ENTITY_BY_ACCOUNT_MAP,
        SEIZURES_BY_ACCOUNT_MAP,
        PAY_ORDERS_CARD_INDEX_1_BY_ACCOUNT_MAP, PAY_ORDERS_CARD_INDEX_2_BY_ACCOUNT_MAP,
        COMMISSION_BY_ACCOUNT_OPERATION_MAP, CHECK_FRAUD_BY_ACCOUNT_OPERATION_MAP,
        OVN_BY_UID_MAP, OVNS_MAP, ACCOUNT_20202_BY_WORK_PLACE_MAP, OPERATION_TYPES_BY_ROLES_MAP,
        REPRESENTATIVE_MAP, REPRESENTATIVE_BY_CARD_MAP, OPERATOR_BY_USER_MAP, CASH_SYMBOLS_MAP}) {
      MapConfig mapConfig = new MapConfig();
      mapConfig.setName(mapName);
      mapConfig.setTimeToLiveSeconds(3600);
      config.addMapConfig(mapConfig);
    }

    instance = Hazelcast.newHazelcastInstance(config);

    requestQueue = instance.getQueue(REQUEST_QUEUE);
    responseQueue = instance.getQueue(RESPONSE_QUEUE);

    requestMap = instance.getMap(REQUEST_MAP);
    responseFlagMap = instance.getMap(RESPONSE_FLAG_MAP);

    userBySessionMap = instance.getMap(USER_BY_SESSION_MAP);

    operationByTaskMap = instance.getMap(OPERATION_BY_TASK_MAP);

    auditedRequests = instance.getList(AUDITED_REQUESTS);
    loggedEvents = instance.getList(LOGGED_EVENTS);

    accountByIdMap = instance.getMap(ACCOUNT_BY_ID_MAP);
    accountByCardNumberMap = instance.getMap(ACCOUNT_BY_CARD_NUMBER_MAP);
    accountResiduesByIdMap = instance.getMap(ACCOUNT_RESIDUES_BY_ID_MAP);
    accountsByLegalEntityMap = instance.getMap(ACCOUNTS_BY_LEGAL_ENTITY_MAP);
    legalEntityByAccountMap = instance.getMap(LEGAL_ENTITY_BY_ACCOUNT_MAP);
    seizuresByAccountMap = instance.getMap(SEIZURES_BY_ACCOUNT_MAP);
    payOrdersCardIndex1ByAccountMap = instance.getMap(PAY_ORDERS_CARD_INDEX_1_BY_ACCOUNT_MAP);
    payOrdersCardIndex2ByAccountMap = instance.getMap(PAY_ORDERS_CARD_INDEX_2_BY_ACCOUNT_MAP);
    commissionByAccountOperationMap = instance.getMap(COMMISSION_BY_ACCOUNT_OPERATION_MAP);
    checkFraudByAccountOperationMap = instance.getMap(CHECK_FRAUD_BY_ACCOUNT_OPERATION_MAP);
    operationPackageInfoMap = instance.getMap(OPERATION_PACKAGE_INFO_MAP);
    operationPackageMap = instance.getMap(OPERATION_PACKAGE_MAP);
    operationPackageResponseMap = instance.getMap(OPERATION_PACKAGE_RESPONSE_MAP);
    ovnByUidMap = instance.getMap(OVN_BY_UID_MAP);
    ovnsMap = instance.getMap(OVNS_MAP);
    ovnResponseMap = instance.getMap(OVN_RESPONSE_MAP);

    account20202ByWorkPlaceMap = instance.getMap(ACCOUNT_20202_BY_WORK_PLACE_MAP);
    operationTypesByRolesMap = instance.getMap(OPERATION_TYPES_BY_ROLES_MAP);
    operationTypeFavouritesByUserMap = instance.getMap(OPERATION_TYPE_FAVOURITES_BY_USER_MAP);
    representativeMap = instance.getMap(REPRESENTATIVE_MAP);

    representativeByCardMap = instance.getMap(REPRESENTATIVE_BY_CARD_MAP);
    operatorByUserMap = instance.getMap(OPERATOR_BY_USER_MAP);
    cashSymbolsMap = instance.getMap(CASH_SYMBOLS_MAP);

    logger.info("Hazelcast server for {} is started", instance.getName());
  }

  @PreDestroy
  public void destroy() {
    shutdown();
  }

  /**
   * Остановить сервер hazelcast.
   */
  public void shutdown() {
    if (instance != null) {
      instance.shutdown();
      logger.info("Hazelcast instance {} is shut down", instance.getName());
    }
  }
}
