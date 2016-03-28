package ru.babaninnv.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.yaml.snakeyaml.Yaml;

public class Main {

  private static Framework m_fwk = null;

  public static void main(String[] argv) throws Exception {
    
    FileUtils.deleteDirectory(new File("felix-cache"));
    
    System.out.println("======================");
    System.out.println("Welcome to CodeGen");
    System.out.println("======================");

    Yaml yaml = new Yaml();
    Map<String, String> props = (Map<String, String>) yaml.load(Main.class.getClassLoader().getResourceAsStream("application.yaml"));
    
    Hashtable<String, String> config = new Hashtable<>();
    config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, props.get("framework_systempackages_extra"));
    
    try {
      m_fwk = getFrameworkFactory().newFramework(config);
      m_fwk.init();
      AutoProcessor.process(null, m_fwk.getBundleContext());
      m_fwk.start();
      
      BundleContext bundleContext = m_fwk.getBundleContext();
      
      bundleContext.installBundle(new File("C:\\Users\\NikitaRed\\git\\codegen-parent\\codegen\\bundles\\org.apache.felix.gogo.runtime-0.16.2.jar").toURI().toString()).start();
      bundleContext.installBundle(new File("C:\\Users\\NikitaRed\\git\\codegen-parent\\codegen\\bundles\\org.apache.felix.gogo.shell-0.12.0.jar").toURI().toString()).start();
            
      Bundle bundle = bundleContext.installBundle(new File("C:\\Users\\NikitaRed\\git\\codegen-parent\\plugins\\simple-plugin\\build\\libs\\simple-plugin.jar").toURI().toString());
      bundle.start();
      
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
