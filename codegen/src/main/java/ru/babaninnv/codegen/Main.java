package ru.babaninnv.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.main.AutoProcessor;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.javac.JavaCompiler;
import org.codehaus.groovy.tools.javac.JavacJavaCompiler;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.yaml.snakeyaml.Yaml;
import ru.babaninnv.codegen.utils.PropertyConstants;

public class Main {

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
    config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, StringUtils.join(packages, ','));

    /*
    CompilationUnit compilationUnit = new CompilationUnit();

    JavaCompiler javaCompiler = new JavacJavaCompiler(new CompilerConfiguration());
    javaCompiler.compile(new ArrayList<>(), compilationUnit);

    GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
    Class<?> groovyClass = groovyClassLoader.parseClass("class SimpleClass { def foo() { return 'Hello dynamic!' } }");
    */

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

      FrameworkEvent event = null;
      do
      {
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
