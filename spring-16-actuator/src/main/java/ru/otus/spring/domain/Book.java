package ru.otus.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "AUTHORID")
    @ManyToOne(targetEntity = Author.class )
    private Author author;

    @JoinColumn(name = "GENREID")
    @ManyToOne(targetEntity = Genre.class )
    private Genre genre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private List<Comment> comments;


    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
