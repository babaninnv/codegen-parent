package ru.babaninnv.codegen.plugin.templator.utils;

import org.apache.commons.lang3.StringUtils;
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

  private Map<String, Object> properties = new HashMap<>();

  public void load(Map<String, Object> properties) {

    String appHome = StringUtils.defaultString(System.getProperty("app.home"));

    this.properties = new HashMap<>();
    this.properties.putAll(properties);
    this.properties.put("java.class.path", System.getProperty("java.class.path"));
    this.properties.put("app.home", new File(appHome).getAbsolutePath());

    LOG.debug("properties: {}", this.properties.toString());
  }
  public String getString(String name) {
    return (String) properties.get(name);
  }
}
