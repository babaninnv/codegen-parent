package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfiguration;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfigurationTest;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

/**
 * Created by BabaninN on 04.04.2016.
 */
@ContextConfiguration(classes = BundleContextConfiguration.class)
public class ReloadCommandImplementationImplTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Autowired
  private CommandImplementation reloadCommandImplementation;

  @Test
  public void testInvoke() throws Exception {
    reloadCommandImplementation.invoke();
  }
}