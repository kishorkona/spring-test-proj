package com.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan({"com.work"})
@EnableWebMvc
public class SpringProjApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(SpringProjApplicationStart.class, args);
	}

}