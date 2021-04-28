package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class OrmDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrmDemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
	}

}
