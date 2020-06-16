package ru.philit.ufs.esb.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.esb.ReceiveMessageListener;
import ru.philit.ufs.esb.client.EsbClient;
import ru.philit.ufs.model.cache.IsEsbCache;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.converter.esb.eks.AccountAdapter;
import ru.philit.ufs.model.converter.esb.eks.CheckFraudAdapter;
import ru.philit.ufs.model.converter.esb.eks.CommissionAdapter;
import ru.philit.ufs.model.converter.esb.eks.LegalEntityAdapter;
import ru.philit.ufs.model.converter.esb.eks.OperationPackageAdapter;
import ru.philit.ufs.model.converter.esb.eks.PaymentOrderAdapter;
import ru.philit.ufs.model.converter.esb.eks.SeizureAdapter;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.converter.esb.pprb.Account20202Adapter;
import ru.philit.ufs.model.converter.esb.pprb.CashDepositAnnouncementAdapter;
import ru.philit.ufs.model.converter.esb.pprb.CashSymbolAdapter;
import ru.philit.ufs.model.converter.esb.pprb.OperationTypeAdapter;
import ru.philit.ufs.model.converter.esb.pprb.OperatorAdapter;
import ru.philit.ufs.model.converter.esb.pprb.RepresentativeAdapter;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.request.RequestType;

/**
 * Сервис на маршрутизацию сообщений между Hazelcast и КСШ.
 */
