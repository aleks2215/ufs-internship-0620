package ru.philit.ufs.esb.mock.client;

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
import org.springframework.util.StringUtils;
import ru.philit.ufs.config.property.JndiEsbProperties;
import ru.philit.ufs.esb.MessageProcessor;

/**
 * Клиент обращений к очередям КСШ для Mock Мастер-систем Банка.
 */
@Service
public class EsbClient {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final JmsTemplate jmsTemplate;
  private final String outQueueName;

  private List<MessageProcessor> messageProcessors;

  @Autowired
  public EsbClient(JmsTemplate jmsTemplate, JndiEsbProperties properties) {
    this.jmsTemplate = jmsTemplate;
    this.outQueueName = properties.getQueue().getOut();
  }

  @PostConstruct
  public void init() {
    messageProcessors = new ArrayList<>();
    logger.info("{} started", this.getClass().getSimpleName());
  }

  public void addMessageProcessor(MessageProcessor processor) {
    messageProcessors.add(processor);
  }

  /**
   * Посылает сообщение в очередь.
   *
   * @param message xml сообщение.
   */
  public void sendMessage(final String message) {
    MessageCreator messageCreator = new MessageCreator() {
      @Override
      public javax.jms.Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(message);
      }
    };
    jmsTemplate.send(outQueueName, messageCreator);
  }

  /**
   * Прослушиватель собщений от ЕФС к Мастер-системам через КСШ.
   */
  @JmsListener(destination = "${jndi.esb.queue.in}")
  public void receiveMessage(final Message<String> message) {
    if (!StringUtils.isEmpty(message.getPayload())) {
      boolean processed = false;
      for (MessageProcessor processor : messageProcessors) {
        processed = processor.processMessage(message.getPayload());
        if (processed) {
          break;
        }
      }
      if (!processed) {
        logger.error("Message is not processed: " + message.getPayload());
      }
    } else {
      logger.error("Message is empty");
    }
  }
}
