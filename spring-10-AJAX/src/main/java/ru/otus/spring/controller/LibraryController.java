package ru.otus.spring.controller;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDTO;
import ru.otus.spring.dto.GenreDTO;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String getAllBooks(Model model) {
        return "list";
    }



  @GetMapping("/edit")
    public String showEditBook(@RequestParam("id") long id, Model model) {
        Book book = libraryService.getBookById(id);
        BookDto bookDTO = BookDto.toDto(book);


        List<Genre> genres = libraryService.getAllGenres();
        List<GenreDTO> genreNames = new ArrayList<>();
        for (Genre genre: genres){
            genreNames.add(GenreDTO.toDto(genre));
        }
        bookDTO.setAllGenres(genreNames);
        model.addAttribute("book", bookDTO);
        return "edit";
    }

    @PostMapping("/edit")
    public String editBook(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "genre") String genre
    ) {
        libraryService.updateBookInfo(id, name, author, genre);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String createBook(@RequestParam(name = "name") String name,
                         @RequestParam(name = "author") String author,
                         @RequestParam(name = "genre") String genre,
                         Model model) {
        Book book = libraryService.addBook(name, author, genre);
        model.addAttribute("addResult", book.getId());
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        libraryService.deleteBook(id);
        return "redirect:/";
    }



   @GetMapping("/comment")
    public String showCommentsForBookId(@RequestParam(name = "id") Long id, Model model) {
       model.addAttribute("bookId", id);
        return "comment";
    }

    @GetMapping("/addComment")
    public String addNewComment(
            @RequestParam("id") Long bookId, Model model
    ) {
        Book book = libraryService.getBookById(bookId);
        model.addAttribute("book", BookDto.toDto(book));

        return "addComment";
    }


    @PostMapping("/addComment")
    public String addComment(
            @RequestParam("id") Long bookId,
            @RequestParam("comment") String comment
    ) {
        libraryService.addComment(bookId, comment);
        return "redirect:/";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Not found");
    }

    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<String> handleException(JDBCException ex) {
        return ResponseEntity.badRequest().body(ex.getSQLException().getMessage());
    }

}
