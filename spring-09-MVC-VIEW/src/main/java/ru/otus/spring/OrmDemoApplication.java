package ru.otus.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.service.LibraryService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class OrmDemoApplication {

	/*@Autowired
	private LibraryService service;*/

	public static void main(String[] args) {
		SpringApplication.run(OrmDemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
	/*	service.addBook("new Book","author", "genre");
		service.addBook("new Book 2","author", "genre");*/
	}

}
