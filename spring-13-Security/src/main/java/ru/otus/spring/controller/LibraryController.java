package ru.otus.spring.controller;

import org.hibernate.JDBCException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = libraryService.getAllBooks();
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", bookDtos);
        return "list";
    }

    @GetMapping("/books/edit")
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

    @PostMapping("/books/edit")
    public String editBook(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "genre") String genre
    ) {
        libraryService.updateBookInfo(id, name, author, genre);
        return "redirect:/books";
    }

    @PostMapping("/books/add")
    public String createBook(@RequestParam(name = "name") String name,
                         @RequestParam(name = "author") String author,
                         @RequestParam(name = "genre") String genre,
                         Model model) {
        Book book = libraryService.addBook(name, author, genre);
        model.addAttribute("addResult", book.getId());
        return "redirect:/books";
    }

    @GetMapping("/books/delete")
    public String delete(@RequestParam("id") long id) {
        libraryService.deleteBook(id);
        return "redirect:/books";
    }



    @GetMapping("/books/comment")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCommentsForBookId(@RequestParam(name = "id") Long id, Model model) {
        List<Comment> comments = libraryService.getCommentsByBook(id);
        List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
        for (Comment comment: comments){
            commentDTOList.add(CommentDTO.toDto(comment));
        }
        model.addAttribute("comments", commentDTOList);
        return "comment";
    }

    @GetMapping("/books/addComment")
    public String addNewComment(
            @RequestParam("id") Long bookId, Model model
    ) {
        Book book = libraryService.getBookById(bookId);
        model.addAttribute("book", BookDto.toDto(book));

        return "addComment";
    }


    @PostMapping("/books/addComment")
    public String addComment(
            @RequestParam("id") Long bookId,
            @RequestParam("comment") String comment
    ) {
        libraryService.addComment(bookId, comment);
        return "redirect:/books";
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
