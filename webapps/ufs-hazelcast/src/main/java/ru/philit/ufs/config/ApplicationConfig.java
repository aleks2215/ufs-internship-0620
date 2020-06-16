package ru.philit.ufs.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Класс приложения Spring Boot.
 */
@SpringBootApplication(scanBasePackages = {"ru.philit.ufs"})
public class ApplicationConfig extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ApplicationConfig.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
  }
}
