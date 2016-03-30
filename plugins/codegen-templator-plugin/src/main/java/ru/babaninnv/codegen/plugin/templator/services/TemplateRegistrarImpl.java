package ru.babaninnv.codegen.plugin.templator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.babaninnv.codegen.plugin.templator.objects.Template;
import ru.babaninnv.codegen.plugin.templator.utils.Constants;
import ru.babaninnv.codegen.plugin.templator.utils.TemplateYamlConstants;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public class TemplateRegistrarImpl implements TemplateRegistrar {

  private static final Logger LOG = LoggerFactory.getLogger(TemplateRegistrarImpl.class);

  private List<Template> templates;
  private Map<String, String> configuration;

  public TemplateRegistrarImpl(Map<String, String> configuration) {
    this.configuration = configuration;
  }

  @Override
  public List<Template> load() throws IOException {

    templates = new ArrayList<>();

    String rootTemplateStoragePath = configuration.get(Constants.TEMPLATE_LOCATION);
    String rootTemplateStorageAbsolutePath = new File(rootTemplateStoragePath).getAbsolutePath();
    LOG.debug("#load> root template storage path: {}", rootTemplateStorageAbsolutePath);

    Files.walkFileTree(new File(rootTemplateStorageAbsolutePath).toPath(), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        File file = path.toFile();

        LOG.debug("#load>visitFile> file: " + file.getName());

        if (file.getName().equals(Constants.TEMPLATE_CONFIG_FILENAME)) {
          Template template = parseYaml(path);
          templates.add(template);
        }

        return FileVisitResult.CONTINUE;
      }
    });

    return templates;
  }

  private Template parseYaml(Path yamlFile) throws IOException {
    Yaml yaml = new Yaml();
    Map<String, Object> load = (Map<String, Object>) yaml.load(Files.newInputStream(yamlFile));

    String name = (String) load.get(TemplateYamlConstants.NAME);
    List<String> modules = (List<String>) load.get(TemplateYamlConstants.MODULES);
    List<String> classes = (List<String>) load.get(TemplateYamlConstants.CLASSES);

    LOG.debug("#parseYaml> name: {}", name);
    LOG.debug("#parseYaml> modules: {}", modules);
    LOG.debug("#parseYaml> classes: {}", classes);

    Path folder = yamlFile.getParent();

    LOG.debug("#parseYaml> folder: {}", folder);

    Template template = new Template(name);
    template.setModules(modules);
    template.setClassPaths(resolveClassname(folder, classes));

    LOG.debug("#parseYaml> template: {}", template);

    return template;
  }

  private List<String> resolveClassname(Path folder, List<String> classes) {

    List<String> classPaths = new ArrayList<>();

    for (String clazz : classes) {
      Path classPath = folder.resolve(clazz);
      LOG.debug("#resolveClassname> classPath: {}", classPath);
      classPaths.add(classPath.toAbsolutePath().toString());
    }

    return classPaths;
  }
}
