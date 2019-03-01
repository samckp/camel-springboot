package com.sam.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.sam.rest.route")
@PropertySource("application.yml")
public class CamelRestDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(CamelRestDemoApplication.class, args);
	}
}
