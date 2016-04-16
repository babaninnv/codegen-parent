package ru.babaninnv.codegen.configurations;

import com.google.common.io.Closer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.babaninnv.codegen.utils.PropertyConstants;

/**
 * Created by NikitaRed on 09.04.2016.
 */
public class ApplicationConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

  private Map<String, Object> properties = new HashMap<>();

  public void load() {
    String configurationPath = System.getProperty(PropertyConstants.CONFIGURATION_PATH);
    
    File configurationFile = null;
    FileInputStream fileInputStream = null;
    Closer closer = Closer.create();
    
    try {
      configurationFile = new File(configurationPath, PropertyConstants.APPLICATION_YAML_FILE);
      
      fileInputStream = new FileInputStream(configurationFile);
      closer.register(fileInputStream);
      Yaml yaml = new Yaml();
      Map<String, Object> load = (Map<String, Object>) yaml.load(fileInputStream);
      properties.putAll(load);

    } catch (FileNotFoundException e) {
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
