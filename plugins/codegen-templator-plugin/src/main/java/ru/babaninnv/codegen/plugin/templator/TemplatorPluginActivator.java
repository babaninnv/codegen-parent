package ru.babaninnv.codegen.plugin.templator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.babaninnv.codegen.plugin.templator.commands.BasicCommandImpl;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommand;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfiguration;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplatorPluginActivator implements BundleActivator {

  private BundleContext bundleContext;

  public void start(BundleContext context) throws Exception {
    this.bundleContext = context;

    Class.forName("org.apache.tools.ant.taskdefs.MatchingTask");

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BundleContextConfiguration.class);

    TemplateCommand templateCommand = applicationContext.getBean(TemplateCommand.class);

    Dictionary<String, Object> props = new Hashtable<>();
    props.put("osgi.command.scope", "templator");
    props.put("osgi.command.function", new String[]{"list", "reload", "make"});
    bundleContext.registerService(TemplateCommand.class, templateCommand, props);

    props = new Hashtable<>();
    props.put("osgi.command.scope", "*");
    props.put("osgi.command.function", new String[]{"help"});
    bundleContext.registerService(BasicCommandImpl.class, new BasicCommandImpl(bundleContext), props);

  }

  public void stop(BundleContext context) throws Exception {

  }

  public Bundle[] getBundles() {
    if (bundleContext != null) {
      return bundleContext.getBundles();
    }
    return null;
  }
}
