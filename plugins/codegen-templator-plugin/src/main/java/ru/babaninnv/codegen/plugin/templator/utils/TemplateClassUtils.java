package ru.babaninnv.codegen.plugin.templator.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Created by BabaninN on 31.03.2016.
 */
public class TemplateClassUtils {

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
      PluginConfiguration.setCurrentTemplateClassLoader(templateClassLoader);

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
  }

  private class WorkspaceSettings {
    String inheritedClasspath = PluginConfiguration.getString("java.class.path");
    String applicationHome = PluginConfiguration.getString("app.home");
    String templatesSourcesFolderPath = PluginConfiguration.getString(Constants.TEMPLATES_SOURCES_FOLDER);
    String templatesClassesFolderPath = PluginConfiguration.getString(Constants.TEMPLATES_CLASSES_FOLDER);
  }
}
