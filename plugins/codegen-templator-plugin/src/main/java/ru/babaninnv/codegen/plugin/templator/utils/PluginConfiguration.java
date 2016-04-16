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

  private Map<String, Object> properties = new HashMap<>();

  public void load() {
    Yaml yaml = new Yaml();
    properties = (Map<String, Object>) yaml.load(PluginConfiguration.class.getClassLoader().getResourceAsStream(Constants.PLUGIN_PROPERTIES_FILE));
    properties.put("java.class.path", System.getProperty("java.class.path"));
    properties.put("app.home", new File("").getAbsolutePath());
    LOG.debug("#load> Set app.home property: {}", properties.get("app.home"));
  }

  public void load(Map<String, Object> properties) {
    this.properties = new HashMap<>();
    this.properties.putAll(properties);
    this.properties.put("java.class.path", System.getProperty("java.class.path"));
<<<<<<< HEAD
    this.properties.put("app.home", new File(appHome).getAbsolutePath());

    LOG.debug("properties: {}", this.properties.toString());
=======
    this.properties.put("app.home", new File("../").getAbsolutePath());
    LOG.debug("#load> Set app.home property: {}", this.properties.get("app.home"));
>>>>>>> branch 'master' of git@repo.babaninfv.ru:nikitared/codegen-parent.git
  }
  public String getString(String name) {
    return (String) properties.get(name);
  }
}
