package ru.babaninnv.codegen.plugin.templator.services;

import java.io.IOException;
import java.util.List;

import ru.babaninnv.codegen.plugin.templator.objects.Template;

/**
 * Created by NikitaRed on 30.03.2016.
 */
public interface TemplateRegistrar {
  List<Template> load() throws IOException;
}
