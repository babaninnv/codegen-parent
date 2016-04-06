package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;
import ru.babaninnv.codegen.plugin.templator.utils.TemplateClassUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by NikitaRed on 03.04.2016.
 */
@Component("reloadCommandImplementation")
public class ReloadCommandImplementationImpl implements CommandImplementation {

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Autowired
  private PluginConfiguration pluginConfiguration;

  @Autowired
  private TemplateClassUtils templateClassUtils;

  @Override
  public void setup(Map<String, String> name) {

  }

  @Override
  public void invoke() {
    templateClassUtils.compile();
  }
}
