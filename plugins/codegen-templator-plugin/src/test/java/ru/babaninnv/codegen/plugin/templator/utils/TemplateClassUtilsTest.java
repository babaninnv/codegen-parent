package ru.babaninnv.codegen.plugin.templator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by BabaninN on 31.03.2016.
 */
@ContextConfiguration(classes = ru.babaninnv.codegen.plugin.templator.configurations.ContextConfiguration.class)
public class TemplateClassUtilsTest extends AbstractTestNGSpringContextTests {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateClassUtilsTest.class);

  @Autowired
  private TemplateClassUtils templateClassUtils;

  @Autowired
  private PluginConfiguration pluginConfiguration;

  @Test
  public void testReload() throws Exception {

    pluginConfiguration.load();

    templateClassUtils.reload();

    TemplateClassLoader classLoader = pluginConfiguration.getCurrentTemplateClassLoader();

    assertThat(classLoader).isNotNull();
  }

  @Test
  public void testCompile() throws Exception {
    pluginConfiguration.load();

    templateClassUtils.compile();
    templateClassUtils.reload();

    TemplateClassLoader classLoader = pluginConfiguration.getCurrentTemplateClassLoader();
    Class<?> templateClass = classLoader.loadClass("ru.babaninnv.codegen.templates.java_example.ExampleJavaTemplate");
    Template template = (Template) templateClass.newInstance();

    StringWriter writer = new StringWriter();
    template.setup(writer, null);
    template.render();

    LOG.info("result: " + writer.toString());

    assertThat(template).isNotNull();
  }
}