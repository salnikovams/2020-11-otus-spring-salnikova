package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repositories.BookRepository;

@RestController
public class LibraryAjaxController {

    private final BookRepository bookRepository;

    public LibraryAjaxController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}/comments")
    public Flux<Comment> getAllComments(@PathVariable(name = "id") String id) {
    return bookRepository.findBookById(id).flatMapIterable(Book::getComments);
    }

    @GetMapping("/books")
    public Flux<Book> showBooks() {
        return bookRepository.findAll();
    }



}
