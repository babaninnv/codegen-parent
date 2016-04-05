package ru.babaninnv.codegen.plugin.templator.services;

import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;

import java.util.List;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public interface TemplateRegistrar {
  void loadTemplatesConfiguration();
  TemplateDefinition getByName(String name);
  void addTemplateDefinition(TemplateDefinition template);
  List<TemplateDefinition> getTemplates();
}
