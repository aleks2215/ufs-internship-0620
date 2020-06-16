package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.config.property.HazelcastServerProperties;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

/**
 * Встроенный сервер распределённого кеша мокируемых данных.
 */
@Service
public class HazelcastMockServer {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final String localAddress;
  private final int localPort;
  private final List<String> membersAddresses;
  private final String groupName;
  private final String groupPassword;

  private HazelcastInstance instance;

  /*
   * Коллекции кешируемых данных. Содержат объекты operationTask в виде json строки.
   * Ключ коллекции - OperationPackageId, OperationTaskId.
   */
  @Getter private IMap<Long, Map<Long, String>> tasksCardDepositByPackageId;
  @Getter private IMap<Long, Map<Long, String>> tasksCardWithdrawByPackageId;
  @Getter private IMap<Long, Map<Long, String>> tasksAccountDepositByPackageId;
  @Getter private IMap<Long, Map<Long, String>> tasksAccountWithdrawByPackageId;
  @Getter private IMap<Long, Map<Long, String>> tasksCheckbookIssuingByPackageId;

  /**
   * Статусы операций, для быстрого доступа.
   */
  @Getter private IMap<Long, PkgTaskStatusType> taskStatuses;
  /**
   * Данные пакетов операций.
   */
  @Getter private IMap<Long, OperationPackageInfo> packageById;
  /**
   * Пакеты операций по ИНН клиента.
   */
  @Getter private IMap<String, Long> packageIdByInn;

  /**
   * Конструктор бина.
   */
  @Autowired
  public HazelcastMockServer(HazelcastServerProperties properties) {
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
    logger.debug("Start up hazelcast mock cache member on {}:{}. The other members: {}",
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

    instance = Hazelcast.newHazelcastInstance(config);

    tasksCardDepositByPackageId = instance.getMap("tasksCardDepositByPackageId");
    tasksCardWithdrawByPackageId = instance.getMap("tasksCardWithdrawByPackageId");
    tasksAccountDepositByPackageId = instance.getMap("tasksAccountDepositByPackageId");
    tasksAccountWithdrawByPackageId = instance.getMap("tasksAccountWithdrawByPackageId");
    tasksCheckbookIssuingByPackageId = instance.getMap("tasksCheckbookIssuingByPackageId");
    taskStatuses = instance.getMap("taskStatuses");
    packageById = instance.getMap("packageById");
    packageIdByInn = instance.getMap("packageIdByInn");
  }

  /**
   * Деструктор бина. Закрытие кеша.
   */
  @PreDestroy
  public void destroy() {
    if (instance != null) {
      instance.shutdown();
      logger.info("Hazelcast mock cache instance {} is shut down", instance.getName());
    }
  }
}
