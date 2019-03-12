package com.sam.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan({"com.sam.rest.route","com.sam.config"})
@PropertySource("application.yml")
@ImportResource("classpath:Bean.xml")
public class CamelRestDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(CamelRestDemoApplication.class, args);
	}
}
