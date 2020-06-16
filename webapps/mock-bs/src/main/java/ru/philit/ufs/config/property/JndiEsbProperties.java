package ru.philit.ufs.config.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Параметры JNDI для КСШ.
 */
@Component
@ConfigurationProperties(prefix = "jndi.esb")
@Validated
@ToString
@Getter
@Setter
@SuppressWarnings("WeakerAccess")
public class JndiEsbProperties {

  private static final String SHOULD_BE_DEFINED = "should be defined";

  private final Queue queue = new Queue();

  /**
   * JNDI name for connection factory.
   */
  @NotBlank(message = SHOULD_BE_DEFINED)
  private String connectionFactory;

  @ToString
  @Getter
  @Setter
  public static class Queue {

    /**
     * JNDI name for income queue.
     */
    @NotBlank(message = SHOULD_BE_DEFINED)
    private String in;
    /**
     * JNDI name for outcome queue.
     */
    @NotBlank(message = SHOULD_BE_DEFINED)
    private String out;
  }

}
