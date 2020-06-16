package ru.philit.ufs.model.cache.hazelcast;

import static java.util.Collections.singletonList;
import static ru.philit.ufs.model.entity.request.RequestType.ACCOUNT_20202;
import static ru.philit.ufs.model.entity.request.RequestType.ACCOUNT_BY_CARD_NUMBER;
import static ru.philit.ufs.model.entity.request.RequestType.ACCOUNT_RESIDUES_BY_ID;
import static ru.philit.ufs.model.entity.request.RequestType.ADD_OPER_TASK;
import static ru.philit.ufs.model.entity.request.RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT;
import static ru.philit.ufs.model.entity.request.RequestType.CASH_SYMBOL;
import static ru.philit.ufs.model.entity.request.RequestType.CHECK_OPER_PACKAGE;
import static ru.philit.ufs.model.entity.request.RequestType.COUNT_COMMISSION;
import static ru.philit.ufs.model.entity.request.RequestType.CREATE_OPER_PACKAGE;
import static ru.philit.ufs.model.entity.request.RequestType.GET_OPER_TASKS;
import static ru.philit.ufs.model.entity.request.RequestType.GET_OVN;
import static ru.philit.ufs.model.entity.request.RequestType.GET_OVN_LIST;
import static ru.philit.ufs.model.entity.request.RequestType.GET_REPRESENTATIVE_BY_CARD;
import static ru.philit.ufs.model.entity.request.RequestType.LEGAL_ENTITY_BY_ACCOUNT;
import static ru.philit.ufs.model.entity.request.RequestType.OPERATOR_BY_USER;
import static ru.philit.ufs.model.entity.request.RequestType.OPER_TYPES_BY_ROLE;
import static ru.philit.ufs.model.entity.request.RequestType.SEARCH_REPRESENTATIVE;
import static ru.philit.ufs.model.entity.request.RequestType.SEIZURES_BY_ACCOUNT;
import static ru.philit.ufs.model.entity.request.RequestType.UPDATE_OPER_TASK;

import com.google.common.collect.Iterables;
import com.hazelcast.core.IMap;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.cache.AccountCache;
import ru.philit.ufs.model.cache.AnnouncementCache;
import ru.philit.ufs.model.cache.OperationCache;
import ru.philit.ufs.model.cache.OperationTypeCache;
import ru.philit.ufs.model.cache.RepresentativeCache;
import ru.philit.ufs.model.cache.UserCache;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
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
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.service.AuditService;
import ru.philit.ufs.web.exception.UserNotFoundException;

/**
 * Модуль данных, обеспечивающий доступ к кешу.
 */
