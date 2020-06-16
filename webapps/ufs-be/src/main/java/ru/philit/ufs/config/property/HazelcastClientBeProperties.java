package ru.philit.ufs.config.property;

import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Параметры клиента Hazelcast для бизнес-приложения.
 */
@Component
@ConfigurationProperties("hazelcast")
@Validated
@ToString(callSuper = true)
@Getter
@SuppressWarnings("WeakerAccess")
public class HazelcastClientBeProperties extends HazelcastClientProperties {

  @Valid
  private final Response response = new Response();

  @ToString
  @Getter
  @Setter
  public static class Response {

    /**
     * Response timeout in seconds.
     */
    private int timeout = 30;
  }

}
