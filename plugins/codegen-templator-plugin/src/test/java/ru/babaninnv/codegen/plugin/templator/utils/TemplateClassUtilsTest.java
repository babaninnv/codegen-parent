package ru.babaninnv.codegen.plugin.templator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfiguration;
import ru.babaninnv.codegen.plugin.templator.configurations.BundleContextConfigurationTest;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by BabaninN on 31.03.2016.
 */
@ContextConfiguration(classes = { BundleContextConfiguration.class, BundleContextConfigurationTest.class})
@ActiveProfiles("test")
public class TemplateClassUtilsTest extends AbstractTestNGSpringContextTests {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateClassUtilsTest.class);

  @Autowired
  private TemplateClassUtils templateClassUtils;

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Test
  public void testCompile() throws Exception {

    templateRegistrar.loadTemplatesConfiguration();
    templateClassUtils.compile();

    TemplateDefinition exampleJavaTemplate = templateRegistrar.getByName("example-java-template");
    assertThat(exampleJavaTemplate).isNotNull();

    Template template = exampleJavaTemplate.getTemplate();

    assertThat(template).isNotNull();

  }
}