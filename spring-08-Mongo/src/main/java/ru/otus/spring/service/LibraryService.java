package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;


import java.util.List;
import java.util.Set;

public interface LibraryService {

    public void addBook(String bookName, String authorName, String genreName);
    public void updateBookInfo(String id, String bookName);
    public void deleteBook(String id);
    public Book getBookById(String id);
    public Book getBookByName(String name);
    public List<Book> getAllBooks();

    public void addComment(String comment, String bookName);
    public Set<Comment> getCommentsByBook(String bookId);
}
