package ru.babaninnv.codegen.plugin.templator.annotations;

/**
 * Created by BabaninN on 04.04.2016.
 */
public @interface Templates {
  String name();
  String description() default "";
}
