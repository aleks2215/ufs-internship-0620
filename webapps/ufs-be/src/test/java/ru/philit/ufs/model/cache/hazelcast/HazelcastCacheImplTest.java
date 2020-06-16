package ru.philit.ufs.model.cache.hazelcast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.hazelcast.core.IMap;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
import ru.philit.ufs.model.entity.request.RequestType;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.service.AuditService;
import ru.philit.ufs.web.exception.UserNotFoundException;

public class HazelcastCacheImplTest {

  private static final String SESSION_ID = "0";
  private static final User USER = new User("login");
  private static final ClientInfo CLIENT_INFO = new ClientInfo(SESSION_ID, USER, "localhost");
  private static final String OPERATION_ID = "123";

  private final IMap<String, User> userBySessionMap = new MockIMap<>();
  private final IMap<Long, Operation> operationByTaskMap = new MockIMap<>();
  private final IMap<LocalKey<Serializable>, List<OperationType>> operationTypesByRolesMap =
      new MockIMap<>();
  private final IMap<String, List<OperationTypeFavourite>> operationTypeFavouritesByUserMap =
      new MockIMap<>();
  private final IMap<LocalKey<CashSymbolRequest>, List<CashSymbol>> cashSymbolsMap =
      new MockIMap<>();
  private final IMap<LocalKey<String>, Account> accountByCardNumberMap = new MockIMap<>();
  private final IMap<LocalKey<String>, LegalEntity> legalEntityByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<String>, AccountResidues> accountResiduesByAccountMap =
      new MockIMap<>();
  private final IMap<LocalKey<String>, List<PaymentOrderCardIndex1>>
      payOrdersCardIndex1ByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<String>, List<PaymentOrderCardIndex2>>
      payOrdersCardIndex2ByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<String>, List<Seizure>> seizuresByAccountMap = new MockIMap<>();

  private final IMap<LocalKey<String>, CashDepositAnnouncement> ovnByUidMap = new MockIMap<>();
  private final IMap<LocalKey<CashDepositAnnouncementsRequest>, List<CashDepositAnnouncement>>
      ovnsMap = new MockIMap<>();
  private final IMap<LocalKey<AccountOperationRequest>, ExternalEntityContainer<BigDecimal>>
      commissionByAccountOperationMap = new MockIMap<>();
  private final IMap<LocalKey<String>, ExternalEntityContainer<String>> account20202ByWorkPlaceMap =
      new MockIMap<>();
  private final IMap<LocalKey<OperationPackageRequest>, OperationPackage> operationPackageInfoMap =
      new MockIMap<>();
  private final IMap<LocalKey<OperationTasksRequest>, List<OperationPackage>> operationPackageMap =
      new MockIMap<>();
  private final IMap<LocalKey<OperationPackage>, OperationPackage> operationPackageResponseMap =
      new MockIMap<>();
  private final IMap<LocalKey<RepresentativeRequest>, List<Representative>> representativeMap =
      new MockIMap<>();
  private final IMap<LocalKey<String>, Representative> representativeByCardNumberMap =
      new MockIMap<>();
  private final IMap<LocalKey<String>, Operator> operatorByUserMap = new MockIMap<>();

  @Mock
  private HazelcastBeClient client;
  @Mock
  private AuditService auditService;

  private HazelcastCacheImpl cache;

