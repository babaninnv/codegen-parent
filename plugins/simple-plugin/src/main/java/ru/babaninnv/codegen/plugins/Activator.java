package ru.babaninnv.codegen.plugins;

import java.util.Hashtable;

import org.apache.felix.shell.Command;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ru.babaninnv.codegen.plugins.service.CodegenService;

public class Activator implements BundleActivator {

  @Override
  public void start(BundleContext context) throws Exception {
    
    final Hashtable<String, Object> props = new Hashtable<>();
    
    props.put("osgi.command.scope", "codegen");
    props.put("osgi.command.function", new String[] { "help" });
    context.registerService(CodegenTemplatorCommand.class.getName(), new CodegenTemplatorCommand(), props);

  }

  @Override
  public void stop(BundleContext context) throws Exception {
    

  }
}
