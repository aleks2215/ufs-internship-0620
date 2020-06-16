package ru.philit.ufs.config.property;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Параметры проекта.
 */
@Component
@ConfigurationProperties("project")
@Validated
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ProjectProperties {

  /**
   * Название проекта.
   */
  @NotNull
  private String name;
  /**
   * Версия проекта.
   */
  @NotNull
  private String version;

}
