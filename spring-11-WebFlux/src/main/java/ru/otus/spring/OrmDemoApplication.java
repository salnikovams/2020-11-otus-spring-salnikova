package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repositories.BookRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "ru.otus.spring")
public class OrmDemoApplication {

	public static void main(String[] args) {

	//	SpringApplication.run(OrmDemoApplication.class, args);

		ApplicationContext context = SpringApplication.run(OrmDemoApplication.class);
		BookRepository repository = context.getBean(BookRepository.class);

		Set<Comment> comments = new HashSet<Comment>();
		comments.add(new Comment("comment1"));
		comments.add(new Comment("comment2"));
		comments.add(new Comment("comment3"));
		repository.saveAll(Arrays.asList(
				new Book("Book1", "Author1", "Genre1", comments),
				new Book("Book2", "Author2", "Genre2", comments),
				new Book("Book3", "Author3", "Genre3", comments)
		)).subscribe(p -> System.out.println(p.getName()));

	}

}
