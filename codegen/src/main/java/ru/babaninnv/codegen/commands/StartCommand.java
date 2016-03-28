package ru.babaninnv.codegen.commands;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import ru.babaninnv.codegen.annotations.TCommand;

@Component
@Service
public class StartCommand implements TerminalCommands {
  
  @TCommand(value = "version", help = "Show version")
  public void version() {
    System.out.println("version");
  }
  
}
