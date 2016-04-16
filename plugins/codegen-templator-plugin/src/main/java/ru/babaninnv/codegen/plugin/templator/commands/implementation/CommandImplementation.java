package ru.babaninnv.codegen.plugin.templator.commands.implementation;

import java.util.Map;

/**
 * Created by BabaninN on 30.03.2016.
 */
public interface CommandImplementation {
  void setup(Map<String, String> properties);
  void invoke();
}
