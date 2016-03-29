package ru.babaninnv.codegen.templates;

import java.io.Writer;

/**
 * Created by BabaninN on 29.03.2016.
 */
public interface Template {
  void setup(Writer writer, Model model);
  void render();
}
