package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.request.RequestType;

public class HazelcastIsEsbClientTest {

  private static final String FIX_UUID = "a55ed416-3976-43f7-912c-4c26ca79e969";

  private final TestHazelcastInstanceFactory instanceFactory = new TestHazelcastInstanceFactory();

  private final IMap<String, ExternalEntityRequest> requestMap = new MockIMap<>();
  private final IQueue<ExternalEntityRequest> requestQueue = new MockIQueue<>(5);
  private final IQueue<ExternalEntity> responseQueue = new MockIQueue<>(5);

  private HazelcastIsEsbClient hazelcastIsEsbClient;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    final HazelcastInstance instance = instanceFactory.newHazelcastInstance();
    hazelcastIsEsbClient = new HazelcastIsEsbClient(instance);
    ReflectionTestUtils.setField(hazelcastIsEsbClient, "requestQueue", requestQueue);
    ReflectionTestUtils.setField(hazelcastIsEsbClient, "requestMap", requestMap);
    ReflectionTestUtils.setField(hazelcastIsEsbClient, "responseQueue", responseQueue);
  }

  @After
  public void shutdown() {
    Hazelcast.shutdownAll();
  }

  @Test
  public void testInit() {
    // when
    hazelcastIsEsbClient.init();
  }

  @Test
  public void testDestroy() {
    // when
    hazelcastIsEsbClient.destroy();
    hazelcastIsEsbClient.getRequestQueue();
  }

  @Test
  public void testPutRequest() {
    // given
    ExternalEntityRequest externalEntityRequest = new ExternalEntityRequest();
    externalEntityRequest.setSessionId("444");
    externalEntityRequest.setEntityType(RequestType.ACCOUNT_BY_CARD_NUMBER);
    externalEntityRequest.setRequestData("111");
    // when
    hazelcastIsEsbClient.putRequest(FIX_UUID, externalEntityRequest);
    // then
    Assert.assertTrue(requestMap.containsKey(FIX_UUID));
    Assert.assertEquals(requestMap.get(FIX_UUID), externalEntityRequest);
  }

  @Test
  public void testPushResponse() {
    // given
    ExternalEntity externalEntity = new ExternalEntity();
    externalEntity.setReceiveDate(new Date());
    externalEntity.setRequestUid(FIX_UUID);
    // when
    hazelcastIsEsbClient.pushResponse(externalEntity);
    // then
    Assert.assertFalse(responseQueue.isEmpty());
    Assert.assertEquals(responseQueue.poll(), externalEntity);
  }
}
