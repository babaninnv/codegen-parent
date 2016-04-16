package ru.babaninnv.codegen.plugin.templator.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.babaninnv.codegen.plugin.templator.utils.Constants;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;

import java.util.HashMap;
import java.util.Map;

@Profile("test")
public class BundleContextConfigurationTest {

  @Bean
  public PluginConfiguration pluginConfiguration() {

    Map<String, Object> properties = new HashMap<>();

    properties.put(Constants.TEMPLATES_CONFIG_FILENAME, "../../templates/src/main/resources/templates.yaml");
    properties.put(Constants.TEMPLATES_JAVA_SOURCES_FOLDER, "../../templates/src/main/java");
    properties.put(Constants.TEMPLATES_GROOVY_SOURCES_FOLDER, "../../templates/src/main/groovy");
    properties.put(Constants.TEMPLATES_RESOURCES_FOLDER, "../../templates/src/main/resources");
    properties.put(Constants.TEMPLATES_CLASSES_FOLDER, "../../templates/build/result-templates-classes");

    PluginConfiguration pluginConfiguration = new PluginConfiguration();
    pluginConfiguration.load(properties);
    return pluginConfiguration;
  }
}