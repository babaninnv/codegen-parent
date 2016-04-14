package ru.babaninnv.codegen.templates.java_example;

import ru.babaninnv.codegen.plugin.templator.templates.Model;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import java.io.Writer;

/**
 * Created by BabaninN on 29.03.2016.
 */
public class ExampleJavaTemplate implements Template {

  private Writer w;

  @SuppressWarnings("unused")
  private Model model;

  @Override
  public String getName() {
    return "ExampleJavaTemplate";
  }

  public void setup(Writer writer, Model model) {
    this.model = model;
    this.w = writer;
  }

  public void render() {
    try {
      w.write("package ru.babaninnv.codegen.templates.java_example;\n");
      w.write("class ExmapleTemplate { \n");
      w.write(" public void foo() { \n");
      w.write(" System.out.println(\"Hello Template\");\n");
      w.write("}");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
