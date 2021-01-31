package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    Author save(Author book);
    Optional<Author> findById(Long id);

    List<Author> findAll();
    Author findByName(String name);

    void deleteById(Long id);
}
