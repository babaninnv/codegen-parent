package ru.babaninnv.codegen;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.babaninnv.codegen.api.CodegenTemplate;

@Component
public class Console {

  private static final Logger LOG = LoggerFactory.getLogger(Console.class);

  private static Framework m_fwk = null;
  
  public void init() {
    
    /*try {
      Map<String, String> configProps = new HashMap<>();
      FrameworkFactory factory = new org.apache.felix.framework.FrameworkFactory();
      m_fwk = factory.newFramework(configProps);
      m_fwk.init();
      
      
      
      BundleContext bundleContext = m_fwk.getBundleContext();

      LOG.info("bundles: {}", Arrays.toString(bundleContext.getBundles()));

      
      
      
      m_fwk.start();
      m_fwk.waitForStop(0);
      
    } catch (Exception e) {
      e.printStackTrace();
    }*/
  }

  public void run() {
    FrameworkEvent event;
  }

  
}
