package com.example.User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.User.entity")
@EnableJpaRepositories(basePackages = "com.example.User.repository")
@ComponentScan(basePackages = "com.example.User")
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		System.out.println("Application Started..!!!!");
	}
}