@Service("EsbService")
public class EsbServiceImpl
    implements EsbService, ReceiveMessageListener {

  private static final Logger logger = LoggerFactory.getLogger(EsbServiceImpl.class);

  private static final String EKS_CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.eks";
  private static final String PPRB_CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.pprb";

  private final EsbClient esbClient;
  private final IsEsbCache isEsbCache;

  private final JaxbConverter eksConverter = new JaxbConverter(EKS_CONTEXT_PATH);
  private final JaxbConverter pprbConverter = new JaxbConverter(PPRB_CONTEXT_PATH);
  private final List<JaxbConverter> jaxbConverters = ImmutableList.of(
      eksConverter, pprbConverter
  );

  @Autowired
  public EsbServiceImpl(EsbClient esbClient, IsEsbCache isEsbCache) {
    this.esbClient = esbClient;
    this.isEsbCache = isEsbCache;
  }

  @PostConstruct
  public void init() {
    esbClient.addReceiveMessageListener(this);
  }

  /**
   * Отправляет запрос данных к КСШ.
   */
  public void sendRequest(ExternalEntityRequest entityRequest) {
    if (entityRequest == null) {
      logger.error("Sending null ExternalEntityRequest");
      return;
    } else if (entityRequest.getEntityType() == null) {
      logger.error("Sending ExternalEntityRequest with null entityType");
      return;
    }

    try {
      switch (entityRequest.getEntityType()) {
        case RequestType.ACCOUNT_BY_ID:
          if (isRequestDataString(entityRequest)) {
            SrvAccountByIdRq request = AccountAdapter.requestById(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.ACCOUNT_BY_CARD_NUMBER:
          if (isRequestDataString(entityRequest)) {
            SrvAccountByCardNumRq request = AccountAdapter.requestByCardNumber(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.ACCOUNT_RESIDUES_BY_ID:
          if (isRequestDataString(entityRequest)) {
            SrvAccountResiduesByIdRq request = AccountAdapter.requestResiduesById(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.LEGAL_ENTITY_BY_ACCOUNT:
          if (isRequestDataString(entityRequest)) {
            SrvLegalEntityByAccountRq request = LegalEntityAdapter.requestByAccount(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.ACCOUNTS_BY_LEGAL_ENTITY:
          if (isRequestDataString(entityRequest)) {
            SrvLEAccountListRq request = AccountAdapter.requestByLegalEntity(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT:
          if (isRequestDataString(entityRequest)) {
            SrvCardIndexElementsByAccountRq request = PaymentOrderAdapter.requestByAccount(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.SEIZURES_BY_ACCOUNT:
          if (isRequestDataString(entityRequest)) {
            SrvSeizureByAccountRq request = SeizureAdapter.requestByAccount(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.COUNT_COMMISSION:
          if (isAccountOperationRequest(entityRequest)) {
            SrvCountCommissionRq request = CommissionAdapter.requestByParams(
                (AccountOperationRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.CHECK_WITH_FRAUD:
          if (isAccountOperationRequest(entityRequest)) {
            SrvCheckWithFraudRq request = CheckFraudAdapter.requestByParams(
                (AccountOperationRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.CHECK_OPER_PACKAGE:
          if (isOperationPackageRequest(entityRequest)) {
            SrvCheckClOperPkgRq request = OperationPackageAdapter.requestCheckPackage(
                (OperationPackageRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.CREATE_OPER_PACKAGE:
          if (isOperationPackageRequest(entityRequest)) {
            SrvCreateClOperPkgRq request = OperationPackageAdapter.requestCreatePackage(
                (OperationPackageRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.GET_OPER_TASKS:
          if (isOperationTasksRequest(entityRequest)) {
            SrvGetTaskClOperPkgRq request = OperationPackageAdapter.requestTasks(
                (OperationTasksRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.ADD_OPER_TASK:
          if (isOperationPackage(entityRequest)) {
            SrvAddTaskClOperPkgRq request = OperationPackageAdapter.requestAddTasks(
                (OperationPackage) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.UPDATE_OPER_TASK:
          if (isOperationPackage(entityRequest)) {
            SrvUpdTaskClOperPkgRq request = OperationPackageAdapter.requestUpdateTasks(
                (OperationPackage) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(eksConverter.getXml(request));
          }
          break;

        case RequestType.GET_OVN_LIST:
          if (isCashDepositAnnouncementsRequest(entityRequest)) {
            SrvCashDepAnmntListByAccountIdRq request =
                CashDepositAnnouncementAdapter.requestOvnList(
                    (CashDepositAnnouncementsRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.GET_OVN:
          if (isRequestDataString(entityRequest)) {
            SrvGetCashDepAnmntItemRq request = CashDepositAnnouncementAdapter.requestGetOvn(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.CREATE_OVN:
          if (isCashDepositAnnouncement(entityRequest)) {
            SrvCreateCashDepAnmntItemRq request = CashDepositAnnouncementAdapter.requestCreateOvn(
                (CashDepositAnnouncement) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.UPDATE_OVN:
          if (isCashDepositAnnouncement(entityRequest)) {
            SrvUpdCashDepAnmntItemRq request = CashDepositAnnouncementAdapter.requestUpdateOvn(
                (CashDepositAnnouncement) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.ACCOUNT_20202:
          if (isRequestDataString(entityRequest)) {
            SrvGet20202NumRq request = Account20202Adapter.requestByWorkPlace(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.SEARCH_REPRESENTATIVE:
          if (isRepresentativeRequest(entityRequest)) {
            SrvSearchRepRq request = RepresentativeAdapter.requestSearchRepCard(
                (RepresentativeRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.GET_REPRESENTATIVE_BY_CARD:
          if (isRequestDataString(entityRequest)) {
            SrvGetRepByCardRq request = RepresentativeAdapter.requestGetRepByCard(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.OPER_TYPES_BY_ROLE:
          if (isRequestDataListString(entityRequest)) {
            SrvGetUserOperationsByRoleRq request = OperationTypeAdapter.requestByRoles(
                (List<String>) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.OPERATOR_BY_USER:
          if (isRequestDataString(entityRequest)) {
            SrvGetOperatorInfoByUserRq request = OperatorAdapter.requestByUser(
                (String) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        case RequestType.CASH_SYMBOL:
          if (isCashSymbolRequest(entityRequest)) {
            SrvCashSymbolsListRq request = CashSymbolAdapter.requestCashSymbol(
                (CashSymbolRequest) entityRequest.getRequestData());
            isEsbCache.putRequest(request.getHeaderInfo().getRqUID(), entityRequest);
            esbClient.sendMessage(pprbConverter.getXml(request));
          }
          break;

        default:
          logger.error("Sending ExternalEntityRequest with unknown entityType {}",
              entityRequest.getEntityType());
      }
    } catch (JAXBException e) {
      logger.error("Error sending ESB request", e);
    }
  }

  private boolean isRequestDataString(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof String;
  }

  private boolean isRequestDataListString(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof List
        && (((List) entityRequest.getRequestData()).isEmpty()
        || ((List) entityRequest.getRequestData()).get(0) instanceof String);
  }

  private boolean isAccountOperationRequest(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof AccountOperationRequest;
  }

  private boolean isOperationPackageRequest(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof OperationPackageRequest;
  }

  private boolean isOperationPackage(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof OperationPackage;
  }

  private boolean isOperationTasksRequest(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof OperationTasksRequest;
  }

  private boolean isCashDepositAnnouncementsRequest(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof CashDepositAnnouncementsRequest;
  }

  private boolean isCashDepositAnnouncement(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof CashDepositAnnouncement;
  }

  private boolean isRepresentativeRequest(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof RepresentativeRequest;
  }

  private boolean isCashSymbolRequest(ExternalEntityRequest entityRequest) {
    return entityRequest.getRequestData() != null
        && entityRequest.getRequestData() instanceof CashSymbolRequest;
  }

  /**
   * ОБработчик события приходя сообщения из КСШ с целью отправки данных запрашивающему модулю.
   *
   * @param message объект пришедшего из КСШ сообщения, согласно протоколу.
   */
  @Override
  public void receiveMessage(String message) {
    Object messageObject = null;
    for (JaxbConverter jaxbConverter : jaxbConverters) {
      try {
        messageObject = jaxbConverter.getObject(message);
      } catch (JAXBException e) {
        // this message can not be converter by this converter
        logger.trace("this message can not be converter by this converter", e);
      }
      if (messageObject != null) {
        break;
      }
    }
    if (messageObject != null) {
      logger.debug("Received message {}", messageObject);
      try {
        ExternalEntity entity = MultiAdapter.convert(messageObject);
        isEsbCache.pushResponse(entity);
      } catch (Exception e) {
        logger.error("Error on push response to queue", e);
      }
    } else {
      logger.error("Received message of unknown schema");
    }
  }
}