@Service
public class HazelcastCacheImpl
    implements AccountCache, AnnouncementCache, OperationCache, OperationTypeCache,
    RepresentativeCache, UserCache {

  private final HazelcastBeClient client;
  private final AuditService auditService;

  @Autowired
  public HazelcastCacheImpl(HazelcastBeClient client, AuditService auditService) {
    this.client = client;
    this.auditService = auditService;
  }

  @Override
  public List<OperationType> getOperationTypes(ClientInfo clientInfo) {
    Serializable userRoles = (Serializable) singletonList(clientInfo.getUser().getRoleId());
    return requestData(
        userRoles, client.getOperationTypesByRolesMap(), OPER_TYPES_BY_ROLE, clientInfo
    );
  }

  @Override
  public List<CashSymbol> getCashSymbols(CashSymbolRequest request, ClientInfo clientInfo) {
    return requestData(
        request, client.getCashSymbolsMap(), CASH_SYMBOL, clientInfo
    );
  }

  @Override
  public List<OperationTypeFavourite> getOperationTypeFavourites(ClientInfo clientInfo) {
    return client.getOperationTypeFavouritesByUserMap().get(clientInfo.getUser().getLogin());
  }

  @Override
  public void saveOperationTypeFavourites(List<OperationTypeFavourite> favourites,
      List<OperationTypeFavourite> previous, ClientInfo clientInfo) {
    String userLogin = clientInfo.getUser().getLogin();
    client.getOperationTypeFavouritesByUserMap().put(userLogin, favourites);
    auditService.auditRequest(clientInfo, "FAVOURITES_BY_USER_LOGIN", previous, favourites, null);
  }

  @Override
  public Account getAccount(String cardNumber, ClientInfo clientInfo) {
    return requestData(
        cardNumber, client.getAccountByCardNumberMap(), ACCOUNT_BY_CARD_NUMBER, clientInfo
    );
  }

  @Override
  public LegalEntity getLegalEntity(String accountId, ClientInfo clientInfo) {
    return requestData(
        accountId, client.getLegalEntityByAccountMap(), LEGAL_ENTITY_BY_ACCOUNT, clientInfo
    );
  }

  @Override
  public AccountResidues getAccountResidues(String accountId, ClientInfo clientInfo) {
    return requestData(
        accountId, client.getAccountResiduesByAccountMap(),
        ACCOUNT_RESIDUES_BY_ID, clientInfo
    );
  }

  @Override
  public List<PaymentOrderCardIndex1> getCardIndexes1(String accountId, ClientInfo clientInfo) {
    return requestData(
        accountId, client.getPayOrdersCardIndex1ByAccountMap(),
        CARD_INDEX_ELEMENTS_BY_ACCOUNT, clientInfo
    );
  }

  @Override
  public List<PaymentOrderCardIndex2> getCardIndexes2(String accountId, ClientInfo clientInfo) {
    return requestData(
        accountId, client.getPayOrdersCardIndex2ByAccountMap(),
        CARD_INDEX_ELEMENTS_BY_ACCOUNT, clientInfo
    );
  }

  @Override
  public List<Seizure> getSeizures(String accountId, ClientInfo clientInfo) {
    return requestData(
        accountId, client.getSeizuresByAccountMap(), SEIZURES_BY_ACCOUNT, clientInfo
    );
  }

  @Override
  public List<CashDepositAnnouncement> getAnnouncements(CashDepositAnnouncementsRequest request,
      ClientInfo clientInfo) {
    return requestData(request, client.getOvnsMap(), GET_OVN_LIST, clientInfo);
  }

  @Override
  public CashDepositAnnouncement getAnnouncementById(String id, ClientInfo clientInfo) {
    return requestData(id, client.getOvnByUidMap(), GET_OVN, clientInfo);
  }

  @Override
  public BigDecimal getCommission(AccountOperationRequest request, ClientInfo clientInfo) {
    ExternalEntityContainer<BigDecimal> container = requestData(
        request, client.getCommissionByAccountOperationMap(), COUNT_COMMISSION, clientInfo
    );
    return container.getData();
  }

  @Override
  public String getAccount20202(String workplaceId, ClientInfo clientInfo) {
    ExternalEntityContainer<String> container = requestData(
        workplaceId, client.getAccount20202ByWorkPlaceMap(), ACCOUNT_20202, clientInfo
    );
    return container.getData();
  }

  @Override
  public Operation getOperation(Long taskId) {
    return client.getOperationByTaskMap().get(taskId);
  }

  @Override
  public void addOperation(Long taskId, Operation operation) {
    client.getOperationByTaskMap().put(taskId, operation);
  }

  @Override
  public OperationPackage getPackage(OperationPackageRequest request, ClientInfo clientInfo) {
    return requestData(
        request, client.getOperationPackageInfoMap(), CHECK_OPER_PACKAGE, clientInfo
    );
  }

  @Override
  public OperationPackage createPackage(OperationPackageRequest request, ClientInfo clientInfo) {
    return requestDataFromExternal(
        request, client.getOperationPackageInfoMap(), CREATE_OPER_PACKAGE, clientInfo
    );
  }

  @Override
  public OperationPackage getTasksInPackage(OperationTasksRequest request, ClientInfo clientInfo) {
    return getFirst(getTasksInPackages(request, clientInfo));
  }

  @Override
  public OperationPackage addTasksInPackage(OperationPackage request, ClientInfo clientInfo) {
    return requestDataFromExternal(
        request, client.getOperationPackageResponseMap(), ADD_OPER_TASK, clientInfo
    );
  }

  @Override
  public OperationPackage updateTasksInPackage(OperationPackage request, ClientInfo clientInfo) {
    return requestDataFromExternal(
        request, client.getOperationPackageResponseMap(), UPDATE_OPER_TASK, clientInfo
    );
  }

  @Override
  public List<OperationPackage> getTasksInPackages(OperationTasksRequest request,
      ClientInfo clientInfo) {
    return requestDataFromExternal(
        request, client.getOperationPackageMap(), GET_OPER_TASKS, clientInfo
    );
  }

  @Override
  public User getUser(String sessionId) {
    if (!client.getUserBySessionMap().containsKey(sessionId)) {
      throw new UserNotFoundException("Session with id is not found: " + sessionId);
    }
    return client.getUserBySessionMap().get(sessionId);
  }

  @Override
  public Operator getOperator(String userLogin, ClientInfo clientInfo) {
    return requestData(
        userLogin, client.getOperatorByUserMap(), OPERATOR_BY_USER, clientInfo
    );
  }

  @Override
  public void addUser(String sessionId, User user) {
    client.getUserBySessionMap().put(sessionId, user);
  }

  @Override
  public boolean removeUser(String sessionId) {
    return client.getUserBySessionMap().remove(sessionId) != null;
  }

  @Override
  public Representative getRepresentativeByCardNumber(String cardNumber, ClientInfo clientInfo) {
    return requestData(
        cardNumber, client.getRepresentativeByCardNumberMap(), GET_REPRESENTATIVE_BY_CARD,
        clientInfo
    );
  }

  @Override
  public Representative getRepresentativeByCriteria(RepresentativeRequest request,
      ClientInfo clientInfo) {
    return getFirst(getRepresentativesByCriteria(request, clientInfo));
  }

  @Override
  public List<Representative> getRepresentativesByCriteria(RepresentativeRequest request,
      ClientInfo clientInfo) {
    return requestData(
        request, client.getRepresentativeMap(), SEARCH_REPRESENTATIVE, clientInfo
    );
  }

  private <K extends Serializable, V> V requestData(
      K key, IMap<LocalKey<K>, V> cacheMap, String type, ClientInfo clientInfo
  ) {
    LocalKey<K> localKey = new LocalKey<>(clientInfo.getSessionId(), key);
    V value = cacheMap.get(localKey);
    if (value == null) {
      value = requestDataFromExternal(localKey, cacheMap, type, clientInfo);
    }
    return value;
  }

  private <K extends Serializable, V> V requestDataFromExternal(
      K key, IMap<LocalKey<K>, V> cacheMap, String type, ClientInfo clientInfo
  ) {
    LocalKey<K> localKey = new LocalKey<>(clientInfo.getSessionId(), key);
    return requestDataFromExternal(localKey, cacheMap, type, clientInfo);
  }

  private <K extends Serializable, V> V requestDataFromExternal(
      LocalKey<K> localKey, IMap<LocalKey<K>, V> cacheMap, String type, ClientInfo clientInfo
  ) {
    client.requestExternalEntity(type, localKey);
    V changedValue = cacheMap.get(localKey);
    auditService.auditRequest(clientInfo, type, localKey.getKey(), changedValue);
    return changedValue;
  }

  private <T> T getFirst(List<T> list) {
    return (list != null) ? Iterables.getFirst(list, null) : null;
  }
}
