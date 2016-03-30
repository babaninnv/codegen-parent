package ru.babaninnv.codegen;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.*;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import ru.babaninnv.codegen.utils.PropertyConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  private static final String PROPERTIES_FILE = "application.yaml";

  private static Framework m_fwk = null;

  private Main() {

  }

  public static void main(String[] argv) throws Exception {
    FileUtils.deleteDirectory(new File("felix-cache"));

    // Load system properties.
    // Main.loadSystemProperties();

    new Main().run();
  }

  private void run() {

    System.out.println("======================");
    System.out.println("  Welcome to CodeGen  ");
    System.out.println("======================");

    Yaml yaml = new Yaml();
    Map<String, Object> props = (Map<String, Object>) yaml.load(Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));

    Hashtable<String, String> config = new Hashtable<>();
    List<String> packages = (List<String>) props.get(PropertyConstants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
    printFrameworkSystemPackagesExtra(packages);
    config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, StringUtils.join(packages, ','));

    try {
      m_fwk = getFrameworkFactory().newFramework(config);
      m_fwk.init();
      AutoProcessor.process(null, m_fwk.getBundleContext());

      BundleContext bundleContext = m_fwk.getBundleContext();

      String bundlesFolder = (String) props.get(PropertyConstants.BUNDLES_FOLDER);
      List<String> bundleNames = (List<String>) props.get(PropertyConstants.BUNDLES);

      for (String bundleName : bundleNames) {
        Bundle bundle = bundleContext.installBundle(new File(bundlesFolder, bundleName).toURI().toString());
        bundle.start();
      }

      printInstalledServices(bundleContext);

      FrameworkEvent event = null;
      do {
        m_fwk.start();

        event = m_fwk.waitForStop(0);
      }
      while (event.getType() == FrameworkEvent.STOPPED_UPDATE);

      System.exit(0);
    } catch (Exception ex) {
      System.err.println("Could not create framework: " + ex);
      ex.printStackTrace();
      System.exit(-1);
    }

  }

  private void printFrameworkSystemPackagesExtra(List<String> packages) {
    packages.forEach(System.out::println);
  }

  private void printInstalledServices(BundleContext bundleContext) {
    Bundle[] bundles = bundleContext.getBundles();

    for (Bundle bundle : bundles) {
      LOG.info("Bundle: {}", bundle.getSymbolicName());

      ServiceReference<?>[] registeredServices = bundle.getRegisteredServices();

      if (registeredServices != null) {

        for (ServiceReference<?> registeredService : registeredServices) {
          Object service = bundleContext.getService(registeredService);
          LOG.info("Service: {}", service.getClass().getName());
        }
      } else {
        LOG.info("Empty services");
      }
    }

  }


  private static FrameworkFactory getFrameworkFactory() throws Exception {
    java.net.URL url = Main.class.getClassLoader()
            .getResource("META-INF/services/org.osgi.framework.launch.FrameworkFactory");
    if (url != null) {
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
      try {
        for (String s = br.readLine(); s != null; s = br.readLine()) {
          s = s.trim();
          // Try to load first non-empty, non-commented line.
          if ((s.length() > 0) && (s.charAt(0) != '#')) {
            return (FrameworkFactory) Class.forName(s).newInstance();
          }
        }
      } finally {
        if (br != null)
          br.close();
      }
    }

    throw new Exception("Could not find framework factory.");
  }
}
