package ru.babaninnv.codegen.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Closer;

import ru.babaninnv.codegen.utils.PropertyConstants;

/**
 * Created by NikitaRed on 09.04.2016.
 */
public class ApplicationConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

  private Map<String, Object> properties = new HashMap<>();

  public void load() {
    String configurationPath = System.getProperty(PropertyConstants.CONFIGURATION_PATH);
    File configurationFile = new File(configurationPath, PropertyConstants.APPLICATION_YAML_FILE);
    FileInputStream fileInputStream = null;
    Closer closer = Closer.create();
    try {
      fileInputStream = new FileInputStream(configurationFile);
      closer.register(fileInputStream);
      Yaml yaml = new Yaml();

      String configString = IOUtils.toString(fileInputStream);

      JexlContext context = new MapContext();
      context.set("APP_HOME", System.getProperty(PropertyConstants.APP_HOME));
      JxltEngine engine = new JexlBuilder().create().createJxltEngine();
      JxltEngine.Expression expr = engine.createExpression(configString);

      Map<String, Object> load = (Map<String, Object>) yaml.load(expr.evaluate(context).toString());
      properties.putAll(load);
      properties.put(PropertyConstants.APP_HOME, StringUtils.defaultString(System.getProperty(PropertyConstants.APP_HOME), ""));
      LOG.info("APP_HOME={}", System.getProperty(PropertyConstants.APP_HOME));

    } catch (Exception e) {
      LOG.error(e.getMessage().concat(". File: ").concat(configurationFile.getAbsolutePath()), e);
    } finally {
      try {
        closer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String getString(String name) {
    return (String) properties.get(name);
  }

  public List<String> getStringList(String name) {
    return (List<String>) properties.get(name);
  }
}
