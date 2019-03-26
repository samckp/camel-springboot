package com.sam.rest;

import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan({"com.sam.rest.route","com.sam.config"})
@PropertySource("application.properties")
@ImportResource("classpath:Bean.xml")
@CamelZipkin
public class CamelRestDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(CamelRestDemoApplication.class, args);
	}
}
