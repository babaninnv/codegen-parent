package ru.babaninnv.codegen.plugin.templator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.templates.Template;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;

import java.util.ArrayList;
import java.util.List;

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
  public TemplateDefinition getByName(String name) {
    for (TemplateDefinition template : templates) {
      if (template.getName() == name) return template;
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
}
