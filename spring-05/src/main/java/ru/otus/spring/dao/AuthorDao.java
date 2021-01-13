package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    Author insert(Author person);

    void update(Author person);

    Author getById(Long id);

    List<Author> getAll();

    Author getByName(String name);

    void deleteById(Long id);
}
