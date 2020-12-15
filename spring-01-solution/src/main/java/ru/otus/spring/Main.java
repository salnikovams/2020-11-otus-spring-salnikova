package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.StudentAnswer;
import ru.otus.spring.service.CsvParser;
import ru.otus.spring.service.TestService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService service = context.getBean(TestService.class);

        service.testStudents();

        context.close();
    }
}
