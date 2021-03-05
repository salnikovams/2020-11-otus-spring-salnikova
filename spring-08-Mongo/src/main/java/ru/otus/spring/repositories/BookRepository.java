package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.domain.Book;
import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query(value="{'name':'?0'}")
    List<Book> findByName(String name);

    int deleteBookById(String id);

    Book findBookById(String id);
}
