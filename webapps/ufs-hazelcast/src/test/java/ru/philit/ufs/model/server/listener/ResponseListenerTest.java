package ru.philit.ufs.model.server.listener;

import static org.mockito.Mockito.when;

import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.request.RequestType;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.server.HazelcastServer;
import ru.philit.ufs.model.server.MockIMap;
import ru.philit.ufs.model.server.MockIQueue;

public class ResponseListenerTest {

  private static final String SESSION_ID = "444";
  private static final String CARD_NUMBER = "4894123569871254";
  private static final String ACCOUNT_ID = "111111";
  private static final String LEGAL_ENTITY_ID = "112";
  private static final String FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";
  private static final String OVN_UID = "121212";

  private final IQueue<ExternalEntity> responseQueue = new MockIQueue<>(5);
  private final IMap<String, ExternalEntityRequest> requestMap = new MockIMap<>();
  private final IMap<ExternalEntityRequest, String> responseFlagMap = new MockIMap<>();

  private final IMap<LocalKey<String>, Account> accountByIdMap = new MockIMap<>();
  private final IMap<LocalKey<String>, Account> accountByCardNumberMap = new MockIMap<>();
  private final IMap<LocalKey<String>, AccountResidues> accountResiduesByIdMap = new MockIMap<>();
  private final IMap<LocalKey<String>, List<Account>> accountsByLegalEntityMap = new MockIMap<>();
  private final IMap<LocalKey<String>, LegalEntity> legalEntityByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<String>, List<Seizure>> seizuresByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<String>, List<PaymentOrderCardIndex1>>
      payOrdersCardIndex1ByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<String>, List<PaymentOrderCardIndex2>>
      payOrdersCardIndex2ByAccountMap = new MockIMap<>();
  private final IMap<LocalKey<AccountOperationRequest>, ExternalEntityContainer<BigDecimal>>
      commissionByAccountOperationMap = new MockIMap<>();
  private final IMap<LocalKey<AccountOperationRequest>, ExternalEntityContainer<Boolean>>
      checkFraudByAccountOperationMap = new MockIMap<>();

  private final IMap<LocalKey<OperationPackageRequest>, OperationPackage> operationPackageInfoMap =
      new MockIMap<>();
  private final IMap<LocalKey<OperationTasksRequest>, List<OperationPackage>> operationPackageMap =
      new MockIMap<>();
  private final IMap<LocalKey<OperationPackage>, OperationPackage> operationPackageResponseMap =
      new MockIMap<>();

  private final IMap<LocalKey<String>, CashDepositAnnouncement> ovnByUidMap = new MockIMap<>();
  private final IMap<LocalKey<CashDepositAnnouncementsRequest>, List<CashDepositAnnouncement>>
      ovnsMap = new MockIMap<>();
  private final IMap<LocalKey<CashDepositAnnouncement>, CashDepositAnnouncement> ovnResponseMap =
      new MockIMap<>();

  private final IMap<LocalKey<String>, ExternalEntityContainer<String>>
      account20202ByWorkPlaceMap = new MockIMap<>();
  private final IMap<LocalKey<Serializable>, List<OperationType>> operationTypesByRolesMap =
      new MockIMap<>();
  private final IMap<LocalKey<RepresentativeRequest>, List<Representative>> representativeMap =
      new MockIMap<>();

  private final IMap<String, List<OperationTypeFavourite>> operationTypeFavouritesByUserMap =
      new MockIMap<>();

  private final IMap<LocalKey<String>, Representative> representativeByCardMap = new MockIMap<>();

  private final IMap<LocalKey<String>, Operator> operatorByUserMap = new MockIMap<>();

  private final IMap<LocalKey<CashSymbolRequest>, List<CashSymbol>> cashSymbolsMap =
      new MockIMap<>();

  @Mock
  private HazelcastServer hazelcastServer;

