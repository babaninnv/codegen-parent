package ru.babaninnv.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import groovy.grape.Grape;

@Component
public class Console {

  private static final Logger LOG = LoggerFactory.getLogger(Console.class);
  
  private static Framework m_fwk = null;

  public void init() {

    Grape.grab("org.infinispan:infinispan-core:8.2.0.Final");
    
    try {
      Map<String, String> configProps = new HashMap<>();
      FrameworkFactory factory = getFrameworkFactory();
      m_fwk = factory.newFramework(configProps);
      m_fwk.init();
      
      BundleContext bundleContext = m_fwk.getBundleContext();
      bundleContext.installBundle(new File("").toURI().toString());
      
      LOG.info("bundles: {}", Arrays.toString(bundleContext.getBundles()));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    try {
      FrameworkEvent event;
      
      do {
        m_fwk.start();
        event = m_fwk.waitForStop(0);
      } while (event.getType() == FrameworkEvent.STOPPED_UPDATE);
      
    } catch (BundleException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static FrameworkFactory getFrameworkFactory() throws Exception {
    URL url = Main.class.getClassLoader().getResource("META-INF/services/org.osgi.framework.launch.FrameworkFactory");
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
