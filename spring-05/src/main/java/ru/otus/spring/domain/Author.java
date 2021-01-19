package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Author {
    private Long id;
    private final String name;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
