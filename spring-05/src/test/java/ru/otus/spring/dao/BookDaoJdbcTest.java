package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @org.junit.jupiter.api.Test
    void countTest() {
        Book book = new Book("bookName", new Author("authorName"), new Genre("genre"));
        bookDaoJdbc.insert(book);
        int count = bookDaoJdbc.count();
        Assertions.assertEquals(count, 2);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        String bookName = "bookName";
        Long bookID = bookDaoJdbc.insert(new Book(bookName, new Author("authorName"), new Genre("genre")));
        Book getBook = bookDaoJdbc.getById(bookID);
        Assertions.assertNotNull(getBook);
        Assertions.assertEquals(getBook.getName(), bookName);
    }

    @org.junit.jupiter.api.Test
    void update() {
        String bookName = "bookName";
        String newName = "newName";
        Long bookId = bookDaoJdbc.insert(new Book(bookName, new Author("authorName"), new Genre("genre")));
         bookDaoJdbc.update(new Book(bookId, newName, new Author("authorName"), new Genre("genre")));
        Book getBook = bookDaoJdbc.getById(bookId);
        Assertions.assertNotNull(getBook);
        Assertions.assertEquals(getBook.getName(), newName);
    }

    @org.junit.jupiter.api.Test
    void getById() {
        String bookName = "The Call of the Wild";
        Book getBook = bookDaoJdbc.getById(1L);
        Assertions.assertNotNull(getBook);
        Assertions.assertEquals(getBook.getName(), bookName);
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Long book1Id = bookDaoJdbc.insert(new Book("bookName",  new Author("authorName"), new Genre("genre")));
        Long book2Id = bookDaoJdbc.insert(new Book("bookName2", new Author("authorName"), new Genre("genre")));
        List<Book> books = bookDaoJdbc.getAll();
        Assertions.assertNotNull(books);
        Assertions.assertEquals(books.size(), 3);
    }

    @org.junit.jupiter.api.Test
    void getByName() {
        String bookName = "bookName";
        bookDaoJdbc.insert(new Book(bookName, new Author("authorName"), new Genre("genre")));
        bookDaoJdbc.insert(new Book("bookName2", new Author("authorName"), new Genre("genre")));
        Book book = bookDaoJdbc.getByName(bookName);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getName(), bookName);
    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        String bookName = "bookName";
        Long bookId = bookDaoJdbc.insert(new Book(bookName,  new Author("authorName"), new Genre("genre")));
        bookDaoJdbc.deleteById(bookId);
        Book bookDeleted = bookDaoJdbc.getById(bookId);
        Assertions.assertNull(bookDeleted);
    }
}