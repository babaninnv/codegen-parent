package ru.babaninnv.codegen.plugin.templator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class TemplateClassUtilsTest {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateClassUtilsTest.class);

  @Test
  public void testReload() throws Exception {


  }

  @Test
  public void testCompile() throws Exception {
    PluginConfiguration.load();

    TemplateClassUtils templateClassUtils = new TemplateClassUtils();
    templateClassUtils.compile();
    templateClassUtils.reload();

    TemplateClassLoader classLoader = PluginConfiguration.getCurrentTemplateClassLoader();
    Class<?> templateClass = classLoader.loadClass("ru.babaninnv.codegen.templates.java_example.ExampleJavaTemplate");
    Template template = (Template) templateClass.newInstance();

    StringWriter writer = new StringWriter();
    template.setup(writer, null);

    LOG.info("result: " + writer.toString());

    assertThat(template).isNotNull();
  }
}