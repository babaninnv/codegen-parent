package ru.babaninnv.codegen.plugin.templator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by NikitaRed on 30.03.2016.
 */
@ContextConfiguration(classes = ru.babaninnv.codegen.plugin.templator.configurations.ContextConfiguration.class)
public class TemplateRegistrarTest extends AbstractTestNGSpringContextTests {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateRegistrarTest.class);

  @Autowired
  private TemplateRegistrar templateRegistrar;

  @Test
  public void testLoad() throws Exception {

    List<TemplateDefinition> templates = templateRegistrar.getTemplates();

    LOG.info("#testLoad> templates.size(): {}", templates.size());

    assertThat(templates).isNotEmpty();
  }
}