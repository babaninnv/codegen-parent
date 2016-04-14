package ru.babaninnv.codegen.plugin.templator.commands;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import org.apache.felix.service.command.Descriptor;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class BasicCommandImpl implements BasicCommand {

  private BundleContext bundleContext;

  public BasicCommandImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Descriptor("help")
  public void help() {
    Map<String, List<Method>> commands = this.getCommands();
    commands.keySet().forEach(System.out::println);
  }

  private Map<String, List<Method>> getCommands() {
    ServiceReference[] refs = null;

    try {
      refs = this.bundleContext.getAllServiceReferences((String) null, "(osgi.command.scope=*)");
    } catch (InvalidSyntaxException ignored) {}

    TreeMap commands = new TreeMap();
    ServiceReference[] serviceReferences = refs;

    assert refs != null;
    int refsCount = refs.length;

    for (int i = 0; i < refsCount; ++i) {
      ServiceReference ref = serviceReferences[i];
      Object svc = this.bundleContext.getService(ref);
      if (svc != null) {
        String scope = (String) ref.getProperty("osgi.command.scope");
        Object ofunc = ref.getProperty("osgi.command.function");
        String[] funcs = ofunc instanceof String[] ? (String[]) ((String[]) ofunc) : new String[]{String.valueOf(ofunc)};
        String[] it = funcs;
        int arr$1 = funcs.length;

        int len$1;
        for (len$1 = 0; len$1 < arr$1; ++len$1) {
          String i$1 = it[len$1];
          commands.put(scope + ":" + i$1, new ArrayList());
        }

        if (!commands.isEmpty()) {
          Method[] var18 = svc.getClass().getMethods();
          Method[] var20 = var18;
          len$1 = var18.length;

          for (int var21 = 0; var21 < len$1; ++var21) {
            Method method = var20[var21];
            List commandMethods = (List) commands.get(scope + ":" + method.getName());
            if (commandMethods != null) {
              commandMethods.add(method);
            }
          }
        }

        Iterator var19 = commands.entrySet().iterator();

        while (var19.hasNext()) {
          if (((List) ((Map.Entry) var19.next()).getValue()).size() == 0) {
            var19.remove();
          }
        }
      }
    }

    return commands;
  }
}
