package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.CommentDTO;

import java.util.List;

public interface LibraryService {

    public void addBook(String bookName, String authorName, String genreName);
    public void updateBookInfo(Long id, String bookName);
    public void deleteBook(Long id);
    public Book getBookById(Long id);
    public Book getBookByName(String name);
    public List<Book> getAllBooks();
    public void deleteAuthor(Long id);
    public void deleteGenre(Long id);
    public Author getAuthorByName(String name);
    public Genre getGenreByName(String name);
    public void addComment(String comment, String bookName);
    public List<CommentDTO> getCommentsByBook(Long bookId);
}
