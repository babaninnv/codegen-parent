package ru.babaninnv.codegen.configurations;

import com.google.common.io.Closer;

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
  private Map<String, Object> properties = new HashMap<>();

  public void load() {
    String configurationPath = System.getProperty(PropertyConstants.CONFIGURATION_PATH);

    FileInputStream fileInputStream = null;
    Closer closer = Closer.create();
    try {
      fileInputStream = new FileInputStream(new File(configurationPath, PropertyConstants.APPLICATION_YAML_FILE));
      closer.register(fileInputStream);
      Yaml yaml = new Yaml();
      Map<String, Object> load = (Map<String, Object>) yaml.load(fileInputStream);

      List<String> packages = (List<String>) properties.get(PropertyConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
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
