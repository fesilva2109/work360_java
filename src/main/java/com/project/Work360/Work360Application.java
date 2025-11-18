package com.project.Work360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Work360Application {

	public static void main(String[] args) {
		SpringApplication.run(Work360Application.class, args);
        System.out.println("Bem vindo ao Hub Work360");
	}
    // http://localhost:8080/swagger-ui/index.html#/
}
