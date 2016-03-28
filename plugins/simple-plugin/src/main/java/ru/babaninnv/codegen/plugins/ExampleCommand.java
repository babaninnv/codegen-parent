package ru.babaninnv.codegen.plugins;

import java.io.PrintStream;

import org.apache.felix.shell.Command;

public class ExampleCommand implements Command {

  @Override
  public String getName() {
    return "example";
  }

  @Override
  public String getUsage() {
    return "example <options>";
  }

  @Override
  public String getShortDescription() {
    return "Example command";
  }

  @Override
  public void execute(String line, PrintStream out, PrintStream err) {
    out.println("hello command!");
  }
}
