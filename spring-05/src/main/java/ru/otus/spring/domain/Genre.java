package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Genre {
    private long id;
    private final String name;


    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }
}
