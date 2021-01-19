package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface LibraryService {

    public void addBook(String bookName, String authorName, String genreName);
    public void updateBookInfo(Long id, String bookName, String authorName, String genreName);
    public void deleteBook(Long id);
    public Book getBookById(Long id);
    public Book getBookByName(String name);
    public int countBook();
    public List<Book> getAllBooks();
    public void deleteAuthor(Long id);
    public void deleteGenre(Long id);
}
