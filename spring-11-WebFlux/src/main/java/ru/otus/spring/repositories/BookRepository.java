package ru.otus.spring.repositories;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    @Query(value="{'name':'?0'}")
    Mono<Book> findByName(String name);

    Mono<Book> findBookById(String id);
}
