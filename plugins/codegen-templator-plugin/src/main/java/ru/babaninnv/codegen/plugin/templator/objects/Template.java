package ru.babaninnv.codegen.plugin.templator.objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class Template {
  private final String name;
  private List<String> modules;
  private List<String> classPaths;

  public Template(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setModules(List<String> modules) {
    this.modules = modules;
  }
  public List<String> getModules() {
    return modules;
  }

  public void setClassPaths(List<String> classPaths) {
    this.classPaths = classPaths;
  }
  public List<String> getClassPaths() {
    return classPaths;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
