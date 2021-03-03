package ru.otus.spring.controller;

import org.hibernate.JDBCException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDTO;
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

    @GetMapping("/")
    public String getAllBooks(Model model) {
        List<Book> books = libraryService.getAllBooks();
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", bookDtos);
        return "list";
    }

    @GetMapping("/edit")
    public String showEditBook(@RequestParam("id") long id, Model model) {
        Book book = libraryService.getBookById(id);
        model.addAttribute("book", BookDto.toDto(book));
        return "edit";
    }

    @PostMapping("/edit")
    public String editBook(
            @RequestParam("id") Long id,
            @RequestParam("name") String name
    ) {
        libraryService.updateBookInfo(id, name);
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
        List<Comment> comments = libraryService.getCommentsByBook(id);
        List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
        for (Comment comment: comments){
            commentDTOList.add(CommentDTO.toDto(comment));
        }
        model.addAttribute("comments", commentDTOList);
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
