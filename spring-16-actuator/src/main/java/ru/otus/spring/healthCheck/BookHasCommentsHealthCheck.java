package ru.otus.spring.healthCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class BookHasCommentsHealthCheck implements HealthIndicator {

    private BookRepository bookRepository;

    @Autowired
    public BookHasCommentsHealthCheck(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        List<Book> bookList = bookRepository.findAll();
        List<Book> bookListWithoutComments= bookList.stream()
                .filter(book -> book.getComments() == null || book.getComments().size() == 0)
                .collect(Collectors.toList());
        if (bookListWithoutComments.isEmpty()) {
            return Health.down().withDetail("message", "There are no books without comments").build();
        }
        return Health.up().withDetail("message", String.format("There are %s books with comments", bookListWithoutComments.size())).build();
    }
}
