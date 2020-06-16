package ru.philit.ufs.web.provider;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.cache.AnnouncementCache;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.OperationCache;
import ru.philit.ufs.model.cache.UserCache;
import ru.philit.ufs.model.cache.mock.MockCacheImpl;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

public class ReportProviderTest {

  private static final String LOGIN = "login";
  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User(LOGIN), "2");
  private static final Date DATE = new Date();
  private static final String ACCOUNT_ID = "3562212";
  private static final BigDecimal AMOUNT = new BigDecimal("14812.28");
  private static final Operation OPERATION = new Operation();

  @Mock
  private OperationCache operationCache;
  @Mock
  private AnnouncementCache announcementCache;
  @Mock
  private UserCache userCache;
  @Spy
  private MockCache mockCache = new MockCacheImpl();

  private ReportProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new ReportProvider(operationCache, announcementCache, userCache, mockCache);
  }

  @Test
  public void testGetOperationPackages() throws Exception {
    // given
    List<OperationPackage> packagesFromCache = singletonList(new OperationPackage());

    // when
    when(operationCache.getTasksInPackages(any(OperationTasksRequest.class), any(ClientInfo.class)))
        .thenReturn(packagesFromCache);
    List<OperationPackage> packages = provider.getOperationPackages(DATE, DATE, CLIENT_INFO);

    // then
    assertNotNull(packages);

    // verify
    verify(operationCache, times(2))
        .getTasksInPackages(any(OperationTasksRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(operationCache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetOperationPackages_NullDateFrom() throws Exception {
    // when
    provider.getOperationPackages(null, DATE, CLIENT_INFO);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetOperationPackages_NullDateTo() throws Exception {
    // when
    provider.getOperationPackages(DATE, null, CLIENT_INFO);
  }

  @Test
  public void testGetOperationPackages_NullFromCache() throws Exception {
    // when
    when(operationCache.getTasksInPackages(any(OperationTasksRequest.class), any(ClientInfo.class)))
        .thenReturn(null);
    List<OperationPackage> packages = provider.getOperationPackages(DATE, DATE, CLIENT_INFO);

    // then
    assertNotNull(packages);
    assertTrue(packages.isEmpty());

    // verify
    verify(operationCache, times(2))
        .getTasksInPackages(any(OperationTasksRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(operationCache);
  }

  @Test
  public void testGetOperation() throws Exception {
    // given
    OperationTask task = new OperationTask();

    // when
    when(operationCache.getOperation(anyLong())).thenReturn(new Operation());
    provider.getOperation(task);

    // verify
    verify(operationCache, times(1)).getOperation(anyLong());
    verifyNoMoreInteractions(operationCache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetOperation_NullTask() throws Exception {
    // when
    provider.getOperation(null);
  }

  @Test
  public void testGetUser() throws Exception {
    // when
    provider.getUser(LOGIN);
  }

  @Test
  public void testGetOperator() throws Exception {
    // when
    when(userCache.getOperator(anyString(), any(ClientInfo.class))).thenReturn(new Operator());
    provider.getOperator(LOGIN, CLIENT_INFO);

    // verify
    verify(userCache, times(1)).getOperator(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(userCache);
  }

  @Test
  public void testGetOperator_NullLogin() throws Exception {
    // when
    Operator operator = provider.getOperator(null, CLIENT_INFO);

    // then
    assertNull(operator);

    // verify
    verifyZeroInteractions(userCache);
  }

  @Test
  public void testGetCommission() throws Exception {
    // when
    when(announcementCache.getCommission(any(AccountOperationRequest.class), any(ClientInfo.class)))
        .thenReturn(BigDecimal.ONE);
    provider.getCommission(ACCOUNT_ID, AMOUNT, OPERATION, CLIENT_INFO);

    // verify
    verify(announcementCache, times(1))
        .getCommission(any(AccountOperationRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(announcementCache);
  }

  @Test
  public void testGetCommission_NullAccountId() throws Exception {
    // when
    BigDecimal commission = provider.getCommission(null, AMOUNT, OPERATION, CLIENT_INFO);

    // then
    assertNull(commission);

    // verify
    verifyZeroInteractions(announcementCache);
  }

  @Test
  public void testGetCommission_NullAmount() throws Exception {
    // when
    BigDecimal commission = provider.getCommission(ACCOUNT_ID, null, OPERATION, CLIENT_INFO);

    // then
    assertNull(commission);

    // verify
    verifyZeroInteractions(announcementCache);
  }

  @Test
  public void testGetCommission_NullOperation() throws Exception {
    // when
    BigDecimal commission = provider.getCommission(ACCOUNT_ID, AMOUNT, null, CLIENT_INFO);

    // then
    assertNull(commission);

    // verify
    verifyZeroInteractions(announcementCache);
  }

}