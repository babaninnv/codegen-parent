package ru.babaninnv.codegen;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.babaninnv.codegen.configurations.context.ApplicationContextConfiguration;

public class Main {

  private static final String PROPERTIES_FILE = "conf/application.yaml";

  public static void main(String[] argv) throws Exception {
    FileUtils.deleteDirectory(new File("felix-cache"));
    new Main().run();
  }

  private void run() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ApplicationContextConfiguration.class);
    applicationContext.refresh();

    Console console = applicationContext.getBean(Console.class);
    console.start();
  }
}
