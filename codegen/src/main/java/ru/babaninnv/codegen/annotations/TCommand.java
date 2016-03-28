package ru.babaninnv.codegen.annotations;

public @interface TCommand {

  String value();

  String help() default "";
  
}