  private ResponseListener responseListener;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    responseListener = new ResponseListener(hazelcastServer);
    when(hazelcastServer.getResponseQueue()).thenReturn(responseQueue);
    when(hazelcastServer.getRequestMap()).thenReturn(requestMap);
    when(hazelcastServer.getResponseFlagMap()).thenReturn(responseFlagMap);
    when(hazelcastServer.getAccountByIdMap()).thenReturn(accountByIdMap);
    when(hazelcastServer.getAccountByCardNumberMap()).thenReturn(accountByCardNumberMap);
    when(hazelcastServer.getAccountResiduesByIdMap()).thenReturn(accountResiduesByIdMap);
    when(hazelcastServer.getAccountsByLegalEntityMap()).thenReturn(accountsByLegalEntityMap);
    when(hazelcastServer.getLegalEntityByAccountMap()).thenReturn(legalEntityByAccountMap);
    when(hazelcastServer.getSeizuresByAccountMap()).thenReturn(seizuresByAccountMap);
    when(hazelcastServer.getPayOrdersCardIndex1ByAccountMap())
        .thenReturn(payOrdersCardIndex1ByAccountMap);
    when(hazelcastServer.getPayOrdersCardIndex2ByAccountMap())
        .thenReturn(payOrdersCardIndex2ByAccountMap);
    when(hazelcastServer.getCommissionByAccountOperationMap())
        .thenReturn(commissionByAccountOperationMap);
    when(hazelcastServer.getCheckFraudByAccountOperationMap())
        .thenReturn(checkFraudByAccountOperationMap);
    when(hazelcastServer.getOperationPackageInfoMap()).thenReturn(operationPackageInfoMap);
    when(hazelcastServer.getOperationPackageMap()).thenReturn(operationPackageMap);
    when(hazelcastServer.getOperationPackageResponseMap()).thenReturn(operationPackageResponseMap);
    when(hazelcastServer.getOvnByUidMap()).thenReturn(ovnByUidMap);
    when(hazelcastServer.getOvnsMap()).thenReturn(ovnsMap);
    when(hazelcastServer.getOvnResponseMap()).thenReturn(ovnResponseMap);
    when(hazelcastServer.getAccount20202ByWorkPlaceMap()).thenReturn(account20202ByWorkPlaceMap);
    when(hazelcastServer.getOperationTypesByRolesMap()).thenReturn(operationTypesByRolesMap);
    when(hazelcastServer.getRepresentativeMap()).thenReturn(representativeMap);
    when(hazelcastServer.getOperationTypeFavouritesByUserMap())
        .thenReturn(operationTypeFavouritesByUserMap);
    when(hazelcastServer.getRepresentativeByCardMap()).thenReturn(representativeByCardMap);
    when(hazelcastServer.getOperatorByUserMap()).thenReturn(operatorByUserMap);
    when(hazelcastServer.getCashSymbolsMap()).thenReturn(cashSymbolsMap);
    responseListener.init();
  }

  @Test
  public void testItemAdded_AccountByCardNumber() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setEntityType(RequestType.ACCOUNT_BY_CARD_NUMBER);
    request.setRequestData(CARD_NUMBER);
    Account account = new Account();
    account.setRequestUid(FIX_UUID.replace('2', '3'));
    account.setReceiveDate(new Date());
    account.setId(ACCOUNT_ID);

    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(account);
    // then
    Assert.assertFalse(responseFlagMap.containsKey(request));

    // when
    account.setRequestUid(FIX_UUID);
    responseQueue.add(account);
    // then
    Assert.assertFalse(responseFlagMap.containsKey(request));

    // when
    request.setSessionId(SESSION_ID);
    responseQueue.add(account);

    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, CARD_NUMBER);
    Assert.assertTrue(accountByCardNumberMap.containsKey(localKey));
    Assert.assertEquals(accountByCardNumberMap.get(localKey), account);
  }

  @Test
  public void testItemAdded_AccountByCardNumber_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_BY_CARD_NUMBER);
    request.setRequestData(CARD_NUMBER);
    ExternalEntity account = new ExternalEntity();
    account.setRequestUid(FIX_UUID);
    account.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(account);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, CARD_NUMBER);
    Assert.assertFalse(accountByIdMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_AccountById() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_BY_ID);
    request.setRequestData(ACCOUNT_ID);
    Account account = new Account();
    account.setRequestUid(FIX_UUID);
    account.setReceiveDate(new Date());
    account.setId(ACCOUNT_ID);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(account);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(accountByIdMap.containsKey(localKey));
    Assert.assertEquals(accountByIdMap.get(localKey), account);
  }

  @Test
  public void testItemAdded_AccountById_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_BY_ID);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntity account = new ExternalEntity();
    account.setRequestUid(FIX_UUID);
    account.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(account);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(accountByIdMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_AccountResiduesById() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_RESIDUES_BY_ID);
    request.setRequestData(ACCOUNT_ID);
    AccountResidues accountResidues = new AccountResidues();
    accountResidues.setRequestUid(FIX_UUID);
    accountResidues.setReceiveDate(new Date());
    accountResidues.setAccountId(ACCOUNT_ID);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(accountResidues);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(accountResiduesByIdMap.containsKey(localKey));
    Assert.assertEquals(accountResiduesByIdMap.get(localKey), accountResidues);
  }

  @Test
  public void testItemAdded_AccountResiduesById_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_RESIDUES_BY_ID);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntity accountResidues = new ExternalEntity();
    accountResidues.setRequestUid(FIX_UUID);
    accountResidues.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(accountResidues);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(accountResiduesByIdMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_AccountsByLegalEntity() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNTS_BY_LEGAL_ENTITY);
    request.setRequestData(LEGAL_ENTITY_ID);
    ExternalEntityList<Account> accounts = new ExternalEntityList<>();
    accounts.setRequestUid(FIX_UUID);
    accounts.setReceiveDate(new Date());
    Account account = new Account();
    account.setRequestUid(FIX_UUID);
    account.setReceiveDate(new Date());
    account.setId(ACCOUNT_ID);
    accounts.getItems().add(account);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(accounts);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, LEGAL_ENTITY_ID);
    Assert.assertTrue(accountsByLegalEntityMap.containsKey(localKey));
    Assert.assertEquals(accountsByLegalEntityMap.get(localKey), accounts.getItems());
    LocalKey<String> localKey2 = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(accountByIdMap.containsKey(localKey2));
    Assert.assertEquals(accountByIdMap.get(localKey2), account);
  }

  @Test
  public void testItemAdded_AccountsByLegalEntity_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNTS_BY_LEGAL_ENTITY);
    request.setRequestData(LEGAL_ENTITY_ID);
    ExternalEntity accounts = new ExternalEntity();
    accounts.setRequestUid(FIX_UUID);
    accounts.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(accounts);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, LEGAL_ENTITY_ID);
    Assert.assertFalse(accountsByLegalEntityMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_AccountsByLegalEntity_Bad2() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNTS_BY_LEGAL_ENTITY);
    request.setRequestData(LEGAL_ENTITY_ID);
    ExternalEntityList<ExternalEntity> accounts = new ExternalEntityList<>();
    accounts.setRequestUid(FIX_UUID);
    accounts.setReceiveDate(new Date());
    accounts.getItems().add(new ExternalEntity());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(accounts);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, LEGAL_ENTITY_ID);
    Assert.assertFalse(accountsByLegalEntityMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_LegalEntity() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.LEGAL_ENTITY_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    LegalEntity legalEntity = new LegalEntity();
    legalEntity.setRequestUid(FIX_UUID);
    legalEntity.setReceiveDate(new Date());
    legalEntity.setId(LEGAL_ENTITY_ID);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(legalEntity);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(legalEntityByAccountMap.containsKey(localKey));
    Assert.assertEquals(legalEntityByAccountMap.get(localKey), legalEntity);
  }

  @Test
  public void testItemAdded_LegalEntity_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.LEGAL_ENTITY_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntity legalEntity = new ExternalEntity();
    legalEntity.setRequestUid(FIX_UUID);
    legalEntity.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(legalEntity);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(legalEntityByAccountMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CardIndexes() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntityList2<PaymentOrderCardIndex1, PaymentOrderCardIndex2> paymentOrders =
        new ExternalEntityList2<>();
    paymentOrders.setRequestUid(FIX_UUID);
    paymentOrders.setReceiveDate(new Date());
    PaymentOrderCardIndex1 paymentOrder1 = new PaymentOrderCardIndex1();
    paymentOrder1.setRequestUid(FIX_UUID);
    paymentOrder1.setReceiveDate(new Date());
    paymentOrder1.setDocId("101");
    paymentOrders.getItems().add(paymentOrder1);
    PaymentOrderCardIndex2 paymentOrder2 = new PaymentOrderCardIndex2();
    paymentOrder2.setRequestUid(FIX_UUID);
    paymentOrder2.setReceiveDate(new Date());
    paymentOrder2.setDocId("102");
    paymentOrders.getItems2().add(paymentOrder2);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(paymentOrders);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(payOrdersCardIndex1ByAccountMap.containsKey(localKey));
    Assert.assertTrue(payOrdersCardIndex2ByAccountMap.containsKey(localKey));
    Assert.assertEquals(payOrdersCardIndex2ByAccountMap.get(localKey), paymentOrders.getItems2());
  }

  @Test
  public void testItemAdded_CardIndexes_Empty() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntityList2<PaymentOrderCardIndex1, PaymentOrderCardIndex2> paymentOrders =
        new ExternalEntityList2<>();
    paymentOrders.setRequestUid(FIX_UUID);
    paymentOrders.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(paymentOrders);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(payOrdersCardIndex1ByAccountMap.containsKey(localKey));
    Assert.assertTrue(payOrdersCardIndex2ByAccountMap.containsKey(localKey));
    Assert.assertEquals(payOrdersCardIndex2ByAccountMap.get(localKey), paymentOrders.getItems2());
  }

  @Test
  public void testItemAdded_CardIndexes_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntity paymentOrders = new ExternalEntity();
    paymentOrders.setRequestUid(FIX_UUID);
    paymentOrders.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(paymentOrders);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(payOrdersCardIndex1ByAccountMap.containsKey(localKey));
    Assert.assertFalse(payOrdersCardIndex2ByAccountMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CardIndexes_Bad2() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntityList2<ExternalEntity, ExternalEntity> paymentOrders = new ExternalEntityList2<>();
    paymentOrders.setRequestUid(FIX_UUID);
    paymentOrders.setReceiveDate(new Date());
    paymentOrders.getItems().add(new ExternalEntity());
    paymentOrders.getItems2().add(new ExternalEntity());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(paymentOrders);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(payOrdersCardIndex1ByAccountMap.containsKey(localKey));
    Assert.assertFalse(payOrdersCardIndex2ByAccountMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Seizures() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.SEIZURES_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntityList<Seizure> seizures = new ExternalEntityList<>();
    seizures.setRequestUid(FIX_UUID);
    seizures.setReceiveDate(new Date());
    Seizure seizure = new Seizure();
    seizure.setRequestUid(FIX_UUID);
    seizure.setReceiveDate(new Date());
    seizure.setId(1L);
    seizures.getItems().add(seizure);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(seizures);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertTrue(seizuresByAccountMap.containsKey(localKey));
    Assert.assertEquals(seizuresByAccountMap.get(localKey), seizures.getItems());
  }

  @Test
  public void testItemAdded_Seizures_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.SEIZURES_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntity seizures = new ExternalEntity();
    seizures.setRequestUid(FIX_UUID);
    seizures.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(seizures);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(seizuresByAccountMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Seizures_Bad2() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.SEIZURES_BY_ACCOUNT);
    request.setRequestData(ACCOUNT_ID);
    ExternalEntityList<ExternalEntity> seizures = new ExternalEntityList<>();
    seizures.setRequestUid(FIX_UUID);
    seizures.setReceiveDate(new Date());
    seizures.getItems().add(new ExternalEntity());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(seizures);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, ACCOUNT_ID);
    Assert.assertFalse(seizuresByAccountMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Commission() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.COUNT_COMMISSION);
    AccountOperationRequest requestKey = new AccountOperationRequest(ACCOUNT_ID,
        BigDecimal.valueOf(100), OperationTypeCode.TO_CARD_DEPOSIT);
    request.setRequestData(requestKey);
    ExternalEntityContainer<BigDecimal> commission = new ExternalEntityContainer<>();
    commission.setRequestUid(FIX_UUID);
    commission.setReceiveDate(new Date());
    commission.setData(null);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(commission);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<AccountOperationRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertTrue(commissionByAccountOperationMap.containsKey(localKey));
    Assert.assertEquals(commissionByAccountOperationMap.get(localKey), commission);
  }

  @Test
  public void testItemAdded_Commission_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.COUNT_COMMISSION);
    AccountOperationRequest requestKey = new AccountOperationRequest(ACCOUNT_ID,
        BigDecimal.valueOf(100), OperationTypeCode.TO_CARD_DEPOSIT);
    request.setRequestData(requestKey);
    ExternalEntity commission = new ExternalEntity();
    commission.setRequestUid(FIX_UUID);
    commission.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(commission);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<AccountOperationRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertFalse(commissionByAccountOperationMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Commission_Bad2() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.COUNT_COMMISSION);
    AccountOperationRequest requestKey = new AccountOperationRequest(ACCOUNT_ID,
        BigDecimal.valueOf(100), OperationTypeCode.TO_CARD_DEPOSIT);
    request.setRequestData(requestKey);
    ExternalEntityContainer<String> commission = new ExternalEntityContainer<>();
    commission.setRequestUid(FIX_UUID);
    commission.setReceiveDate(new Date());
    commission.setData("1");
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(commission);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<AccountOperationRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertFalse(commissionByAccountOperationMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CheckFraud() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CHECK_WITH_FRAUD);
    AccountOperationRequest requestKey = new AccountOperationRequest(ACCOUNT_ID,
        BigDecimal.valueOf(100), OperationTypeCode.TO_CARD_DEPOSIT);
    request.setRequestData(requestKey);
    ExternalEntityContainer<Boolean> checked = new ExternalEntityContainer<>();
    checked.setRequestUid(FIX_UUID);
    checked.setReceiveDate(new Date());
    checked.setData(true);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(checked);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<AccountOperationRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertTrue(checkFraudByAccountOperationMap.containsKey(localKey));
    Assert.assertEquals(checkFraudByAccountOperationMap.get(localKey), checked);
  }

  @Test
  public void testItemAdded_CheckFraud_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CHECK_WITH_FRAUD);
    AccountOperationRequest requestKey = new AccountOperationRequest(ACCOUNT_ID,
        BigDecimal.valueOf(100), OperationTypeCode.TO_CARD_DEPOSIT);
    request.setRequestData(requestKey);
    ExternalEntity checked = new ExternalEntity();
    checked.setRequestUid(FIX_UUID);
    checked.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(checked);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<AccountOperationRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertFalse(checkFraudByAccountOperationMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CheckFraud_Bad2() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CHECK_WITH_FRAUD);
    AccountOperationRequest requestKey = new AccountOperationRequest(ACCOUNT_ID,
        BigDecimal.valueOf(100), OperationTypeCode.TO_CARD_DEPOSIT);
    request.setRequestData(requestKey);
    ExternalEntityContainer<String> checked = new ExternalEntityContainer<>();
    checked.setRequestUid(FIX_UUID);
    checked.setReceiveDate(new Date());
    checked.setData("1");
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(checked);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<AccountOperationRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertFalse(checkFraudByAccountOperationMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CheckPackage() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CHECK_OPER_PACKAGE);
    OperationPackageRequest packageRequest = new OperationPackageRequest();
    request.setRequestData(packageRequest);
    OperationPackage opPackage = new OperationPackage(1L);
    opPackage.setRequestUid(FIX_UUID);
    opPackage.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(opPackage);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<OperationPackageRequest> localKey = new LocalKey<>(SESSION_ID, packageRequest);
    Assert.assertTrue(operationPackageInfoMap.containsKey(localKey));
    Assert.assertEquals(operationPackageInfoMap.get(localKey), opPackage);
  }

  @Test
  public void testItemAdded_CheckPackage_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CHECK_OPER_PACKAGE);
    OperationPackageRequest packageRequest = new OperationPackageRequest();
    request.setRequestData(packageRequest);
    ExternalEntity opPackage = new ExternalEntity();
    opPackage.setRequestUid(FIX_UUID);
    opPackage.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(opPackage);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<OperationPackageRequest> localKey = new LocalKey<>(SESSION_ID, packageRequest);
    Assert.assertFalse(operationPackageInfoMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_GetTasks() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_OPER_TASKS);
    OperationTasksRequest tasksRequest = new OperationTasksRequest();
    request.setRequestData(tasksRequest);
    ExternalEntityList<OperationPackage> packages = new ExternalEntityList<>();
    packages.setRequestUid(FIX_UUID);
    packages.setReceiveDate(new Date());
    OperationPackage opPackage = new OperationPackage();
    opPackage.setRequestUid(FIX_UUID);
    opPackage.setReceiveDate(new Date());
    opPackage.setId(12345L);
    packages.getItems().add(opPackage);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(packages);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<OperationTasksRequest> localKey = new LocalKey<>(SESSION_ID, tasksRequest);
    Assert.assertTrue(operationPackageMap.containsKey(localKey));
    Assert.assertEquals(operationPackageMap.get(localKey), packages.getItems());
  }

  @Test
  public void testItemAdded_GetTasks_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_OPER_TASKS);
    OperationTasksRequest tasksRequest = new OperationTasksRequest();
    request.setRequestData(tasksRequest);
    ExternalEntity packages = new ExternalEntity();
    packages.setRequestUid(FIX_UUID);
    packages.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(packages);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<OperationTasksRequest> localKey = new LocalKey<>(SESSION_ID, tasksRequest);
    Assert.assertFalse(operationPackageMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_AddTask() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ADD_OPER_TASK);
    OperationPackage packageRequest = new OperationPackage();
    request.setRequestData(packageRequest);
    OperationPackage opPackage = new OperationPackage(1L);
    opPackage.setRequestUid(FIX_UUID);
    opPackage.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(opPackage);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<OperationPackage> localKey = new LocalKey<>(SESSION_ID, packageRequest);
    Assert.assertTrue(operationPackageResponseMap.containsKey(localKey));
    Assert.assertEquals(operationPackageResponseMap.get(localKey), opPackage);
  }

  @Test
  public void testItemAdded_AddTask_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ADD_OPER_TASK);
    OperationPackage packageRequest = new OperationPackage();
    request.setRequestData(packageRequest);
    ExternalEntity opPackage = new ExternalEntity();
    opPackage.setRequestUid(FIX_UUID);
    opPackage.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(opPackage);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<OperationPackage> localKey = new LocalKey<>(SESSION_ID, packageRequest);
    Assert.assertFalse(operationPackageResponseMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_OvnList() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_OVN_LIST);
    CashDepositAnnouncementsRequest announcementsRequest = new CashDepositAnnouncementsRequest();
    request.setRequestData(announcementsRequest);
    ExternalEntityList<CashDepositAnnouncement> announcements = new ExternalEntityList<>();
    announcements.setRequestUid(FIX_UUID);
    announcements.setReceiveDate(new Date());
    CashDepositAnnouncement announcement = new CashDepositAnnouncement();
    announcement.setRequestUid(FIX_UUID);
    announcement.setReceiveDate(new Date());
    announcement.setNum(12345L);
    announcements.getItems().add(announcement);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(announcements);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<CashDepositAnnouncementsRequest> localKey = new LocalKey<>(SESSION_ID,
        announcementsRequest);
    Assert.assertTrue(ovnsMap.containsKey(localKey));
    Assert.assertEquals(ovnsMap.get(localKey), announcements.getItems());
  }

  @Test
  public void testItemAdded_OvnList_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_OVN_LIST);
    CashDepositAnnouncementsRequest announcementsRequest = new CashDepositAnnouncementsRequest();
    request.setRequestData(announcementsRequest);
    ExternalEntity announcements = new ExternalEntity();
    announcements.setRequestUid(FIX_UUID);
    announcements.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(announcements);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<CashDepositAnnouncementsRequest> localKey = new LocalKey<>(SESSION_ID,
        announcementsRequest);
    Assert.assertFalse(ovnsMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Ovn() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_OVN);
    request.setRequestData(OVN_UID);
    CashDepositAnnouncement ovn = new CashDepositAnnouncement();
    ovn.setRequestUid(FIX_UUID);
    ovn.setReceiveDate(new Date());
    ovn.setUid(OVN_UID);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(ovn);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, OVN_UID);
    Assert.assertTrue(ovnByUidMap.containsKey(localKey));
    Assert.assertEquals(ovnByUidMap.get(localKey), ovn);
  }

  @Test
  public void testItemAdded_Ovn_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_OVN);
    request.setRequestData(OVN_UID);
    ExternalEntity ovn = new ExternalEntity();
    ovn.setRequestUid(FIX_UUID);
    ovn.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(ovn);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, OVN_UID);
    Assert.assertFalse(ovnByUidMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CreateOvn() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CREATE_OVN);
    CashDepositAnnouncement announcementRequest = new CashDepositAnnouncement();
    request.setRequestData(announcementRequest);
    CashDepositAnnouncement ovn = new CashDepositAnnouncement();
    ovn.setRequestUid(FIX_UUID);
    ovn.setReceiveDate(new Date());
    ovn.setUid(OVN_UID);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(ovn);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<CashDepositAnnouncement> localKey = new LocalKey<>(SESSION_ID, announcementRequest);
    Assert.assertTrue(ovnResponseMap.containsKey(localKey));
    Assert.assertEquals(ovnResponseMap.get(localKey), ovn);
  }

  @Test
  public void testItemAdded_CreateOvn_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CREATE_OVN);
    CashDepositAnnouncement announcementRequest = new CashDepositAnnouncement();
    request.setRequestData(announcementRequest);
    ExternalEntity ovn = new ExternalEntity();
    ovn.setRequestUid(FIX_UUID);
    ovn.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(ovn);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<CashDepositAnnouncement> localKey = new LocalKey<>(SESSION_ID, announcementRequest);
    Assert.assertFalse(ovnResponseMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Account20202() throws Exception {
    // given
    final String workplaceId = "121212";
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_20202);
    request.setRequestData(workplaceId);
    ExternalEntityContainer<String> container = new ExternalEntityContainer<>();
    container.setRequestUid(FIX_UUID);
    container.setReceiveDate(new Date());
    container.setData("1546445");
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(container);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, workplaceId);
    Assert.assertTrue(account20202ByWorkPlaceMap.containsKey(localKey));
    Assert.assertEquals(account20202ByWorkPlaceMap.get(localKey), container);
  }

  @Test
  public void testItemAdded_Account20202_Bad() throws Exception {
    // given
    final String workplaceId = "121212";
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.ACCOUNT_20202);
    request.setRequestData(workplaceId);
    ExternalEntity container = new ExternalEntity();
    container.setRequestUid(FIX_UUID);
    container.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(container);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, workplaceId);
    Assert.assertFalse(account20202ByWorkPlaceMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_SearchRepresentative() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.SEARCH_REPRESENTATIVE);
    RepresentativeRequest requestKey = new RepresentativeRequest();
    request.setRequestData(requestKey);
    ExternalEntityList<Representative> representatives = new ExternalEntityList<>();
    representatives.setRequestUid(FIX_UUID);
    representatives.setReceiveDate(new Date());
    Representative representative = new Representative();
    representative.setRequestUid(FIX_UUID);
    representative.setReceiveDate(new Date());
    representative.setId("112233");
    representatives.getItems().add(representative);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(representatives);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<RepresentativeRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertTrue(representativeMap.containsKey(localKey));
    Assert.assertEquals(representativeMap.get(localKey), representatives.getItems());
  }

  @Test
  public void testItemAdded_SearchRepresentative_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.SEARCH_REPRESENTATIVE);
    RepresentativeRequest requestKey = new RepresentativeRequest();
    request.setRequestData(requestKey);
    ExternalEntity representatives = new ExternalEntity();
    representatives.setRequestUid(FIX_UUID);
    representatives.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(representatives);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<RepresentativeRequest> localKey = new LocalKey<>(SESSION_ID, requestKey);
    Assert.assertFalse(representativeMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_OperationTypes() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.OPER_TYPES_BY_ROLE);
    Serializable roles = (Serializable) Collections.singletonList("123");
    request.setRequestData(roles);
    ExternalEntityList<OperationType> operationTypes = new ExternalEntityList<>();
    operationTypes.setRequestUid(FIX_UUID);
    operationTypes.setReceiveDate(new Date());
    OperationType operationType = new OperationType();
    operationType.setRequestUid(FIX_UUID);
    operationType.setReceiveDate(new Date());
    operationType.setId("234651");
    operationTypes.getItems().add(operationType);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(operationTypes);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<Serializable> localKey = new LocalKey<>(SESSION_ID, roles);
    Assert.assertTrue(operationTypesByRolesMap.containsKey(localKey));
    Assert.assertEquals(operationTypesByRolesMap.get(localKey), operationTypes.getItems());
  }

  @Test
  public void testItemAdded_OperationTypes_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.OPER_TYPES_BY_ROLE);
    Serializable roles = (Serializable) Collections.singletonList("123");
    request.setRequestData(roles);
    ExternalEntity operationTypes = new ExternalEntity();
    operationTypes.setRequestUid(FIX_UUID);
    operationTypes.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(operationTypes);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<Serializable> localKey = new LocalKey<>(SESSION_ID, roles);
    Assert.assertFalse(operationTypesByRolesMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_RepresentativeByCard() throws Exception {
    // given
    final String card = "123456";
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_REPRESENTATIVE_BY_CARD);
    request.setRequestData(card);
    Representative representative = new Representative();
    representative.setRequestUid(FIX_UUID);
    representative.setReceiveDate(new Date());
    representative.setId("112233");
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(representative);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, card);
    Assert.assertTrue(representativeByCardMap.containsKey(localKey));
    Assert.assertEquals(representativeByCardMap.get(localKey), representative);
  }

  @Test
  public void testItemAdded_RepresentativeByCard_Bad() throws Exception {
    // given
    final String card = "123456";
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.GET_REPRESENTATIVE_BY_CARD);
    request.setRequestData(card);
    ExternalEntity representative = new ExternalEntity();
    representative.setRequestUid(FIX_UUID);
    representative.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(representative);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, card);
    Assert.assertFalse(representativeByCardMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_OperatorByUser() throws Exception {
    // given
    final String user = "Петров";
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.OPERATOR_BY_USER);
    request.setRequestData(user);
    Operator operator = new Operator();
    operator.setRequestUid(FIX_UUID);
    operator.setReceiveDate(new Date());
    operator.setOperatorFullName("Петров Петр Петрович");
    operator.setFirstName("Петр");
    operator.setLastName("Петров");
    operator.setPatronymic("Петрович");
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(operator);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, user);
    Assert.assertTrue(operatorByUserMap.containsKey(localKey));
    Assert.assertEquals(operatorByUserMap.get(localKey), operator);
  }

  @Test
  public void testItemAdded_OperatorByUser_Bad() throws Exception {
    // given
    final String user = "Петров";
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.OPERATOR_BY_USER);
    request.setRequestData(user);
    ExternalEntity operator = new ExternalEntity();
    operator.setRequestUid(FIX_UUID);
    operator.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(operator);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, user);
    Assert.assertFalse(operatorByUserMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_CashSymbol() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CASH_SYMBOL);
    CashSymbolRequest cashSymbolRequest = new CashSymbolRequest();
    cashSymbolRequest.setGetList(true);
    cashSymbolRequest.setCode("02");
    request.setRequestData(cashSymbolRequest);
    ExternalEntityList<CashSymbol> cashSymbols = new ExternalEntityList<>();
    cashSymbols.setRequestUid(FIX_UUID);
    cashSymbols.setReceiveDate(new Date());
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setRequestUid(FIX_UUID);
    cashSymbol.setReceiveDate(new Date());
    cashSymbol.setCode("02");
    cashSymbol.setDescription("Символ 02");
    cashSymbols.getItems().add(cashSymbol);
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(cashSymbols);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<CashSymbolRequest> localKey = new LocalKey<>(SESSION_ID, cashSymbolRequest);
    Assert.assertTrue(cashSymbolsMap.containsKey(localKey));
    Assert.assertEquals(cashSymbolsMap.get(localKey), cashSymbols.getItems());
  }

  @Test
  public void testItemAdded_CashSymbol_Bad() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType(RequestType.CASH_SYMBOL);
    CashSymbolRequest cashSymbolRequest = new CashSymbolRequest();
    cashSymbolRequest.setGetList(true);
    cashSymbolRequest.setCode("02");
    request.setRequestData(cashSymbolRequest);
    ExternalEntity cashSymbols = new ExternalEntity();
    cashSymbols.setRequestUid(FIX_UUID);
    cashSymbols.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(cashSymbols);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
    LocalKey<CashSymbolRequest> localKey = new LocalKey<>(SESSION_ID, cashSymbolRequest);
    Assert.assertFalse(cashSymbolsMap.containsKey(localKey));
  }

  @Test
  public void testItemAdded_Default() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType("Unknown");
    request.setRequestData("Something");
    ExternalEntity externalEntity = new ExternalEntity();
    externalEntity.setRequestUid(FIX_UUID);
    externalEntity.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(externalEntity);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
  }

  @Test
  public void testItemAdded_Default_SeveralListeners() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    request.setEntityType("Unknown");
    request.setRequestData("Something");
    ExternalEntity externalEntity = new ExternalEntity();
    externalEntity.setRequestUid(FIX_UUID);
    externalEntity.setReceiveDate(new Date());
    responseListener.init();
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(externalEntity);
    // then
    Assert.assertTrue(responseFlagMap.containsKey(request));
  }

  @Test
  public void testItemAdded_BadRequest() throws Exception {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(SESSION_ID);
    ExternalEntity externalEntity = new ExternalEntity();
    externalEntity.setRequestUid(FIX_UUID);
    externalEntity.setReceiveDate(new Date());
    // when
    requestMap.put(FIX_UUID, request);
    responseQueue.add(externalEntity);
    // then
    Assert.assertFalse(responseFlagMap.containsKey(request));
  }
}
