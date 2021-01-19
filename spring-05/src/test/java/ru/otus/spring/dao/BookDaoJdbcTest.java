package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
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

        Author author= authorDaoJdbc.insert(new Author("authorName"));
        Genre genre = genreDaoJdbc.insert(new Genre("genre"));


        Book book = new Book("bookName", author, genre);
        bookDaoJdbc.insert(book);
        int count = bookDaoJdbc.count();
        Assertions.assertEquals(count, 2);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        String bookName = "bookName";
        Author author= authorDaoJdbc.insert(new Author("authorName"));
        Genre genre = genreDaoJdbc.insert(new Genre("genre"));


        Long bookID = bookDaoJdbc.insert(new Book(bookName, author, genre));
        Book getBook = bookDaoJdbc.getById(bookID);
        Assertions.assertNotNull(getBook);
        Assertions.assertEquals(getBook.getName(), bookName);
    }

    @org.junit.jupiter.api.Test
    void update() {
        String bookName = "bookName";
        String newName = "newName";
        Author author= authorDaoJdbc.insert(new Author("authorName"));
        Genre genre = genreDaoJdbc.insert(new Genre("genre"));

        Long bookId = bookDaoJdbc.insert(new Book(bookName, author, genre));
         bookDaoJdbc.update(new Book(bookId, newName, author, genre));
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
        Author author1= authorDaoJdbc.insert(new Author("authorName"));
        Author author2= authorDaoJdbc.insert(new Author("authorName2"));

        Genre genre1 = genreDaoJdbc.insert(new Genre("genre"));
        Genre genre2 = genreDaoJdbc.insert(new Genre("genre2"));

        Long book1Id = bookDaoJdbc.insert(new Book("bookName",  author1, genre1));
        Long book2Id = bookDaoJdbc.insert(new Book("bookName2", author2, genre2));
        List<Book> books = bookDaoJdbc.getAll();
        Assertions.assertNotNull(books);
        Assertions.assertEquals(books.size(), 3);
    }

    @org.junit.jupiter.api.Test
    void getByName() {
        String bookName = "bookName";
        Author author= authorDaoJdbc.insert(new Author("authorName"));
        Genre genre = genreDaoJdbc.insert(new Genre("genre"));

        bookDaoJdbc.insert(new Book(bookName, author, genre));
        bookDaoJdbc.insert(new Book("bookName2", author, genre));
        Book book = bookDaoJdbc.getByName(bookName);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getName(), bookName);
    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        String bookName = "bookName";
        Author author= authorDaoJdbc.insert(new Author("authorName"));
        Genre genre = genreDaoJdbc.insert(new Genre("genre"));

        Long bookId = bookDaoJdbc.insert(new Book(bookName, author, genre));
        bookDaoJdbc.deleteById(bookId);
        Book bookDeleted = bookDaoJdbc.getById(bookId);
        Assertions.assertNull(bookDeleted);
    }
}