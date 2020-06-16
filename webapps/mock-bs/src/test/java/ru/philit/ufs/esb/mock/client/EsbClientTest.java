package ru.philit.ufs.esb.mock.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.support.GenericMessage;
import ru.philit.ufs.config.property.JndiEsbProperties;
import ru.philit.ufs.esb.MessageProcessor;

public class EsbClientTest {

  @Mock
  private JmsTemplate jmsTemplate;

  private EsbClient esbClient;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    esbClient = new EsbClient(jmsTemplate, new JndiEsbProperties());
    esbClient.init();
  }

  @Test
  public void testInit() throws Exception {
    List<MessageProcessor> processors = getMessageProcessors();
    Assert.assertNotNull(processors);
    Assert.assertTrue(processors.isEmpty());
  }

  @Test
  public void testAddMessageProcessor() throws Exception {
    esbClient.addMessageProcessor(new MessageProcessor() {
      @Override
      public boolean processMessage(String message) {
        return false;
      }
    });

    List<MessageProcessor> processors = getMessageProcessors();
    Assert.assertNotNull(processors);
    Assert.assertEquals(processors.size(), 1);
  }

  @Test
  public void testSendMessage() throws Exception {
    doNothing().when(jmsTemplate).send(anyString(), any(MessageCreator.class));

    esbClient.sendMessage("Test message");

    verify(jmsTemplate, times(1)).send(anyString(), any(MessageCreator.class));
    verifyNoMoreInteractions(jmsTemplate);
  }

  @Test
  public void testReceiveMessageEks() throws Exception {
    esbClient.addMessageProcessor(new MessageProcessor() {
      @Override
      public boolean processMessage(String message) {
        return true;
      }
    });
    esbClient.receiveMessage(new GenericMessage<>("payload"));
  }

  @Test
  public void testReceiveMessageEks_NotProcessed() throws Exception {
    esbClient.addMessageProcessor(new MessageProcessor() {
      @Override
      public boolean processMessage(String message) {
        return false;
      }
    });
    esbClient.receiveMessage(new GenericMessage<>("payload"));
  }

  @Test
  public void testReceiveMessageEks_EmptyPayload() throws Exception {
    esbClient.addMessageProcessor(new MessageProcessor() {
      @Override
      public boolean processMessage(String message) {
        return true;
      }
    });
    esbClient.receiveMessage(new GenericMessage<>(""));
  }

  @SuppressWarnings("unchecked")
  private List<MessageProcessor> getMessageProcessors() throws Exception {
    Field field = EsbClient.class.getDeclaredField("messageProcessors");
    field.setAccessible(true);
    return (List<MessageProcessor>) field.get(esbClient);
  }
}
