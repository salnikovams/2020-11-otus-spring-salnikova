package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.BookForMongo;

public interface BookRepositoryForMongo extends MongoRepository<BookForMongo, String> {
}
