package ru.philit.ufs.web.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.OperationCache;
import ru.philit.ufs.model.cache.mock.MockCacheImpl;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

@RunWith(DataProviderRunner.class)
public class OperationProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User("login"), "2");
  private static final String WORKPLACE_ID = "27841892";
  private static final OperationTaskCardDeposit TASK = new OperationTaskCardDeposit();
  private static final Long PACKAGE_ID = 12413L;
  private static final Long TASK_ID = 381935L;
  private static final String TYPE_CODE = OperationTypeCode.TO_CARD_DEPOSIT.code();

  /**
   * Список пакетов операций, считаемых пустыми.
   */
  @DataProvider
  public static Object[] cacheEmptyPackages() {
    return new Object[]{
        null,
        new OperationPackage()
    };
  }

  @Mock
  private OperationCache cache;
  @Spy
  private MockCache mockCache = new MockCacheImpl();
  @Mock
  private RepresentativeProvider representativeProvider;

  private OperationProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new OperationProvider(representativeProvider, cache, mockCache);
  }

  @Test
  public void testAddActiveDepositTask() throws Exception {
    // given
    OperationPackage opPackage = new OperationPackage();
    opPackage.setId(PACKAGE_ID);

    // when
    when(cache.createPackage(any(OperationPackageRequest.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    when(cache.addTasksInPackage(any(OperationPackage.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    provider.addActiveDepositTask(WORKPLACE_ID, TASK, CLIENT_INFO);

    // verify
    verify(cache, times(1)).createPackage(any(OperationPackageRequest.class),
        any(ClientInfo.class));
    verify(cache, times(1)).addTasksInPackage(any(OperationPackage.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testAddActiveDepositTask_NullWorkplaceId() throws Exception {
    // when
    provider.addActiveDepositTask(null, TASK, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testAddActiveDepositTask_NullTask() throws Exception {
    // when
    provider.addActiveDepositTask(WORKPLACE_ID, null, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test
  @UseDataProvider("cacheEmptyPackages")
  public void testAddActiveDepositTask_NullFromCache(OperationPackage cached) throws Exception {
    // given
    OperationPackage opPackage = new OperationPackage();
    opPackage.setId(PACKAGE_ID);

    // when
    when(cache.getPackage(any(OperationPackageRequest.class), any(ClientInfo.class)))
        .thenReturn(cached);
    when(cache.createPackage(any(OperationPackageRequest.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    when(cache.addTasksInPackage(any(OperationPackage.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    provider.addActiveDepositTask(WORKPLACE_ID, TASK, CLIENT_INFO);

    // verify
    /*verify(cache, times(1))
        .getPackage(any(OperationPackageRequest.class), any(ClientInfo.class));*/
    verify(cache, times(1))
        .createPackage(any(OperationPackageRequest.class), any(ClientInfo.class));
    verify(cache, times(1))
        .addTasksInPackage(any(OperationPackage.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testAddForwardedDepositTask() throws Exception {
    // given
    OperationPackage opPackage = new OperationPackage();
    opPackage.setId(PACKAGE_ID);

    // when
    when(cache.createPackage(any(OperationPackageRequest.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    when(cache.addTasksInPackage(any(OperationPackage.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    provider.addForwardedDepositTask(WORKPLACE_ID, TASK, CLIENT_INFO);

    // verify
    verify(cache, times(1)).createPackage(any(OperationPackageRequest.class),
        any(ClientInfo.class));
    verify(cache, times(1)).addTasksInPackage(any(OperationPackage.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testConfirmOperation() throws Exception {
    // given
    OperationTask task1 = new OperationTask();
    task1.setId(TASK_ID - 1);
    OperationTask task2 = new OperationTask();
    task2.setId(TASK_ID);
    OperationPackage opPackage = new OperationPackage();
    opPackage.setToCardDeposits(Arrays.asList(task1, task2));

    // when
    when(cache.getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    when(cache.updateTasksInPackage(any(OperationPackage.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    doNothing().when(cache)
        .addOperation(anyLong(), any(Operation.class));
    provider.confirmOperation(PACKAGE_ID, TASK_ID, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verify(cache, times(1))
        .getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class));
    verify(cache, times(1))
        .updateTasksInPackage(any(OperationPackage.class), any(ClientInfo.class));
    verify(cache, times(1))
        .addOperation(anyLong(), any(Operation.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testConfirmOperationNullPackageId() throws Exception {
    // when
    provider.confirmOperation(null, TASK_ID, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testConfirmOperationNullTaskId() throws Exception {
    // when
    provider.confirmOperation(PACKAGE_ID, null, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testConfirmOperationNullWorkplaceId() throws Exception {
    // when
    provider.confirmOperation(PACKAGE_ID, TASK_ID, null, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testConfirmOperationNullTypeCode() throws Exception {
    // when
    provider.confirmOperation(PACKAGE_ID, TASK_ID, WORKPLACE_ID, null, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testConfirmOperation_NullFromCache() throws Exception {
    // when
    when(cache.getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class)))
        .thenReturn(null);
    provider.confirmOperation(PACKAGE_ID, TASK_ID, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verify(cache, times(1))
        .getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testConfirmOperation_NoTasks() throws Exception {
    // given
    OperationPackage opPackage = new OperationPackage();

    // when
    when(cache.getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    when(cache.updateTasksInPackage(any(OperationPackage.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    doNothing().when(cache)
        .addOperation(anyLong(), any(Operation.class));
    provider.confirmOperation(PACKAGE_ID, TASK_ID, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verify(cache, times(1))
        .getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class));
    verify(cache, times(1))
        .updateTasksInPackage(any(OperationPackage.class), any(ClientInfo.class));
    verify(cache, times(1))
        .addOperation(anyLong(), any(Operation.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testCancelOperation() throws Exception {
    // given
    OperationPackage opPackage = new OperationPackage();

    // when
    when(cache.getTasksInPackage(any(OperationTasksRequest.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    when(cache.updateTasksInPackage(any(OperationPackage.class), any(ClientInfo.class)))
        .thenReturn(opPackage);
    doNothing().when(cache).addOperation(anyLong(), any(Operation.class));
    provider.cancelOperation(PACKAGE_ID, TASK_ID, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getTasksInPackage(any(OperationTasksRequest.class),
        any(ClientInfo.class));
    verify(cache, times(1)).updateTasksInPackage(any(OperationPackage.class),
        any(ClientInfo.class));
    verify(cache, times(1)).addOperation(anyLong(), any(Operation.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testCancelOperation_NullPackageId() throws Exception {
    // when
    provider.cancelOperation(null, TASK_ID, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testCancelOperation_NullTaskId() throws Exception {
    // when
    provider.cancelOperation(PACKAGE_ID, null, WORKPLACE_ID, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testCancelOperation_NullWorkplaceId() throws Exception {
    // when
    provider.cancelOperation(PACKAGE_ID, TASK_ID, null, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testCancelOperation_NullTypeCode() throws Exception {
    // when
    provider.cancelOperation(PACKAGE_ID, TASK_ID, WORKPLACE_ID, null, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

}