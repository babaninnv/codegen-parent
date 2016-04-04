package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import com.google.common.collect.ImmutableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.objects.Template;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrarImpl;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Created by BabaninN on 30.03.2016.
 */
@ContextConfiguration(classes = ru.babaninnv.codegen.plugin.templator.configurations.ContextConfiguration.class)
public class ListCommandImplementationImplTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Autowired
  private CommandImplementation listCommandImplementation;

  @Test
  public void testInvoke() throws Exception {
    listCommandImplementation.invoke();
  }
}