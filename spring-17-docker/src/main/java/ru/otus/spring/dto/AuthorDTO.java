package ru.otus.spring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Author;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private long id;
    private String name;

    public static AuthorDTO toDto(Author book) {//todo: написать нормальные билдеры
        return new AuthorDTO(book.getId(), book.getName());
    }
}
