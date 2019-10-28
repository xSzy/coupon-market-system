package com.ptit.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CmsApplication  {

	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
		System.out.println("Server started!");
	}
	
	
}
