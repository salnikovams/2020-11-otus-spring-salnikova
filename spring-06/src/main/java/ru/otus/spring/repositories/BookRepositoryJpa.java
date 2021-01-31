package ru.otus.spring.repositories;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Book save(Book book);
    Optional<Book> findById(Long id);

    List<Book> findAll();
    List<Book> findByName(String name);

    void deleteById(Long id);
}
