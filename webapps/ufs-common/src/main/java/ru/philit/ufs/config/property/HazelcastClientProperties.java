package ru.philit.ufs.config.property;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * Параметры клиента Hazelcast.
 */
@Component
@ConfigurationProperties("hazelcast")
@Validated
@ToString
@Getter
@SuppressWarnings("WeakerAccess")
public class HazelcastClientProperties {

  private static final String SHOULD_BE_DEFINED = "should be defined";
  private static final String SHOULD_MATCH = "should match \"{regexp}\"";

  @Valid
  private final Group group = new Group();
  @Valid
  private final Members members = new Members();

  public List<String> getMembersAddresses() {
    return Arrays.asList(StringUtils.commaDelimitedListToStringArray(members.getAddresses()));
  }

  @ToString
  @Getter
  @Setter
  public static class Group {

    /**
     * Name for group of servers.
     */
    @NotBlank(message = SHOULD_BE_DEFINED)
    private String name;
    /**
     * Password for group of servers.
     */
    @NotBlank(message = SHOULD_BE_DEFINED)
    private String password;
  }

  @ToString
  @Getter
  @Setter
  public static class Members {

    /**
     * Addresses of servers or other nodes of cluster (delimited by comma).
     */
    @Pattern(regexp = "^(([\\w.-]+):(\\d+)(,([\\w.-]+):(\\d+))*)?$", message = SHOULD_MATCH)
    private String addresses;
  }

}
