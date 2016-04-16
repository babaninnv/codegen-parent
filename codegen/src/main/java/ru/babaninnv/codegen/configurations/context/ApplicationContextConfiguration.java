package ru.babaninnv.codegen.configurations.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.babaninnv.codegen.Console;
import ru.babaninnv.codegen.ConsoleDefault;
import ru.babaninnv.codegen.configurations.ApplicationConfiguration;
import ru.babaninnv.codegen.utils.FelixFrameworkUtils;

@Configuration
public class ApplicationContextConfiguration {

  @Bean
  public ApplicationConfiguration applicationConfiguration() {
    ApplicationConfiguration configuration = new ApplicationConfiguration();
    configuration.load();
    return configuration;
  }

  @Bean
  public FelixFrameworkUtils felixFrameworkUtils() {
    FelixFrameworkUtils felixFrameworkUtils = new FelixFrameworkUtils();
    return felixFrameworkUtils;
  }

  @Bean
  public Console console() {
    Console console = new ConsoleDefault();
    console.setConfiguration(applicationConfiguration());
    console.setFelixFrameworkUtils(felixFrameworkUtils());
    console.init();
    return console;
  }
}
