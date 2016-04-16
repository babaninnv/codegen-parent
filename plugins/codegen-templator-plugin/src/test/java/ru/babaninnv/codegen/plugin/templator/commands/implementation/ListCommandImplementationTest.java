package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfiguration;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfigurationTest;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;

import static org.mockito.Mockito.mock;

/**
 * Created by BabaninN on 30.03.2016.
 */
@ContextConfiguration(classes = BundleContextConfiguration.class)
public class ListCommandImplementationTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private CommandImplementation listCommandImplementation;

  @Test
  public void testInvoke() throws Exception {
    listCommandImplementation.invoke();
  }
}