package com.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DiaryApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(DiaryApplication.class, args);
	}
}

