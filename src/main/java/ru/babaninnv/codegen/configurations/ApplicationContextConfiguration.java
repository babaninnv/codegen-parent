package ru.babaninnv.codegen.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.babaninnv.codegen.Console;

@Configuration
public class ApplicationContextConfiguration {
	@Bean
	public Console console() {
		return new Console();
	}
}
