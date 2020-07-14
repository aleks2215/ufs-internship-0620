package ru.philit.ufs.model.cache.hazelcast;

import static org.mockito.Mockito.when;

import com.hazelcast.core.IMap;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

public class HazelcastMockCacheImplTest {

  private static final String CTRL_TASK_ELEMENT = "\"pkgTaskId\":10";
  private static final String INN = "77700044422232";
  private static final String USER_LOGIN = "aleks-login";

  static class TestTaskBody {

    public Long pkgTaskId;

    public TestTaskBody(Long pkgTaskId) {
      this.pkgTaskId = pkgTaskId;
    }
  }

  @Mock
  private HazelcastMockServer hazelcastMockServer;

  private HazelcastMockCacheImpl mockCache;

  private IMap<Long, Map<Long, String>> tasksCardDepositByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksCardWithdrawByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksAccountDepositByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksAccountWithdrawByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksCheckbookIssuingByPackageId = new MockIMap<>();
  private IMap<Long, PkgTaskStatusType> taskStatuses = new MockIMap<>();
  private IMap<Long, OperationPackageInfo> packageById = new MockIMap<>();
  private IMap<String, Long> packageIdByInn = new MockIMap<>();
  private IMap<String, CashOrder> cashOrderById = new MockIMap<>();

  private SrvCreateCashOrderRs createCashOrderRs1;
  private SrvCreateCashOrderRs createCashOrderRs2;
  private SrvUpdStCashOrderRs updStCashOrderRs;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockCache = new HazelcastMockCacheImpl(hazelcastMockServer);
    when(hazelcastMockServer.getTasksCardDepositByPackageId())
        .thenReturn(tasksCardDepositByPackageId);
    when(hazelcastMockServer.getTasksCardWithdrawByPackageId())
        .thenReturn(tasksCardWithdrawByPackageId);
    when(hazelcastMockServer.getTasksAccountDepositByPackageId())
        .thenReturn(tasksAccountDepositByPackageId);
    when(hazelcastMockServer.getTasksAccountWithdrawByPackageId())
        .thenReturn(tasksAccountWithdrawByPackageId);
    when(hazelcastMockServer.getTasksCheckbookIssuingByPackageId())
        .thenReturn(tasksCheckbookIssuingByPackageId);
    when(hazelcastMockServer.getTaskStatuses()).thenReturn(taskStatuses);
    when(hazelcastMockServer.getPackageById()).thenReturn(packageById);
    when(hazelcastMockServer.getPackageIdByInn()).thenReturn(packageIdByInn);
    when(hazelcastMockServer.getCashOrderById()).thenReturn(cashOrderById);

    createCashOrderRs1 = new SrvCreateCashOrderRs();
    createCashOrderRs1.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    createCashOrderRs1.getSrvCreateCashOrderRsMessage().setKO1(new KO1());
    createCashOrderRs1.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderId("101");
    createCashOrderRs1.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderStatus(
        CashOrderStatusType.CREATED);
    createCashOrderRs1.getSrvCreateCashOrderRsMessage().getKO1()
        .setAmount(new BigDecimal(50000));

    createCashOrderRs2 = new SrvCreateCashOrderRs();
    createCashOrderRs2.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    createCashOrderRs2.getSrvCreateCashOrderRsMessage().setKO1(new KO1());
    createCashOrderRs2.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderId("102");
    createCashOrderRs2.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderStatus(
        CashOrderStatusType.CREATED);
    createCashOrderRs2.getSrvCreateCashOrderRsMessage().getKO1()
        .setAmount(new BigDecimal(5000000));

