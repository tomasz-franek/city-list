package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.example.repository"})
@EntityScan(basePackages = "org.example")
@ComponentScan(basePackages ={ "org.example.controller","org.example.service"})
public class CityApplication {
	public static void main(String[] args) {
		SpringApplication.run(CityApplication.class, args);
	}
}
