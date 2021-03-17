package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface LibraryService {

    public Book addBook(String bookName, String authorName, String genreName);
    public Book updateBookInfo(Long id, String bookName, String authorName, String genre);
    public void deleteBook(Long id);
    public Book getBookById(Long id);
    public Book getBookByName(String name);
    public List<Book> getAllBooks();
    public void deleteAuthor(Long id);
    public void deleteGenre(Long id);
    public Author getAuthorByName(String name);
    public Genre getGenreByName(String name);
    public List<Genre> getAllGenres();
    public void addComment(Long bookId,  String comment);
    public List<Comment> getCommentsByBook(Long bookId);
}
