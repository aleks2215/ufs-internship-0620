package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceNotActiveException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import ru.philit.ufs.config.property.HazelcastServerProperties;

public class HazelcastMockServerTest {

  private HazelcastMockServer hazelcastMockServer;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    hazelcastMockServer = new HazelcastMockServer(getServerProperties());
  }

  private HazelcastServerProperties getServerProperties() {
    HazelcastServerProperties properties = new HazelcastServerProperties();
    properties.getGroup().setName("mockGroup");
    properties.getGroup().setPassword("mock");
    properties.getInstance().setHost("localhost");
    return properties;
  }

  @Test
  public void testInit() {
    // when
    hazelcastMockServer.init();
    // then
    Assert.assertNotNull(hazelcastMockServer.getTasksCardDepositByPackageId());
    Assert.assertNotNull(hazelcastMockServer.getTasksCardWithdrawByPackageId());
    Assert.assertNotNull(hazelcastMockServer.getTasksAccountDepositByPackageId());
    Assert.assertNotNull(hazelcastMockServer.getTasksAccountWithdrawByPackageId());
    Assert.assertNotNull(hazelcastMockServer.getTasksCheckbookIssuingByPackageId());
    Assert.assertNotNull(hazelcastMockServer.getTaskStatuses());
    Assert.assertNotNull(hazelcastMockServer.getPackageById());
    Assert.assertNotNull(hazelcastMockServer.getPackageIdByInn());
    Assert.assertNotNull(hazelcastMockServer.getCashOrderById());
    hazelcastMockServer.destroy();
  }

  @Test(expected = HazelcastInstanceNotActiveException.class)
  public void testDestroy() {
    // given
    hazelcastMockServer.init();
    // when
    hazelcastMockServer.destroy();
    // then
    HazelcastInstance instance =
        (HazelcastInstance) Whitebox.getInternalState(hazelcastMockServer, "instance");
    instance.getMap("someMap");
  }

  @Test
  public void testDestroy2() {
    // when
    hazelcastMockServer.destroy();
    // then
    // no happen
  }
}
