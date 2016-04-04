package ru.babaninnv.codegen.plugin.templator.commands;

import org.apache.felix.service.command.Descriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.babaninnv.codegen.plugin.templator.commands.implementation.CommandImplementation;
import ru.babaninnv.codegen.plugin.templator.commands.implementation.ListCommandImplementationImpl;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

/**
 * Created by NikitaRed on 30.03.2016.
 */
@Component
public class TemplateCommandImpl implements TemplateCommand {

  @Autowired
  private CommandImplementation listCommandImplementation;

  @Autowired
  private CommandImplementation reloadCommandImplementation;

  @Autowired
  private CommandImplementation makeCommandImplementation;

  private TemplateRegistrar templateRegistrar;

  public TemplateCommandImpl(TemplateRegistrar templateRegistrar) {
    this.templateRegistrar = templateRegistrar;
  }

  @Override
  @Descriptor("list")
  public void list() {
    listCommandImplementation.invoke();
  }

  @Override
  @Descriptor("reload")
  public void reload() {
    reloadCommandImplementation.invoke();
  }

  @Override
  @Descriptor("make")
  public void make() {
    makeCommandImplementation.invoke();
  }
}
