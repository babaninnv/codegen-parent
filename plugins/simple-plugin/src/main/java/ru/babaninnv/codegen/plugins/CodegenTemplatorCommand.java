package ru.babaninnv.codegen.plugins;

import java.io.PrintStream;

import org.apache.felix.shell.Command;

public class CodegenTemplatorCommand implements Command {

  @Override
  public String getName() {
    return "codegen:templator";
  }

  @Override
  public String getUsage() {
    return "codegen:templator";
  }

  @Override
  public String getShortDescription() {
    return "codegen:templator";
  }

  @Override
  public void execute(String line, PrintStream out, PrintStream err) {
    System.out.println("line: " + line);
    
    
  }

}
