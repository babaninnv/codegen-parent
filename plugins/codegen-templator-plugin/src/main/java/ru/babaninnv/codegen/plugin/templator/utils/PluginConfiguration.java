package ru.babaninnv.codegen.plugin.templator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class PluginConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(PluginConfiguration.class);

  private static Map<String, Object> properties = new HashMap<>();
  private static TemplateClassLoader currentTemplateClassLoader;

  public static void load() {
    Yaml yaml = new Yaml();
    properties = (Map<String, Object>) yaml.load(PluginConfiguration.class.getClassLoader().getResourceAsStream(Constants.PLUGIN_PROPERTIES_FILE));
    properties.put("java.class.path", System.getProperty("java.class.path"));

    properties.put("app.home", new File("../..").getAbsolutePath());
    LOG.debug("#load> Set app.home property: {}", properties.get("app.home"));
  }

  public static Object get(String name) {
    return properties.get(name);
  }

  public static String getString(String name) {
    return (String) properties.get(name);
  }

  public static List<String> getStringList(String name) {
    return (List<String>) properties.get(name);
  }

  public static void setCurrentTemplateClassLoader(TemplateClassLoader currentTemplateClassLoader) {
    PluginConfiguration.currentTemplateClassLoader = currentTemplateClassLoader;
  }

  public static TemplateClassLoader getCurrentTemplateClassLoader() {
    return currentTemplateClassLoader;
  }
}
