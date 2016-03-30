package ru.babaninnv.codegen.plugin.templator.commands;

import org.apache.felix.service.command.Descriptor;
import ru.babaninnv.codegen.plugin.templator.commands.implementation.ListCommandImplementationImpl;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplateCommandImpl implements TemplateCommand {

  private TemplateRegistrar templateRegistrar;

  public TemplateCommandImpl(TemplateRegistrar templateRegistrar) {
    this.templateRegistrar = templateRegistrar;
  }

  @Descriptor("list")
  public void list() {
    new ListCommandImplementationImpl(templateRegistrar).invoke();
  }
}
