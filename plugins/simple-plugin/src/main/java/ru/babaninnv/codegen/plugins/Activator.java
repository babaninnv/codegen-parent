package ru.babaninnv.codegen.plugins;

import org.apache.felix.shell.Command;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ru.babaninnv.codegen.plugins.service.CodegenService;

public class Activator implements BundleActivator {

  @Override
  public void start(BundleContext context) throws Exception {
    context.registerService(CodegenService.class.getName(), new CodegenServiceImpl(), null);
    context.registerService(Command.class.getName(), new ExampleCommand(), null);
    context.registerService(Command.class.getName(), new CodegenTemplatorCommand(), null);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    

  }
}
