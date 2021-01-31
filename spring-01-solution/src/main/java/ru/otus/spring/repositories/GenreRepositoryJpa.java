package ru.otus.spring.repositories;

import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {

    public Genre save(Genre genre);

    Optional<Genre> findById(Long id);

    List<Genre> findAll();

    Genre findByName(String name);

    void deleteById(Long id);
}
