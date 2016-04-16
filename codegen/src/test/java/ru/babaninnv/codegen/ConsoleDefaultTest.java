package ru.babaninnv.codegen;

import com.google.common.collect.ImmutableList;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.testng.annotations.Test;

import ru.babaninnv.codegen.api.Console;
import ru.babaninnv.codegen.configurations.ApplicationConfiguration;
import ru.babaninnv.codegen.utils.FelixFrameworkUtils;
import ru.babaninnv.codegen.utils.PropertyConstants;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by NikitaRed on 10.04.2016.
 */
public class ConsoleDefaultTest {

  @Test(description = "Console will be init and start normally")
  public void consoleWillBeInitAndStartNormally() throws Exception {

    // GIVEN

    FelixFrameworkUtils felixFrameworkUtils = mock(FelixFrameworkUtils.class);
    ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    FrameworkFactory frameworkFactory = mock(FrameworkFactory.class);
    Framework framework = mock(Framework.class);
    BundleContext bundleContext = mock(BundleContext.class);
    Bundle bundle = mock(Bundle.class);
    FrameworkEvent frameworkEvent = mock(FrameworkEvent.class);

    // WHEN

    when(configuration.getStringList(PropertyConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA)).thenReturn(ImmutableList.of("com.example", "com.example.example"));
    when(felixFrameworkUtils.getFrameworkFactory()).thenReturn(frameworkFactory);
    when(frameworkFactory.newFramework(any())).thenReturn(framework);
    when(framework.getBundleContext()).thenReturn(bundleContext);
    when(bundleContext.installBundle(anyString())).thenReturn(bundle);
    when(framework.waitForStop(0)).thenReturn(frameworkEvent);
    when(frameworkEvent.getType()).thenReturn(FrameworkEvent.STOPPED);

    Console console = new ConsoleDefault();
    console.setFelixFrameworkUtils(felixFrameworkUtils);
    console.setConfiguration(configuration);
    console.init();
    console.start();
  }
}