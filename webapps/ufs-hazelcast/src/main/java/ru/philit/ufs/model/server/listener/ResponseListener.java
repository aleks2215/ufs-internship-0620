package ru.philit.ufs.model.server.listener;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.common.ExternalEntityList2;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.common.LocalKey;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.request.RequestType;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.server.HazelcastServer;

/**
 * Прослушиватель события прихода ответов от Мастер-систем.
 */
@Service
public class ResponseListener
    implements ItemListener<ExternalEntity> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final HazelcastServer hazelcastServer;

  @Autowired
  public ResponseListener(HazelcastServer hazelcastServer) {
    this.hazelcastServer = hazelcastServer;
  }

  @PostConstruct
  public void init() {
    hazelcastServer.getResponseQueue().addItemListener(this, false);
    logger.info("{} started", this.getClass().getSimpleName());
  }

  @Override
  public void itemAdded(ItemEvent<ExternalEntity> itemEvent) {
    logger.debug("Call itemAdded of responseQueue");
    ExternalEntity entity = hazelcastServer.getResponseQueue().poll();
    if (entity == null) {
      logger.debug("  polled response entity null");
      return;
    }
    ExternalEntityRequest request = hazelcastServer.getRequestMap()
        .get(entity.getRequestUid());
    logger.debug("  polled {}", entity);
    if (request == null) {
      logger.error("Received response entity on unknown request. {}", entity);
      return;
    } else if (request.getEntityType() == null || request.getRequestData() == null
        || request.getSessionId() == null) {
      logger.error("Received response entity {} on invalid request {}",
          entity.getClass().getSimpleName(), request);
      return;
    }

    Class elementClass1 = null;
    Class elementClass2 = null;
    if (entity instanceof ExternalEntityList
        && !((ExternalEntityList) entity).getItems().isEmpty()) {
      elementClass1 = ((ExternalEntityList) entity).getItems().get(0).getClass();
    }
    if (entity instanceof ExternalEntityList2
        && !((ExternalEntityList2) entity).getItems2().isEmpty()) {
      elementClass2 = ((ExternalEntityList2) entity).getItems2().get(0).getClass();
    }
    if (entity instanceof ExternalEntityContainer
        && ((ExternalEntityContainer) entity).getData() != null) {
      elementClass1 = ((ExternalEntityContainer) entity).getData().getClass();
    }

    switch (request.getEntityType()) {
      case RequestType.ACCOUNT_BY_ID:
        if (entity instanceof Account) {
          hazelcastServer.getAccountByIdMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (Account) entity);
        }
        break;

      case RequestType.ACCOUNT_BY_CARD_NUMBER:
        if (entity instanceof Account) {
          hazelcastServer.getAccountByCardNumberMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (Account) entity);
        }
        break;

      case RequestType.ACCOUNT_RESIDUES_BY_ID:
        if (entity instanceof AccountResidues) {
          hazelcastServer.getAccountResiduesByIdMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (AccountResidues) entity);
        }
        break;

      case RequestType.ACCOUNTS_BY_LEGAL_ENTITY:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == Account.class)) {
          hazelcastServer.getAccountsByLegalEntityMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              ((ExternalEntityList<Account>) entity).getItems());
          for (Account account : ((ExternalEntityList<Account>) entity).getItems()) {
            hazelcastServer.getAccountByIdMap().put(
                new LocalKey<>(request.getSessionId(), account.getId()), account);
          }
        }
        break;

      case RequestType.LEGAL_ENTITY_BY_ACCOUNT:
        if (entity instanceof LegalEntity) {
          hazelcastServer.getLegalEntityByAccountMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (LegalEntity) entity);
        }
        break;

      case RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT:
        if (entity instanceof ExternalEntityList2) {
          if (elementClass1 == null || elementClass1 == PaymentOrderCardIndex1.class) {
            hazelcastServer.getPayOrdersCardIndex1ByAccountMap().put(
                new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
                ((ExternalEntityList2) entity).getItems());
          }
          if (elementClass2 == null || elementClass2 == PaymentOrderCardIndex2.class) {
            hazelcastServer.getPayOrdersCardIndex2ByAccountMap().put(
                new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
                ((ExternalEntityList2) entity).getItems2());
          }
        }
        break;

      case RequestType.SEIZURES_BY_ACCOUNT:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == Seizure.class)) {
          hazelcastServer.getSeizuresByAccountMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              ((ExternalEntityList<Seizure>) entity).getItems());
        }
        break;

      case RequestType.COUNT_COMMISSION:
        if (entity instanceof ExternalEntityContainer
            && (elementClass1 == null || elementClass1 == BigDecimal.class)) {
          hazelcastServer.getCommissionByAccountOperationMap().put(
              new LocalKey<>(request.getSessionId(),
                  (AccountOperationRequest) request.getRequestData()),
              (ExternalEntityContainer<BigDecimal>) entity);
        }
        break;

      case RequestType.CHECK_WITH_FRAUD:
        if (entity instanceof ExternalEntityContainer
            && (elementClass1 == null || elementClass1 == Boolean.class)) {
          hazelcastServer.getCheckFraudByAccountOperationMap().put(
              new LocalKey<>(request.getSessionId(),
                  (AccountOperationRequest) request.getRequestData()),
              (ExternalEntityContainer<Boolean>) entity);
        }
        break;

      case RequestType.CHECK_OPER_PACKAGE:
      case RequestType.CREATE_OPER_PACKAGE:
        if (entity instanceof OperationPackage) {
          hazelcastServer.getOperationPackageInfoMap().put(
              new LocalKey<>(request.getSessionId(),
                  (OperationPackageRequest) request.getRequestData()),
              (OperationPackage) entity);
        }
        break;

      case RequestType.GET_OPER_TASKS:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == OperationPackage.class)) {
          hazelcastServer.getOperationPackageMap().put(
              new LocalKey<>(request.getSessionId(),
                  (OperationTasksRequest) request.getRequestData()),
              ((ExternalEntityList<OperationPackage>) entity).getItems());
        }
        break;

      case RequestType.ADD_OPER_TASK:
      case RequestType.UPDATE_OPER_TASK:
        if (entity instanceof OperationPackage) {
          hazelcastServer.getOperationPackageResponseMap().put(
              new LocalKey<>(request.getSessionId(),
                  (OperationPackage) request.getRequestData()),
              (OperationPackage) entity);
        }
        break;

      case RequestType.GET_OVN_LIST:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == CashDepositAnnouncement.class)) {
          hazelcastServer.getOvnsMap().put(
              new LocalKey<>(request.getSessionId(),
                  (CashDepositAnnouncementsRequest) request.getRequestData()),
              ((ExternalEntityList<CashDepositAnnouncement>) entity).getItems());
        }
        break;

      case RequestType.GET_OVN:
        if (entity instanceof CashDepositAnnouncement) {
          hazelcastServer.getOvnByUidMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (CashDepositAnnouncement) entity);
        }
        break;

      case RequestType.CREATE_OVN:
      case RequestType.UPDATE_OVN:
        if (entity instanceof CashDepositAnnouncement) {
          hazelcastServer.getOvnResponseMap().put(
              new LocalKey<>(request.getSessionId(),
                  (CashDepositAnnouncement) request.getRequestData()),
              (CashDepositAnnouncement) entity);
        }
        break;

      case RequestType.ACCOUNT_20202:
        if (entity instanceof ExternalEntityContainer
            && (elementClass1 == null || elementClass1 == String.class)) {
          hazelcastServer.getAccount20202ByWorkPlaceMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (ExternalEntityContainer<String>) entity);
        }
        break;

      case RequestType.SEARCH_REPRESENTATIVE:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == Representative.class)) {
          hazelcastServer.getRepresentativeMap().put(
              new LocalKey<>(request.getSessionId(),
                  (RepresentativeRequest) request.getRequestData()),
              ((ExternalEntityList<Representative>) entity).getItems());
        }
        break;

      case RequestType.OPER_TYPES_BY_ROLE:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == OperationType.class)) {
          hazelcastServer.getOperationTypesByRolesMap().put(
              new LocalKey<>(request.getSessionId(), request.getRequestData()),
              ((ExternalEntityList<OperationType>) entity).getItems());
        }
        break;

      case RequestType.GET_REPRESENTATIVE_BY_CARD:
        if (entity instanceof Representative) {
          hazelcastServer.getRepresentativeByCardMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (Representative) entity);
        }
        break;

      case RequestType.OPERATOR_BY_USER:
        if (entity instanceof Operator) {
          hazelcastServer.getOperatorByUserMap().put(
              new LocalKey<>(request.getSessionId(), (String) request.getRequestData()),
              (Operator) entity);
        }
        break;

      case RequestType.CASH_SYMBOL:
        if (entity instanceof ExternalEntityList
            && (elementClass1 == null || elementClass1 == CashSymbol.class)) {
          hazelcastServer.getCashSymbolsMap().put(
              new LocalKey<>(request.getSessionId(), (CashSymbolRequest) request.getRequestData()),
              ((ExternalEntityList<CashSymbol>) entity).getItems());
        }
        break;

      default:
        logger.error("Received response on unknown request type {}",
            request.getEntityType());
    }

    hazelcastServer.getResponseFlagMap().put(request, entity.getRequestUid());
  }

  @Override
  public void itemRemoved(ItemEvent<ExternalEntity> itemEvent) {
    // This event is not required to handling
  }
}
