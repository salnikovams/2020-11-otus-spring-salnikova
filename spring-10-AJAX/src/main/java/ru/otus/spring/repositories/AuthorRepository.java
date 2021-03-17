package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Author;
import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();
    Author findByName(String name);
}
