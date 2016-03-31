package ru.babaninnv.codegen.plugin.templator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class PluginConfigurationTest {

  private static final Logger LOG = LoggerFactory.getLogger(PluginConfigurationTest.class);

  @Test
  public void testLoadAndGet() throws Exception {
    PluginConfiguration.load();
    String templatesSourcesFolder = PluginConfiguration.getString(Constants.TEMPLATES_SOURCES_FOLDER);
    LOG.info("templatesSourcesFolder: {}", templatesSourcesFolder);
    assertThat(templatesSourcesFolder).isNotEmpty();

    String templatesClassesFolder = PluginConfiguration.getString(Constants.TEMPLATES_CLASSES_FOLDER);
    LOG.info("templatesClassesFolder: {}", templatesClassesFolder);
    assertThat(templatesClassesFolder).isNotEmpty();
  }
}