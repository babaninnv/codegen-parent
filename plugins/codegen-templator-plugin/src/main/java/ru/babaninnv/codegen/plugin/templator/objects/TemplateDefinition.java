package ru.babaninnv.codegen.plugin.templator.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import java.util.List;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplateDefinition {
  private String name;
  private List<String> modules;
  private List<String> classPaths;
  private Template template;

  public TemplateDefinition(Template template) {
    this.template = template;
    this.name = template.getName();
  }

  public String getName() {
    return template.getName();
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    TemplateDefinition that = (TemplateDefinition) o;

    return new EqualsBuilder().append(name, that.name).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(name)
            .toHashCode();
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }
}
