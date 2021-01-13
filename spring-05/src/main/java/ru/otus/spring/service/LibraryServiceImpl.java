package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDaoJdbc;
import ru.otus.spring.dao.BookDaoJdbc;
import ru.otus.spring.dao.GenreDaoJdbc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookDaoJdbc bookDaoJdbc;

    private final AuthorDaoJdbc authorDaoJdbc;

    private final GenreDaoJdbc genreDaoJdbc;

    @Autowired
    public LibraryServiceImpl(BookDaoJdbc bookDaoJdbc, AuthorDaoJdbc authorDaoJdbc, GenreDaoJdbc genreDaoJdbc) {
        this.bookDaoJdbc = bookDaoJdbc;
        this.authorDaoJdbc = authorDaoJdbc;
        this.genreDaoJdbc=genreDaoJdbc;
    }

    @Override
    public void addBook(String bookName, String authorName, String genreName) {
        Author author = authorDaoJdbc.getByName(authorName);
       if (author == null){
           author = authorDaoJdbc.insert(new Author(authorName));
       }

        Genre genre = genreDaoJdbc.getByName(genreName);
        if (genre == null){
            genre = genreDaoJdbc.insert(new Genre(genreName));
        }

        Book book = new Book(bookName, author, genre);
        bookDaoJdbc.insert(book);
    }

    @Override
    public void updateBookInfo(Long id, String bookName, String authorName, String genreName) {
        Author author = authorDaoJdbc.getByName(authorName);
        if (author == null){
            author = authorDaoJdbc.insert(new Author(authorName));
        }

        Genre genre = genreDaoJdbc.getByName(genreName);
        if (genre == null){
            genre = genreDaoJdbc.insert(new Genre(genreName));
        }

        Book book = new Book(id, bookName, author, genre);
        bookDaoJdbc.update(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookDaoJdbc.deleteById(id);
    }

    @Override
    public Book getBookById(Long id) {
        return bookDaoJdbc.getById(id);
    }

    @Override
    public Book getBookByName(String name) {
        return bookDaoJdbc.getByName(name);
    }

    @Override
    public int countBook() {
        return bookDaoJdbc.count();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDaoJdbc.getAll();
    }

    @Override
    public void deleteAuthor(Long id) {
        authorDaoJdbc.deleteById(id);
    }

    @Override
    public void deleteGenre(Long id) {
        genreDaoJdbc.deleteById(id);
    }
}
