package ru.babaninnv.codegen.plugin.templator.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.type.filter.AssignableTypeFilter;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Set;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class TemplateClassUtils {

  @Autowired
  private PluginConfiguration pluginConfiguration;

  @Autowired
  private TemplateRegistrar templateRegistrar;

  private static final Logger LOG = LoggerFactory.getLogger(TemplateClassUtils.class);

  public void reload() {

    WorkspaceSettings workspaceSettings = new WorkspaceSettings();

    try {

      File templatesClassesFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesClassesFolderPath);

      LOG.debug("#reload> templatesClassesFolder: {}", templatesClassesFolder.getAbsolutePath());

      if (!templatesClassesFolder.exists()) {
        throw new Exception(String.format("Folder '%s' with compiled templates not found",
                templatesClassesFolder.getAbsolutePath()));
      }

      TemplateClassLoader templateClassLoader = new TemplateClassLoader(new URL[]{templatesClassesFolder.toURI().toURL()}, this.getClass().getClassLoader());
      pluginConfiguration.setCurrentTemplateClassLoader(templateClassLoader);

    } catch (Exception e) {
      LOG.error("#reload> " + e.getMessage(), e);
    }
  }

  public void compile() throws IOException {

    WorkspaceSettings workspaceSettings = new WorkspaceSettings();

    File sourceFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesSourcesFolderPath);
    File outputFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesClassesFolderPath);

    if (outputFolder.exists()) FileUtils.forceDelete(outputFolder);
    FileUtils.forceMkdir(outputFolder);

    String javaVersionArg = "-1.8";
    String encodingArg = "-encoding UTF-8";
    String classpathArg = String.format("-cp \"%s\"", workspaceSettings.inheritedClasspath);
    String sourcepathArg = String.format("\"%s\"", sourceFolder.getAbsolutePath());
    String outputFolderArg = String.format("-d \"%s\"", outputFolder.getAbsolutePath());
    String additionalArgs = "-time -verbose";

    String commandLine = StringUtils.join(ImmutableList.of(javaVersionArg,
                                                           additionalArgs,
                                                           encodingArg,
                                                           classpathArg,
                                                           outputFolderArg,
                                                           sourcepathArg), " ");

    System.out.println("commandLine: " + commandLine);

    CompilationProgress progress = null;
    BatchCompiler.compile(commandLine, new PrintWriter(System.out), new PrintWriter(System.err), progress);

    reload();

    TemplateClassLoader classLoader = pluginConfiguration.getCurrentTemplateClassLoader();

    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.setResourceLoader(new DefaultResourceLoader(pluginConfiguration.getCurrentTemplateClassLoader()));
    scanner.addIncludeFilter(new AssignableTypeFilter(Template.class));
    Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("ru");

    for (BeanDefinition beanDefinition : beanDefinitions) {
      try {
        Template template = (Template) classLoader.loadClass(beanDefinition.getBeanClassName()).newInstance();
        TemplateDefinition templateDefinition = new TemplateDefinition(template);
        templateRegistrar.addTemplateDefinition(templateDefinition);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  private class WorkspaceSettings {
    String inheritedClasspath = pluginConfiguration.getString("java.class.path");
    String applicationHome = pluginConfiguration.getString("app.home");
    String templatesSourcesFolderPath = pluginConfiguration.getString(Constants.TEMPLATES_SOURCES_FOLDER);
    String templatesClassesFolderPath = pluginConfiguration.getString(Constants.TEMPLATES_CLASSES_FOLDER);
  }
}
