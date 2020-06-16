package ru.philit.ufs.config;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import ru.philit.ufs.config.property.JndiEsbProperties;

/**
 * Конфигуратор JMS-клиентов доступа к КСШ.
 */
@Configuration
@EnableJms
@ComponentScan(basePackages = {"ru.philit.ufs.esb.mock.client"})
public class JmsConfig {

  private final String connectionFactoryJndiName;

  public JmsConfig(JndiEsbProperties properties) {
    this.connectionFactoryJndiName = properties.getConnectionFactory();
  }

  @Bean
  public ConnectionFactory esbConnectionFactory() throws NamingException {
    return (ConnectionFactory) new InitialContext().lookup(connectionFactoryJndiName);
  }

  /**
   * ContainerFactory для прослушивателя JMS сообщений.
   */
  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws NamingException {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(esbConnectionFactory());
    factory.setConcurrency("1-1");
    return factory;
  }

  @Bean
  public JmsTemplate jmsTemplate() throws NamingException {
    return new JmsTemplate(esbConnectionFactory());
  }
}
