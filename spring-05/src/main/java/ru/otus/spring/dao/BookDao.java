package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    Long insert(Book book);

    void update(Book book);

    Book getById(long id);

    Book getByName(String name);

    List<Book> getAll();

    void deleteById(long id);
}
