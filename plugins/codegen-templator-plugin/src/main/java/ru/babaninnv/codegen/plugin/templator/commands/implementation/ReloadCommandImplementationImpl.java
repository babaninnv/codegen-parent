package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

/**
 * Created by NikitaRed on 03.04.2016.
 */
@Component("reloadCommandImplementation")
public class ReloadCommandImplementationImpl implements CommandImplementation {

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Override
  public void invoke() {

  }
}
