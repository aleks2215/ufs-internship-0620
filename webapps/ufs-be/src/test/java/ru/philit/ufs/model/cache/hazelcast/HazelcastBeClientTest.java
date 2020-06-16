package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.philit.ufs.config.property.HazelcastClientBeProperties;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.common.LocalKey;
import ru.philit.ufs.model.entity.request.RequestType;

public class HazelcastBeClientTest {

  private static final String SESSION_ID = "444";
  private static final String CARD_NUMBER = "4894123569871254";
  private static final String FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";

  private final TestHazelcastInstanceFactory instanceFactory = new TestHazelcastInstanceFactory();

  private final IQueue<ExternalEntityRequest> requestQueue = new MockIQueue<>(5);
  private final IMap<ExternalEntityRequest, String> responseFlagMap = new MockIMap<>();

  private HazelcastBeClient hazelcastBeClient;

  /**
   * Подготовка объектов.
   */
  @Before
  public void setUp() throws Exception {
    hazelcastBeClient = new HazelcastBeClient(
        instanceFactory.newHazelcastInstance(), new HazelcastClientBeProperties()
    );
    ReflectionTestUtils.setField(hazelcastBeClient, "requestQueue", requestQueue);
    ReflectionTestUtils.setField(hazelcastBeClient, "responseFlagMap", responseFlagMap);
  }

  @After
  public void tearDown() throws Exception {
    Hazelcast.shutdownAll();
  }

  @Test
  public void testInit() throws Exception {
    // when
    hazelcastBeClient.init();
  }

  @Test
  public void testDestroy() throws Exception {
    // when
    hazelcastBeClient.destroy();
    hazelcastBeClient.getUserBySessionMap();
    hazelcastBeClient.getAuditedRequests();
    hazelcastBeClient.getLoggedEvents();
    hazelcastBeClient.getOperationTypesByRolesMap();
    hazelcastBeClient.getOperationTypeFavouritesByUserMap();
    hazelcastBeClient.getAccountByCardNumberMap();
    hazelcastBeClient.getLegalEntityByAccountMap();
    hazelcastBeClient.getAccountResiduesByAccountMap();
    hazelcastBeClient.getSeizuresByAccountMap();
    hazelcastBeClient.getPayOrdersCardIndex1ByAccountMap();
    hazelcastBeClient.getPayOrdersCardIndex2ByAccountMap();
    hazelcastBeClient.getCommissionByAccountOperationMap();
    hazelcastBeClient.getAccount20202ByWorkPlaceMap();
    hazelcastBeClient.getOvnByUidMap();
    hazelcastBeClient.getOvnsMap();
    hazelcastBeClient.getOperationPackageInfoMap();
    hazelcastBeClient.getOperationPackageMap();
    hazelcastBeClient.getOperationPackageResponseMap();
    hazelcastBeClient.getRepresentativeMap();
    hazelcastBeClient.getRepresentativeByCardNumberMap();
  }

  @Test
  public void testRequestExternalEntity() throws Exception {
    // when
    LocalKey<String> localKey = new LocalKey<>(SESSION_ID, CARD_NUMBER);
    // then
    Assert.assertFalse(
        hazelcastBeClient.requestExternalEntity(RequestType.ACCOUNT_BY_CARD_NUMBER, localKey)
    );
    requestQueue.clear();

    // when
    requestQueue.addItemListener(new ItemListener<ExternalEntityRequest>() {
      @Override
      public void itemAdded(ItemEvent<ExternalEntityRequest> itemEvent) {
        ExternalEntityRequest request = requestQueue.poll();
        responseFlagMap.put(request, FIX_UUID);
      }

      @Override
      public void itemRemoved(ItemEvent<ExternalEntityRequest> itemEvent) {
      }
    }, false);
    // then
    Assert.assertTrue(
        hazelcastBeClient.requestExternalEntity(RequestType.ACCOUNT_BY_CARD_NUMBER, localKey)
    );
  }
}
