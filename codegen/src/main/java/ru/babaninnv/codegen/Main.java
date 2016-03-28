package ru.babaninnv.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.util.tracker.ServiceTracker;

import ru.babaninnv.codegen.api.CodegenTemplate;
import ru.babaninnv.codegen.plugins.service.CodegenService;

public class Main {

  private static Framework m_fwk = null;

  public static void main(String[] argv) throws Exception {
    
    FileUtils.deleteDirectory(new File("felix-cache"));
    
    // Print welcome banner.
    System.out.println("\nWelcome to My Launcher");
    System.out.println("======================\n");

    Hashtable<String, String> config = new Hashtable<>();
    config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, 
        "ru.babaninnv.codegen.plugins.service," +
        "org.apache.felix.shell," + 
        "org.apache.felix.gogo.command");
    
    try {
      m_fwk = getFrameworkFactory().newFramework(config);
      m_fwk.init();
      AutoProcessor.process(null, m_fwk.getBundleContext());
      m_fwk.start();
      
      BundleContext bundleContext = m_fwk.getBundleContext();
      
      //bundleContext.installBundle(new File("bundles\\org.apache.felix.gogo.runtime-0.16.2.jar").toURI().toString()).start();
      //bundleContext.installBundle(new File("bundles\\org.apache.felix.gogo.command-0.16.0.jar").toURI().toString()).start();
      //bundleContext.installBundle(new File("bundles\\org.apache.felix.gogo.shell-0.12.0.jar").toURI().toString()).start();
      //bundleContext.installBundle(new File("bundles\\org.apache.felix.shell-1.4.3.jar").toURI().toString()).start();
      //bundleContext.installBundle(new File("bundles\\org.apache.felix.shell.tui-1.4.1.jar").toURI().toString()).start();
      
      
      Bundle bundle = bundleContext.installBundle(new File("C:\\Users\\NikitaRed\\git\\codegen-parent\\plugins\\simple-plugin\\build\\libs\\simple-plugin.jar").toURI().toString());
      bundle.start();
      
      
      ServiceReference<?> serviceReference = bundleContext.getServiceReference(CodegenService.class.getName());
      
      CodegenService codegenService = (CodegenService) bundleContext.getService(serviceReference);
      codegenService.speak();
      
      System.out.println(Arrays.toString(m_fwk.getRegisteredServices()));
      
      m_fwk.waitForStop(0);
      System.exit(0);
    } catch (Exception ex) {
      System.err.println("Could not create framework: " + ex);
      ex.printStackTrace();
      System.exit(-1);
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
