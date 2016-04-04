package ru.babaninnv.codegen.plugin.templator.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommand;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommandImpl;
import ru.babaninnv.codegen.plugin.templator.commands.implementation.CommandImplementation;
import ru.babaninnv.codegen.plugin.templator.commands.implementation.ListCommandImplementationImpl;
import ru.babaninnv.codegen.plugin.templator.commands.implementation.MakeCommandImplementationImpl;
import ru.babaninnv.codegen.plugin.templator.commands.implementation.ReloadCommandImplementationImpl;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrarImpl;
import ru.babaninnv.codegen.plugin.templator.utils.Constants;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;
import ru.babaninnv.codegen.plugin.templator.utils.TemplateClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NikitaRed on 03.04.2016.
 */
public class ContextConfiguration {

  @Bean
  public PluginConfiguration pluginConfiguration() {

    Map<String, Object> properties = new HashMap<>();
    properties.put(Constants.TEMPLATES_SOURCES_FOLDER, "templates/src/main/java");
    properties.put(Constants.TEMPLATES_CLASSES_FOLDER, "templates/build/result-templates-classes");

    PluginConfiguration pluginConfiguration = new PluginConfiguration();
    pluginConfiguration.load(properties);
    return pluginConfiguration;
  }

  @Bean
  public TemplateRegistrar templateRegistrar() {
    TemplateRegistrarImpl templateRegistrar = new TemplateRegistrarImpl();
    return templateRegistrar;
  }

  @Bean
  public TemplateCommand templateCommand() {
    return new TemplateCommandImpl(templateRegistrar());
  }

  @Bean
  public CommandImplementation listCommandImplementation() {
    return new ListCommandImplementationImpl();
  }

  @Bean
  public CommandImplementation makeCommandImplementation() {
    return new MakeCommandImplementationImpl();
  }

  @Bean
  public CommandImplementation reloadCommandImplementation() {
    return new ReloadCommandImplementationImpl();
  }

  @Bean
  public TemplateClassUtils templateClassUtils() {
    return new TemplateClassUtils();
  }
}
