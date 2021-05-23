package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Book;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
    List<Book> findByName(String name);
}
