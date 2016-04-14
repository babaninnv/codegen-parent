package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.utils.TemplateClassUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by BabaninN on 30.03.2016.
 */
@Component("listCommandImplementation")
public class ListCommandImplementationImpl implements CommandImplementation {

  @Autowired
  private TemplateClassUtils templateClassUtils;

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Override
  public void setup(Map<String, String> name) {

  }

  @Override
  public void invoke() {
    try {
      templateRegistrar.loadTemplatesConfiguration();
      templateClassUtils.compile();

      List<TemplateDefinition> templates = templateRegistrar.getTemplates();

      if (CollectionUtils.isNotEmpty(templates)) {
        System.out.println("Found templates:");
        for (TemplateDefinition template : templates) {
          System.out.println(" - template: " + template.getName());
        }
      } else {
        System.out.println("Templates is empty");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
