package ru.philit.ufs.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.philit.ufs.config.property.HazelcastClientBeProperties;

/**
 * Конфигуратор Hazelcast клиента.
 */
@Configuration
public class HazelcastConfig {

  private final List<String> membersAddresses;
  private final String groupName;
  private final String groupPassword;

  /**
   * Конструктор бина конфига.
   */
  public HazelcastConfig(HazelcastClientBeProperties properties) {
    membersAddresses = properties.getMembersAddresses();
    groupName = properties.getGroup().getName();
    groupPassword = properties.getGroup().getPassword();
  }

  @Bean
  public HazelcastInstance hazelcastClient() {
    return HazelcastClient.newHazelcastClient(getConfig());
  }

  private ClientConfig getConfig() {
    ClientConfig config = new ClientConfig();
    for (String serverAddress : membersAddresses) {
      config.getNetworkConfig().addAddress(serverAddress);
    }
    config.getGroupConfig().setName(groupName).setPassword(groupPassword);
    return config;
  }
}
