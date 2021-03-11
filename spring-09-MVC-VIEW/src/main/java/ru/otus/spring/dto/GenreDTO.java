package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Genre;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {

    private String name;

    public static GenreDTO toDto(Genre author) {//todo: написать нормальные билдеры
        return new GenreDTO(author.getName());
    }
}
