package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.babaninnv.codegen.plugin.templator.objects.Template;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

import java.io.IOException;
import java.util.List;

/**
 * Created by BabaninN on 30.03.2016.
 */
@Component("listCommandImplementation")
public class ListCommandImplementationImpl implements CommandImplementation {

  @Autowired
  private TemplateRegistrar templateRegistrar;

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
