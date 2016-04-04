package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.templates.Template;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by NikitaRed on 03.04.2016.
 */
@Component("makeCommandImplementation")
public class MakeCommandImplementationImpl implements CommandImplementation {

  @Autowired
  private TemplateRegistrar templateRegistrar;

  private String name;

  @Override
  public void setup(Map<String, String> properties) {
    this.name = properties.get("name");
  }

  @Override
  public void invoke() {

    StringWriter writer = new StringWriter();

    TemplateDefinition templateDefinition = templateRegistrar.getByName(name);
    Template template = templateDefinition.getTemplate();
    template.setup(writer, null);
    template.render();

    System.out.println(writer.toString());
  }
}
