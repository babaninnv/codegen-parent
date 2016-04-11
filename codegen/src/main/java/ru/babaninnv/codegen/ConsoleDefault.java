package ru.babaninnv.codegen;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import ru.babaninnv.codegen.configurations.ApplicationConfiguration;
import ru.babaninnv.codegen.utils.FelixFrameworkUtils;
import ru.babaninnv.codegen.utils.PropertyConstants;

/**
 * Created by NikitaRed on 09.04.2016.
 */
public class ConsoleDefault implements Console {

  private ApplicationConfiguration configuration;
  private FelixFrameworkUtils felixFrameworkUtils;
  private List<String> packages;
  private String bundlesFolder;
  private List<String> bundleNames;

  @Override
  public void setConfiguration(ApplicationConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public void setFelixFrameworkUtils(FelixFrameworkUtils felixFrameworkUtils) {
    this.felixFrameworkUtils = felixFrameworkUtils;
  }

  @Override
  public void init() {
    packages = configuration.getStringList(PropertyConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
    bundlesFolder = configuration.getString(PropertyConstants.BUNDLES_FOLDER);
    bundleNames = configuration.getStringList(PropertyConstants.BUNDLES);
  }

  @Override
  public void start() {

    System.out.println("======================");
    System.out.println("  Welcome to CodeGen  ");
    System.out.println("======================");

    Hashtable<String, String> config = new Hashtable<>();

    felixFrameworkUtils.printFrameworkSystemPackagesExtra(packages);
    config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, StringUtils.join(packages, ','));

    try {
      FrameworkFactory frameworkFactory = felixFrameworkUtils.getFrameworkFactory();
      Framework framework = frameworkFactory.newFramework(config);
      framework.init();
      AutoProcessor.process(null, framework.getBundleContext());

      BundleContext bundleContext = framework.getBundleContext();

      // Install bundles
      for (String bundleName : bundleNames) {
        Bundle bundle = bundleContext.installBundle(new File(bundlesFolder, bundleName).toURI().toString());
        bundle.start();
      }

      felixFrameworkUtils.printInstalledServices(bundleContext);

      // Run felix framework
      FrameworkEvent event = null;
      do {
        framework.start();
        event = framework.waitForStop(0);
        assert event != null;
      }
      while (event.getType() == FrameworkEvent.STOPPED_UPDATE);

    } catch (Exception ex) {
      System.err.println("Could not create framework: " + ex);
      ex.printStackTrace();
    }
  }
}