  /**
   * Подготовка объектов.
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    cache = new HazelcastCacheImpl(client, auditService);
    when(client.getUserBySessionMap()).thenReturn(userBySessionMap);
    when(client.getOperationByTaskMap()).thenReturn(operationByTaskMap);
    when(client.getOperationTypesByRolesMap()).thenReturn(operationTypesByRolesMap);
    when(client.getCashSymbolsMap()).thenReturn(cashSymbolsMap);
    when(client.getOperationTypeFavouritesByUserMap()).thenReturn(operationTypeFavouritesByUserMap);
    when(client.getAccountByCardNumberMap()).thenReturn(accountByCardNumberMap);
    when(client.getLegalEntityByAccountMap()).thenReturn(legalEntityByAccountMap);
    when(client.getAccountResiduesByAccountMap()).thenReturn(accountResiduesByAccountMap);
    when(client.getSeizuresByAccountMap()).thenReturn(seizuresByAccountMap);
    when(client.getPayOrdersCardIndex1ByAccountMap()).thenReturn(payOrdersCardIndex1ByAccountMap);
    when(client.getPayOrdersCardIndex2ByAccountMap()).thenReturn(payOrdersCardIndex2ByAccountMap);
    when(client.getCommissionByAccountOperationMap()).thenReturn(commissionByAccountOperationMap);
    when(client.getAccount20202ByWorkPlaceMap()).thenReturn(account20202ByWorkPlaceMap);
    when(client.getOvnByUidMap()).thenReturn(ovnByUidMap);
    when(client.getOvnsMap()).thenReturn(ovnsMap);
    when(client.getOperationPackageInfoMap()).thenReturn(operationPackageInfoMap);
    when(client.getOperationPackageMap()).thenReturn(operationPackageMap);
    when(client.getOperationPackageResponseMap()).thenReturn(operationPackageResponseMap);
    when(client.getRepresentativeMap()).thenReturn(representativeMap);
    when(client.getRepresentativeByCardNumberMap()).thenReturn(representativeByCardNumberMap);
    when(client.getOperatorByUserMap()).thenReturn(operatorByUserMap);

    doAnswer(new Answer() {
      @Override
      @SuppressWarnings("unchecked")
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        LocalKey key = (LocalKey) invocationOnMock.getArguments()[1];

        switch ((String) invocationOnMock.getArguments()[0]) {
          case RequestType.ACCOUNT_BY_CARD_NUMBER:
            accountByCardNumberMap.put(key, new Account());
            break;
          case RequestType.LEGAL_ENTITY_BY_ACCOUNT:
            legalEntityByAccountMap.put(key, new LegalEntity());
            break;
          case RequestType.ACCOUNT_RESIDUES_BY_ID:
            accountResiduesByAccountMap.put(key, new AccountResidues());
            break;
          case RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT:
            payOrdersCardIndex1ByAccountMap.put(key, new ArrayList<PaymentOrderCardIndex1>());
            payOrdersCardIndex2ByAccountMap.put(key, new ArrayList<PaymentOrderCardIndex2>());
            break;
          case RequestType.SEIZURES_BY_ACCOUNT:
            seizuresByAccountMap.put(key, new ArrayList<Seizure>());
            break;
          case RequestType.GET_OVN_LIST:
            ovnsMap.put(key, new ArrayList<CashDepositAnnouncement>());
            break;
          case RequestType.GET_OVN:
            ovnByUidMap.put(key, new CashDepositAnnouncement());
            break;
          case RequestType.COUNT_COMMISSION:
            commissionByAccountOperationMap.put(key, new ExternalEntityContainer<>(BigDecimal.TEN));
            break;
          case RequestType.ACCOUNT_20202:
            account20202ByWorkPlaceMap.put(key, new ExternalEntityContainer<>("1234567890"));
            break;
          case RequestType.CHECK_OPER_PACKAGE:
          case RequestType.CREATE_OPER_PACKAGE:
            operationPackageInfoMap.put(key, new OperationPackage());
            break;
          case RequestType.GET_OPER_TASKS:
            operationPackageMap.put(key, Collections.singletonList(new OperationPackage()));
            break;
          case RequestType.ADD_OPER_TASK:
          case RequestType.UPDATE_OPER_TASK:
            operationPackageResponseMap.put(key, new OperationPackage());
            break;
          case RequestType.GET_REPRESENTATIVE_BY_CARD:
            representativeByCardNumberMap.put(key, new Representative());
            break;
          case RequestType.SEARCH_REPRESENTATIVE:
            representativeMap.put(key, Collections.singletonList(new Representative()));
            break;
          case RequestType.CASH_SYMBOL:
            cashSymbolsMap.put(key, Collections.singletonList(new CashSymbol()));
            break;
          case RequestType.OPERATOR_BY_USER:
            operatorByUserMap.put(key, new Operator());
            break;
          default:
        }
        return null;
      }
    }).when(client).requestExternalEntity(anyString(), any(LocalKey.class));

    doNothing().when(auditService)
        .auditRequest(any(ClientInfo.class), anyString(), anyObject(), anyObject());
    doNothing().when(auditService)
        .auditRequest(any(ClientInfo.class), anyString(), anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testGetExternalEntities() throws Exception {
    assertNotNull(cache.getAccount("1", CLIENT_INFO));
    assertNotNull(cache.getLegalEntity("1", CLIENT_INFO));
    assertNotNull(cache.getAccountResidues("1", CLIENT_INFO));
    assertNotNull(cache.getCardIndexes1("1", CLIENT_INFO));
    assertNotNull(cache.getCardIndexes2("1", CLIENT_INFO));
    assertNotNull(cache.getSeizures("1", CLIENT_INFO));
    assertNotNull(cache.getAnnouncements(new CashDepositAnnouncementsRequest(), CLIENT_INFO));
    assertNotNull(cache.getAnnouncementById("1", CLIENT_INFO));
    assertNotNull(cache.getCommission(new AccountOperationRequest(), CLIENT_INFO));
    assertNotNull(cache.getAccount20202("1", CLIENT_INFO));
    assertNotNull(cache.getPackage(new OperationPackageRequest(), CLIENT_INFO));
    assertNotNull(cache.createPackage(new OperationPackageRequest(), CLIENT_INFO));
    assertNotNull(cache.getTasksInPackage(new OperationTasksRequest(), CLIENT_INFO));
    assertNotNull(cache.getTasksInPackages(new OperationTasksRequest(), CLIENT_INFO));
    assertNotNull(cache.addTasksInPackage(new OperationPackage(), CLIENT_INFO));
    assertNotNull(cache.updateTasksInPackage(new OperationPackage(), CLIENT_INFO));
    assertNotNull(cache.getRepresentativeByCardNumber("1", CLIENT_INFO));
    assertNotNull(cache.getRepresentativeByCriteria(new RepresentativeRequest("1"), CLIENT_INFO));
    assertNotNull(cache.getRepresentativesByCriteria(new RepresentativeRequest("1"), CLIENT_INFO));
    assertNotNull(cache.getCashSymbols(new CashSymbolRequest(), CLIENT_INFO));
    assertNotNull(cache.getOperator("1", CLIENT_INFO));

    Operation operation = new Operation();
    operation.setId(OPERATION_ID);
    cache.addOperation(1L, operation);
    assertNotNull(cache.getOperation(1L));
    assertEquals(cache.getOperation(1L).getId(), OPERATION_ID);
  }

  @Test
  public void testGetExternalEntities_getCardIndexes2() throws Exception {
    assertNotNull(cache.getCardIndexes2("1", CLIENT_INFO));
  }

  @Test
  public void testGetOperationTypes() throws Exception {
    cache.addUser(SESSION_ID, USER);

    cache.getOperationTypes(CLIENT_INFO);
    cache.saveOperationTypeFavourites(ImmutableList.of(new OperationTypeFavourite(1L, 1)), null,
        CLIENT_INFO);
    List<OperationTypeFavourite> favourites = cache.getOperationTypeFavourites(CLIENT_INFO);
    assertNotNull(favourites);
    assertEquals(favourites.size(), 1);
  }

  @Test
  public void testUserCache() throws Exception {
    assertEquals(client.getUserBySessionMap().size(), 0);

    cache.addUser(SESSION_ID, USER);
    assertEquals(client.getUserBySessionMap().size(), 1);

    User cacheUser = cache.getUser(SESSION_ID);
    assertEquals(cacheUser, USER);

    boolean removed = cache.removeUser(SESSION_ID);
    assertTrue(removed);
    assertEquals(client.getUserBySessionMap().size(), 0);

    boolean removed2 = cache.removeUser(SESSION_ID);
    assertFalse(removed2);
    assertEquals(client.getUserBySessionMap().size(), 0);
  }

  @Test(expected = UserNotFoundException.class)
  public void testUserNotFound() throws Exception {
    cache.getUser(SESSION_ID);
  }
}
