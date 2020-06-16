package ru.philit.ufs.web.controller;

import static org.mockito.Mockito.doAnswer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.philit.ufs.model.server.HazelcastServer;

public class HazelcastServerControllerTest {

  @Mock
  private HazelcastServer hazelcastServer;

  private HazelcastServerController controller;

  private boolean isShutdown = false;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    controller = new HazelcastServerController(hazelcastServer);
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        isShutdown = true;
        return null;
      }
    }).when(hazelcastServer).shutdown();
  }

  @Test
  public void testShutdownServer() {
    controller.shutdownServer();
    Assert.assertTrue(isShutdown);
  }
}
