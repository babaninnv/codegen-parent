package ru.babaninnv.codegen.plugin.templator.utils;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class TemplateClassLoader extends URLClassLoader {
  public TemplateClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }


}
