package ru.babaninnv.codegen.plugin.templator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

/**
 * Created by BabaninN on 31.03.2016.
 */
@ContextConfiguration(classes = ru.babaninnv.codegen.plugin.templator.configurations.ContextConfiguration.class)
public class PluginConfigurationTest extends AbstractTestNGSpringContextTests {

  private static final Logger LOG = LoggerFactory.getLogger(PluginConfigurationTest.class);

  @Autowired
  private PluginConfiguration pluginConfiguration;

  @Test
  public void testLoadAndGet() throws Exception {
    pluginConfiguration.load();
    String templatesSourcesFolder = pluginConfiguration.getString(Constants.TEMPLATES_SOURCES_FOLDER);
    LOG.info("templatesSourcesFolder: {}", templatesSourcesFolder);
    assertThat(templatesSourcesFolder).isNotEmpty();

    String templatesClassesFolder = pluginConfiguration.getString(Constants.TEMPLATES_CLASSES_FOLDER);
    LOG.info("templatesClassesFolder: {}", templatesClassesFolder);
    assertThat(templatesClassesFolder).isNotEmpty();
  }
}