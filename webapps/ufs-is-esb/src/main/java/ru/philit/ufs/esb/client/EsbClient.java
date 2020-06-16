package ru.philit.ufs.esb.client;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.philit.ufs.config.property.JndiEsbProperties;
import ru.philit.ufs.esb.ReceiveMessageListener;

/**
 * Клиент обращений к очередям КСШ для системы ЕКС.
 */
@Service
public class EsbClient {

  private static final Logger logger = LoggerFactory.getLogger(EsbClient.class);

  private final JmsTemplate jmsTemplate;

  private final String inQueueName;

  private List<ReceiveMessageListener> receiveMessageListeners;

  @Autowired
  public EsbClient(JmsTemplate jmsTemplate, JndiEsbProperties properties) {
    this.jmsTemplate = jmsTemplate;
    this.inQueueName = properties.getQueue().getIn();
  }

  @PostConstruct
  public void init() {
    receiveMessageListeners = new ArrayList<>();
    logger.info("{} started", this.getClass().getSimpleName());
  }

  public void addReceiveMessageListener(ReceiveMessageListener listener) {
    receiveMessageListeners.add(listener);
  }

  /**
   * Посылает сообщение в очередь.
   *
   * @param message объект сообщения согласно xsd.
   */
  public void sendMessage(final String message) {
    MessageCreator messageCreator = new MessageCreator() {
      @Override
      public javax.jms.Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(message);
      }
    };
    jmsTemplate.send(inQueueName, messageCreator);
  }

  /**
   * Прослушиватель собщений от ЕКС к ЕФС через КСШ.
   */
  @JmsListener(destination = "${jndi.esb.queue.out}")
  public void receiveMessage(final Message<String> message) {
    if (message.getPayload() != null && !message.getPayload().isEmpty()) {
      for (ReceiveMessageListener listener : receiveMessageListeners) {
        listener.receiveMessage(message.getPayload());
      }
    } else {
      logger.error("Message is empty");
    }
  }
}
