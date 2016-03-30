package ru.babaninnv.codegen.plugin.templator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.babaninnv.codegen.plugin.templator.objects.Template;
import ru.babaninnv.codegen.plugin.templator.utils.Constants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplateRegistrarTest {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateRegistrarTest.class);

  @Test
  public void testLoad() throws Exception {

    Map<String, String> configuration = new HashMap<>();
    configuration.put(Constants.TEMPLATE_LOCATION, "../../templates/build/result-templates");

    TemplateRegistrar templateRegistrar = new TemplateRegistrarImpl(configuration);
    List<Template> templates = templateRegistrar.load();

    LOG.info("#testLoad> templates.size(): {}", templates.size());

    assertThat(templates).isNotEmpty();
  }
}