    updStCashOrderRs = new SrvUpdStCashOrderRs();
    updStCashOrderRs.setSrvUpdCashOrderRsMessage(new SrvUpdCashOrderRsMessage());
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setCashOrderId("101");
    updStCashOrderRs.getSrvUpdCashOrderRsMessage().setCashOrderStatus(
        CashOrderStatusType.COMMITTED);
  }

  @Test
  public void testSaveTask() {
    // given
    TestTaskBody taskBody = new TestTaskBody(10L);

    // when
    mockCache.saveTaskCardDeposit(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCardDepositByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskCardWithdraw(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCardWithdrawByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCardWithdrawByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskAccountDeposit(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksAccountDepositByPackageId.containsKey(1L));
    Assert.assertTrue(tasksAccountDepositByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskAccountWithdraw(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksAccountWithdrawByPackageId.containsKey(1L));
    Assert.assertTrue(tasksAccountWithdrawByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskCheckbookIssuing(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCheckbookIssuingByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCheckbookIssuingByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));
  }

  @Test
  public void testSaveTaskStatus() {
    // when
    mockCache.saveTaskStatus(1L, PkgTaskStatusType.ACTIVE);
    //then
    Assert.assertTrue(taskStatuses.containsKey(1L));
    Assert.assertEquals(taskStatuses.get(1L), PkgTaskStatusType.ACTIVE);
  }

  @Test
  public void testCreatePackage() {
    // when
    Long packageId = mockCache.checkPackage(INN);
    // then
    Assert.assertNull(packageId);

    // when
    OperationPackageInfo packageInfo = mockCache.createPackage(INN, "12345", "Sidorov_SS");
    // then
    Assert.assertNotNull(packageInfo.getId());

    // when
    Long packageId2 = mockCache.checkPackage(INN);
    // then
    Assert.assertEquals(packageInfo.getId(), packageId2);

    // when
    OperationPackageInfo packageInfo2 = mockCache.createPackage(INN, "12345", "Sidorov_SS");
    // then
    Assert.assertNotEquals(packageInfo.getId(), packageInfo2.getId());
  }

  @Test
  public void testSearchTaskCardDeposit() {
    // when
    Map<Long, List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>> resultMap =
        mockCache.searchTasksCardDeposit(null, null, null, null, null);
    // then
    Assert.assertNotNull(resultMap);
    Assert.assertTrue(resultMap.isEmpty());
  }

  @Test
  public void testCreateCashOrder() {
    // when
    mockCache.createCashOrder(createCashOrderRs1, USER_LOGIN);
    // then
    Assert.assertNotNull(cashOrderById);
    Assert.assertNotNull(cashOrderById.get("101"));
    Assert.assertEquals(cashOrderById.get("101").getUserLogin(), USER_LOGIN);
  }

  @Test
  public void testUpdateStatusCashOrder() {
    // when
    mockCache.createCashOrder(createCashOrderRs1, USER_LOGIN);

    // then
    Assert.assertNotNull(cashOrderById);
    Assert.assertNotNull(cashOrderById.get("101"));
    Assert.assertEquals(cashOrderById.get("101").getCashOrderStatus(), CashOrderStatus.CREATED);

    // when
    mockCache.updateStatusCashOrder(updStCashOrderRs);

    // then
    Assert.assertEquals(cashOrderById.size(), 1);
    Assert.assertNotNull(cashOrderById.get("101"));
    Assert.assertEquals(cashOrderById.get("101").getCashOrderStatus(), CashOrderStatus.COMMITTED);
  }

  @Test
  public void testCheckCashOrdersLimitByUser() {
    // when
    mockCache.createCashOrder(createCashOrderRs1, USER_LOGIN);
    LimitStatusType limitStatusType = mockCache.checkCashOrdersLimitByUser(USER_LOGIN);
    // then
    Assert.assertEquals(limitStatusType, LimitStatusType.LIMIT_PASSED);

    // when
    mockCache.createCashOrder(createCashOrderRs2, USER_LOGIN);
    LimitStatusType limitStatusType2 = mockCache.checkCashOrdersLimitByUser(USER_LOGIN);
    // then
    Assert.assertEquals(limitStatusType2, LimitStatusType.LIMIT_ERROR);
  }

}
