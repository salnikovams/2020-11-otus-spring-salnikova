package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDTO;
import ru.otus.spring.service.LibraryService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LibraryAjaxController {

    private final LibraryService libraryService;

    public LibraryAjaxController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/comments")
    public List<CommentDTO> getAllComments(@RequestParam(name = "id") Long id) {
        List<Comment> comments = libraryService.getCommentsByBook(id);
        List<CommentDTO> commentDtos = comments.stream()
                .map(CommentDTO::toDto)
                .collect(Collectors.toList());
        return commentDtos;
    }


      @GetMapping("/books")
      public List<BookDto> showBooks() {
          List<Book> books = libraryService.getAllBooks();
          List<BookDto> bookDtos = books.stream()
                  .map(BookDto::toDto)
                  .collect(Collectors.toList());
          return bookDtos;
      }


}
