package ru.babaninnv.codegen.plugin.templator;

import org.apache.sling.testing.mock.osgi.MockOsgi;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.testng.annotations.Test;

import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommand;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommandImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

/**
 * Created by NikitaRed on 03.04.2016.
 */
public class TemplatorPluginActivatorTest {

  @Test
  public void testStart() throws Exception {

    BundleContext bundleContext = MockOsgi.newBundleContext();

    TemplatorPluginActivator templatorPluginActivator = new TemplatorPluginActivator();
    templatorPluginActivator.start(bundleContext);

    ServiceReference<TemplateCommand> serviceReference = bundleContext.getServiceReference(TemplateCommand.class);
    TemplateCommand templateCommand = bundleContext.getService(serviceReference);

    assertThat(templateCommand).isNotNull();
  }
}