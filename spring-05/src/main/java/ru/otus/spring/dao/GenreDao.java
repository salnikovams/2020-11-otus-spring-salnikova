package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    Genre insert(Genre genre);

    void update(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    Genre getByName(String name);

    void deleteById(long id);
}

