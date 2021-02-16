package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "ru.otus.spring")
public class OrmDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrmDemoApplication.class, args);
	}

}
