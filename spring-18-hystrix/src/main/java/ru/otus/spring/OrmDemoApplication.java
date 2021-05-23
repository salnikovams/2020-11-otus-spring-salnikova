package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCircuitBreaker
public class OrmDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrmDemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
	}

}
