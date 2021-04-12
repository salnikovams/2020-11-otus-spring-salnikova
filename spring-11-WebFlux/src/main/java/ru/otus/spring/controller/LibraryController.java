package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.repositories.BookRepository;

@Controller
public class LibraryController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/")
    public String getAllBooks(Model model) {
        return "list";
    }

    @PostMapping("/add")
    public String createBook(
            @ModelAttribute BookDto bookDto,
            Model model) {
        Book book = new Book(bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
        Mono<Book> monoBook = bookRepository.save(book);
        model.addAttribute("addResult", monoBook.map(b -> b.getId()));
        return "redirect:/";
    }

    @GetMapping("/addComment")
    public String addNewComment(
            @RequestParam("id") String bookId,
            Model model
    ) {
        Mono<Book> monoBook = bookRepository.findBookById(bookId);
        model.addAttribute("book", monoBook.map(b->b.toDto()));
        return "addComment";
    }

    @PostMapping("/addComment")
    public String addComment(
            @RequestParam("id") String bookId,
            @ModelAttribute CommentDto commentDTO,
            Model model
    ) {

        Mono<Book> monoBook = bookRepository.findBookById(bookId)
        .map(b->{b.getComments().add(new Comment(commentDTO.getComment())); return b;})
                .flatMap(bookRepository::save);
        model.addAttribute("book", monoBook.map(b->b.toDto()));

        return "redirect:/";
    }

       @GetMapping("/delete")
    public String delete(@RequestParam("id") String id,  Model model) {

           model.addAttribute("book", bookRepository.deleteById(id));
        return "redirect:/";
    }

}