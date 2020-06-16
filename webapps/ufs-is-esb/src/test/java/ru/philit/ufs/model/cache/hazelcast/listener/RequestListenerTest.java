package ru.philit.ufs.model.cache.hazelcast.listener;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import com.hazelcast.core.IQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.philit.ufs.esb.service.EsbService;
import ru.philit.ufs.model.TestData;
import ru.philit.ufs.model.cache.hazelcast.HazelcastIsEsbClient;
import ru.philit.ufs.model.cache.hazelcast.MockIQueue;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;

public class RequestListenerTest {

  private final IQueue<ExternalEntityRequest> requestQueue = new MockIQueue<>(5);

  @Mock
  private HazelcastIsEsbClient hazelcastIsEsbClient;
  @Mock
  private EsbService esbService;

  private RequestListener requestListener;

  private TestData testData;
  private ExternalEntityRequest sentEntityRequest;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    testData = new TestData();
    MockitoAnnotations.initMocks(this);
    requestListener = new RequestListener(hazelcastIsEsbClient, esbService);
    when(hazelcastIsEsbClient.getRequestQueue()).thenReturn(requestQueue);
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        sentEntityRequest = (ExternalEntityRequest) invocationOnMock.getArguments()[0];
        return null;
      }
    }).when(esbService).sendRequest(any(ExternalEntityRequest.class));
  }

  @Test
  public void testItemAdded() {
    // given
    requestListener.init();
    // when
    requestQueue.add(testData.getAccountByCardNumberRequest());
    // then
    Assert.assertEquals(sentEntityRequest, testData.getAccountByCardNumberRequest());
  }
}
