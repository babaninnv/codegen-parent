package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.objects.Template;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrarImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Created by BabaninN on 30.03.2016.
 */
public class ListCommandImplementationImplTest {

  @Test
  public void testInvoke() throws Exception {
    TemplateRegistrar templateRegistrar = mock(TemplateRegistrar.class);
    when(templateRegistrar.load()).thenReturn(ImmutableList.of());

    CommandImplementation commandImplementation = new ListCommandImplementationImpl(templateRegistrar);
    commandImplementation.invoke();
  }
}