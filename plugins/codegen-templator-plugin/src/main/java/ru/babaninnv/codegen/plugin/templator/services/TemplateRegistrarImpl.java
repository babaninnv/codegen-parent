package ru.babaninnv.codegen.plugin.templator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.templates.Template;
import ru.babaninnv.codegen.plugin.templator.utils.Constants;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;
import ru.babaninnv.codegen.plugin.templator.utils.TemplateYamlConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by NikitaRed on 30.03.2016.
 */
@Component
public class TemplateRegistrarImpl implements TemplateRegistrar {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateRegistrarImpl.class);

  private List<TemplateDefinition> templates;

  @Autowired
  private PluginConfiguration configuration;

  @Override
  public void loadTemplatesConfiguration() {
    try {
      File templatesConfigurationFile = new File(configuration.getString("app.home"), configuration.getString(Constants.TEMPLATES_CONFIG_FILENAME));
      parseYaml(templatesConfigurationFile.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public TemplateDefinition getByName(String name) {
    for (TemplateDefinition templateDefinition : templates) {
      if (templateDefinition.getName().equals(name)) return templateDefinition;
    }
    return null;
  }

  @Override
  public void addTemplateDefinition(TemplateDefinition templateDefinition) {

    if (templates == null) templates = new ArrayList<>();

    int templateDefinitionPos = templates.indexOf(templateDefinition);

    if (templateDefinitionPos > -1) {
      TemplateDefinition oldTemplateDefinition = templates.get(templateDefinitionPos);
      Template templateObject = templateDefinition.getTemplate();
      oldTemplateDefinition.setTemplate(templateObject);
      return;
    } else {
      templates.add(templateDefinition);
    }
  }

  @Override
  public List<TemplateDefinition> getTemplates() {
    return templates;
  }

  private void parseYaml(Path yamlFile) throws IOException {
    Yaml yaml = new Yaml();
    Map<String, Object> load = (Map<String, Object>) yaml.load(Files.newInputStream(yamlFile));

    List<Object> templatesSource = (List<Object>) load.get(TemplateYamlConstants.TEMPLATES);

    if (templatesSource != null && !templatesSource.isEmpty()) {

      if (templates == null) templates = new ArrayList<>();
      templates.clear();

      for (Object template : templatesSource) {
        Map<String, Object> templateMap = (Map<String, Object>) template;
        templateMap.forEach((name, o) -> {
          Map<String, Object> props = (Map<String, Object>) o;

          TemplateDefinition templateDefinition = new TemplateDefinition(name);
          templateDefinition.setClassName((String) props.get("classname"));

          templates.add(templateDefinition);
        });
      }
    }

    assert templates != null;
  }
}
