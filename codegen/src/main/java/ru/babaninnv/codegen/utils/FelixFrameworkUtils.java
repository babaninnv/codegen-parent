package ru.babaninnv.codegen.utils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by NikitaRed on 09.04.2016.
 */
public class FelixFrameworkUtils {

  private static final Logger LOG = LoggerFactory.getLogger(FelixFrameworkUtils.class);

  public FrameworkFactory getFrameworkFactory() throws Exception {
    URL url = FelixFrameworkUtils.class.getClassLoader()
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

  public void printFrameworkSystemPackagesExtra(List<String> packages) {
    packages.forEach(System.out::println);
  }

  public void printInstalledServices(BundleContext bundleContext) {
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
}
