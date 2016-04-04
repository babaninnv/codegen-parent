package ru.babaninnv.codegen.plugin.templator;

import ch.qos.logback.classic.spi.Configurator;
import org.apache.aries.blueprint.container.BlueprintContainerImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import ru.babaninnv.codegen.plugin.templator.commands.BasicCommand;
import ru.babaninnv.codegen.plugin.templator.commands.BasicCommandImpl;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommand;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommandImpl;
import ru.babaninnv.codegen.plugin.templator.configurations.ContextConfiguration;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrarImpl;
import ru.babaninnv.codegen.plugin.templator.utils.PluginConfiguration;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplatorPluginActivator implements BundleActivator {

  private BundleContext bundleContext;

  public void start(BundleContext context) throws Exception {
    this.bundleContext = context;

    Class.forName("ru.babaninnv.codegen.plugin.templator.configurations.ContextConfiguration");
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);

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
