package ru.philit.ufs.model.server;

import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REQUEST_MAP;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceNotActiveException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import ru.philit.ufs.config.property.HazelcastServerProperties;

public class HazelcastServerTest {

  private HazelcastServer hazelcastServer;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    hazelcastServer = new HazelcastServer(getServerProperties());
  }

  private HazelcastServerProperties getServerProperties() {
    HazelcastServerProperties properties = new HazelcastServerProperties();
    properties.getGroup().setName("testGroup");
    properties.getGroup().setPassword("test");
    properties.getInstance().setHost("localhost");
    return properties;
  }

  @Test
  public void testInit() {
    // when
    hazelcastServer.init();
    // then
    Assert.assertNotNull(hazelcastServer.getRequestQueue());
    Assert.assertNotNull(hazelcastServer.getResponseQueue());
    Assert.assertNotNull(hazelcastServer.getRequestMap());
    Assert.assertNotNull(hazelcastServer.getResponseFlagMap());
    Assert.assertNotNull(hazelcastServer.getUserBySessionMap());
    Assert.assertNotNull(hazelcastServer.getAuditedRequests());
    Assert.assertNotNull(hazelcastServer.getLoggedEvents());
    Assert.assertNotNull(hazelcastServer.getAccountByIdMap());
    Assert.assertNotNull(hazelcastServer.getAccountByCardNumberMap());
    Assert.assertNotNull(hazelcastServer.getAccountResiduesByIdMap());
    Assert.assertNotNull(hazelcastServer.getAccountsByLegalEntityMap());
    Assert.assertNotNull(hazelcastServer.getLegalEntityByAccountMap());
    Assert.assertNotNull(hazelcastServer.getSeizuresByAccountMap());
    Assert.assertNotNull(hazelcastServer.getPayOrdersCardIndex1ByAccountMap());
    Assert.assertNotNull(hazelcastServer.getPayOrdersCardIndex2ByAccountMap());
    Assert.assertNotNull(hazelcastServer.getCommissionByAccountOperationMap());
    Assert.assertNotNull(hazelcastServer.getCheckFraudByAccountOperationMap());
    Assert.assertNotNull(hazelcastServer.getOperationPackageMap());
    Assert.assertNotNull(hazelcastServer.getOperationPackageInfoMap());
    Assert.assertNotNull(hazelcastServer.getOperationPackageResponseMap());
    Assert.assertNotNull(hazelcastServer.getOvnByUidMap());
    Assert.assertNotNull(hazelcastServer.getOvnsMap());
    Assert.assertNotNull(hazelcastServer.getOvnResponseMap());
    Assert.assertNotNull(hazelcastServer.getAccount20202ByWorkPlaceMap());
    Assert.assertNotNull(hazelcastServer.getOperationTypesByRolesMap());
    Assert.assertNotNull(hazelcastServer.getRepresentativeMap());
    Assert.assertNotNull(hazelcastServer.getOperationTypeFavouritesByUserMap());
    Assert.assertNotNull(hazelcastServer.getRepresentativeByCardMap());
    Assert.assertNotNull(hazelcastServer.getOperatorByUserMap());
    Assert.assertNotNull(hazelcastServer.getCashSymbolsMap());
    hazelcastServer.shutdown();
  }

  @Test(expected = HazelcastInstanceNotActiveException.class)
  public void testDestroy() {
    // given
    hazelcastServer.init();
    // when
    hazelcastServer.destroy();
    // then
    HazelcastInstance instance =
        (HazelcastInstance) Whitebox.getInternalState(hazelcastServer, "instance");
    instance.getMap(REQUEST_MAP);
  }
}
