package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Book;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private long id;

    private String name;

    private String author;

    private String genre;

    private List<GenreDTO> allGenres;


    public BookDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BookDto(long id, String name, String author, String genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
    }
}
