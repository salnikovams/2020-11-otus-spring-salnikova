package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Book {
    private long id;
    private final String name;
    private final Author author;
    private final Genre genre;


    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
