package ru.babaninnv.codegen.plugins;

import org.apache.felix.service.command.Descriptor;

public class CodegenTemplatorCommand {

  @Descriptor("displays available commands")
  public void help() {
    System.out.println("help");
  }

}
