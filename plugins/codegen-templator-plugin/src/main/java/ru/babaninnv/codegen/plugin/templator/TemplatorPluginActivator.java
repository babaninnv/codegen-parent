package ru.babaninnv.codegen.plugin.templator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import ru.babaninnv.codegen.plugin.templator.commands.BasicCommand;
import ru.babaninnv.codegen.plugin.templator.commands.BasicCommandImpl;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommand;
import ru.babaninnv.codegen.plugin.templator.commands.TemplateCommandImpl;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrarImpl;

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

    Map<String, String> config = new HashMap<>();

    TemplateRegistrar templateRegistrar = new TemplateRegistrarImpl(config);

    Dictionary<String, Object> props = new Hashtable<>();
    props.put("osgi.command.scope", "templator");
    props.put("osgi.command.function", new String[]{"list"});
    bundleContext.registerService(TemplateCommandImpl.class, new TemplateCommandImpl(templateRegistrar), props);

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
