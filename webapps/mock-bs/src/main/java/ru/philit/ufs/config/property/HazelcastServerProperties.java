package ru.philit.ufs.config.property;

import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Параметры сервера Hazelcast.
 */
@Component
@ConfigurationProperties("hazelcast")
@Validated
@ToString(callSuper = true)
@Getter
@SuppressWarnings("WeakerAccess")
public class HazelcastServerProperties extends HazelcastClientProperties {

  @Valid
  private final Instance instance = new Instance();

  @ToString
  @Getter
  @Setter
  public static class Instance {

    /**
     * External host name for instance node.
     */
    private String host;
    /**
     * Port number for instance node.
     */
    private int port = 5702;
  }

}
