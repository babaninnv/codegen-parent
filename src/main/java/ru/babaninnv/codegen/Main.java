package ru.babaninnv.codegen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.babaninnv.codegen.configurations.ApplicationContextConfiguration;

public class Main {
	
	public Main() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(ApplicationContextConfiguration.class);
		applicationContext.refresh();
		Console console = applicationContext.getBean(Console.class);
		console.run();
		
		applicationContext.destroy();
		applicationContext.close();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
