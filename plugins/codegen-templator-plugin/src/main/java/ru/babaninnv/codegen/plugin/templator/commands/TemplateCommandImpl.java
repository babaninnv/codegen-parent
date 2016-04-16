package ru.babaninnv.codegen.plugin.templator.commands;

import com.google.common.collect.ImmutableMap;

import org.apache.felix.service.command.Descriptor;
import org.springframework.beans.factory.annotation.Autowired;

import ru.babaninnv.codegen.plugin.templator.commands.implementation.CommandImplementation;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplateCommandImpl implements TemplateCommand {

  @Autowired
  private CommandImplementation listCommandImplementation;

  @Autowired
  private CommandImplementation makeCommandImplementation;

  @Override
  @Descriptor("list")
  public void list() {
    listCommandImplementation.invoke();
  }

  @Override
  @Descriptor("make")
  public void make(String name) {
    makeCommandImplementation.setup(ImmutableMap.of("name", name));
    makeCommandImplementation.invoke();
  }
}
