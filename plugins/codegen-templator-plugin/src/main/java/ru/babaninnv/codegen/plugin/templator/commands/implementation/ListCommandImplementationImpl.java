package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.apache.commons.collections4.CollectionUtils;
import ru.babaninnv.codegen.plugin.templator.objects.Template;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

import java.io.IOException;
import java.util.List;

/**
 * Created by BabaninN on 30.03.2016.
 */
public class ListCommandImplementationImpl implements CommandImplementation {

  private TemplateRegistrar templateRegistrar;

  public ListCommandImplementationImpl(TemplateRegistrar templateRegistrar) {
    this.templateRegistrar = templateRegistrar;
  }

  @Override
  public void invoke() {
    try {
      List<Template> templates = templateRegistrar.load();

      if (CollectionUtils.isNotEmpty(templates)) {
        System.out.println("Found templates:");
        for (Template template : templates) {
          System.out.println(" - template: " + template.getName());
        }
      } else {
        System.out.println("Templates is empty");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
