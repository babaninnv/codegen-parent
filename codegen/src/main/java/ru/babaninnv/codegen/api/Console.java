package ru.babaninnv.codegen.api;

import ru.babaninnv.codegen.configurations.ApplicationConfiguration;
import ru.babaninnv.codegen.utils.FelixFrameworkUtils;

/**
 * Created by NikitaRed on 10.04.2016.
 */
public interface Console {
  void setConfiguration(ApplicationConfiguration configuration);
  void setFelixFrameworkUtils(FelixFrameworkUtils felixFrameworkUtils);
  void init();
  void start();
}
