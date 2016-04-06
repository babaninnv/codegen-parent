package ru.babaninnv.codegen.plugin.templator.utils;

import com.google.common.collect.ImmutableList;
import groovy.lang.GroovyClassLoader;
import groovy.util.AntBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.codehaus.groovy.ant.Groovyc;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.FileSystemCompiler;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.babaninnv.codegen.plugin.templator.objects.TemplateDefinition;
import ru.babaninnv.codegen.plugin.templator.services.TemplateRegistrar;
import ru.babaninnv.codegen.plugin.templator.templates.Template;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class TemplateClassUtils {

  @Autowired
  private PluginConfiguration pluginConfiguration;

  @Autowired
  private TemplateRegistrar templateRegistrar;

  private static final Logger LOG = LoggerFactory.getLogger(TemplateClassUtils.class);

  private WorkspaceSettings workspaceSettings;

  public void compile() {

    workspaceSettings = new WorkspaceSettings();

    try {
      //javaCompile();
      groovyCompile();
      //copyResources();
      //reloadClasses();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void javaCompile() throws IOException {

    WorkspaceSettings workspaceSettings = new WorkspaceSettings();

    File javaSourceFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesJavaSourcesFolderPath);
    File outputFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesClassesFolderPath);

    if (outputFolder.exists()) FileUtils.forceDelete(outputFolder);
    FileUtils.forceMkdir(outputFolder);

    String currentJarLocation = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).toString().replaceAll("c:", "C:");

    String javaVersionArg = "-1.8";
    String encodingArg = "-encoding UTF-8";
    String classpathArg = String.format("-cp \"%s\"", workspaceSettings.inheritedClasspath.concat(";").concat(currentJarLocation));
    String sourcepathArg = String.format("\"%s\"", javaSourceFolder.getAbsolutePath());
    String outputFolderArg = String.format("-d \"%s\"", outputFolder.getAbsolutePath());
    String additionalArgs = "-time";

    String commandLine = StringUtils.join(ImmutableList.of(javaVersionArg,
            additionalArgs,
            encodingArg,
            classpathArg,
            outputFolderArg,
            sourcepathArg), " ");

    CompilationProgress progress = null;
    BatchCompiler.compile(commandLine, new PrintWriter(System.out), new PrintWriter(System.err), progress);
  }

  private void groovyCompile() throws IOException {

    WorkspaceSettings workspaceSettings = new WorkspaceSettings();

    File groovySourceFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesGroovySourcesFolderPath);
    File outputFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesClassesFolderPath);

    if (outputFolder.exists()) FileUtils.forceDelete(outputFolder);
    FileUtils.forceMkdir(outputFolder);

    String currentJarLocation = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).toString().replaceAll("c:", "C:");

    CompilerConfiguration configuration = new CompilerConfiguration();
    configuration.setClasspath(workspaceSettings.inheritedClasspath.concat(";").concat(currentJarLocation));

    CompilationUnit compilationUnit = new CompilationUnit();

    FileSystemCompiler.doCompilation(configuration, compilationUnit, );

    /*
    Project project = new Project();
    Path classpath = new Path(project, workspaceSettings.inheritedClasspath.concat(";").concat(currentJarLocation));
    Path sourceFolder = new Path(project, groovySourceFolder.getAbsolutePath());

    Groovyc groovyc = new Groovyc();
    groovyc.setProject(project);
    groovyc.setSrcdir(sourceFolder);
    groovyc.setClasspath(classpath);
    groovyc.setDestdir(outputFolder);
    groovyc.execute();
    */
  }

  private void copyResources() throws IOException {
    File resourcesFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesResourcesFolderPath);
    File outputFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesClassesFolderPath);
    FileUtils.copyDirectory(resourcesFolder, outputFolder);
  }

  private void reloadClasses() throws MalformedURLException {
    File templatesClassesFolder = new File(workspaceSettings.applicationHome, workspaceSettings.templatesClassesFolderPath);
    TemplateClassLoader classLoader = new TemplateClassLoader(new URL[]{templatesClassesFolder.toURI().toURL()}, this.getClass().getClassLoader());

    List<TemplateDefinition> templateDefinitions = templateRegistrar.getTemplates();
    for (TemplateDefinition templateDefinition : templateDefinitions) {
      try {
        Class<?> clazz = classLoader.loadClass(templateDefinition.getClassName());
        templateDefinition.setTemplate((Template) clazz.newInstance());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private class WorkspaceSettings {
    String inheritedClasspath = pluginConfiguration.getString("java.class.path");
    String applicationHome = pluginConfiguration.getString("app.home");
    String templatesJavaSourcesFolderPath = pluginConfiguration.getString(Constants.TEMPLATES_JAVA_SOURCES_FOLDER);
    String templatesGroovySourcesFolderPath = pluginConfiguration.getString(Constants.TEMPLATES_GROOVY_SOURCES_FOLDER);
    String templatesResourcesFolderPath = pluginConfiguration.getString(Constants.TEMPLATES_RESOURCES_FOLDER);
    String templatesClassesFolderPath = pluginConfiguration.getString(Constants.TEMPLATES_CLASSES_FOLDER);
  }
}
