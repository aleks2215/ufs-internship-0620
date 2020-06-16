package ru.philit.ufs.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.support.GenericMessage;
import ru.philit.ufs.config.property.JndiEsbProperties;
import ru.philit.ufs.esb.ReceiveMessageListener;
import ru.philit.ufs.esb.client.EsbClient;
import ru.philit.ufs.model.TestData;

public class EsbClientTest {

  @Mock
  private Session session;
  @Mock
  private JmsTemplate jmsTemplate;
  @Mock
  private TextMessage textMessage;

  private EsbClient esbClient;

  private String textMessageString = null;
  private String receivedMessage = null;
  private String sentMessage = null;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    esbClient = new EsbClient(jmsTemplate, new JndiEsbProperties());

    try {
      doAnswer(new Answer() {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
          textMessageString = (String) invocationOnMock.getArguments()[0];
          return null;
        }
      }).when(textMessage).setText(anyString());

      doAnswer(new Answer() {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
          return textMessageString;
        }
      }).when(textMessage).getText();

      doAnswer(new Answer() {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
          textMessage.setText((String) invocationOnMock.getArguments()[0]);
          return textMessage;
        }
      }).when(session).createTextMessage(anyString());
    } catch (JMSException e) {
      // cannot be
    }

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        Message message = ((MessageCreator) invocationOnMock.getArguments()[1])
            .createMessage(session);
        if (message instanceof TextMessage) {
          sentMessage = ((TextMessage) message).getText();
        }
        return null;
      }
    }).when(jmsTemplate).send(anyString(), any(MessageCreator.class));

    esbClient.init();
    esbClient.addReceiveMessageListener(new ReceiveMessageListener() {
      @Override
      public void receiveMessage(String message) {
        receivedMessage = message;
      }
    });
  }

  @Test
  public void testSendMessage() {
    // when
    esbClient.sendMessage(TestData.ACCOUNT_RESPONSE_XML);
    // then
    Assert.assertEquals(sentMessage, TestData.ACCOUNT_RESPONSE_XML);
  }

  @Test
  public void testReceiveMessage() {
    // when
    esbClient.receiveMessage(new GenericMessage<>(TestData.ACCOUNT_RESPONSE_XML));
    // then
    Assert.assertEquals(receivedMessage, TestData.ACCOUNT_RESPONSE_XML);
  }

  @Test
  public void testReceiveMessage_Empty() {
    // when
    String emptyMessage = "";
    esbClient.receiveMessage(new GenericMessage<>(emptyMessage));
    // then
    Assert.assertNull(receivedMessage);
  }
}
