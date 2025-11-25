package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan({"com.test"})
@EnableWebMvc
public class SpringProjectStart {
	public static void main(String[] args) {
		SpringApplication.run(SpringProjectStart.class, args);
	}

}