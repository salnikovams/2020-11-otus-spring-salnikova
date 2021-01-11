package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.service.TestService;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {

		SpringApplication.run(TestApplication.class, args);
		/*ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);

		TestService service1 = context.getBean(TestService.class);

		service1.testStudents();

		context.close();*/
	}


}
