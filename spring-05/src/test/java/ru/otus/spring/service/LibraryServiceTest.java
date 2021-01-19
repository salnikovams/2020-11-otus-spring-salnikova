package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorDaoJdbc;
import ru.otus.spring.dao.BookDaoJdbc;
import ru.otus.spring.dao.GenreDaoJdbc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
class LibraryServiceTest {

    @Autowired
    LibraryService libraryService;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void createBookTest() {
        String bookName = "bookName";
        String authorName = "authorName";
        String genreName = "genreName";

        libraryService.addBook(bookName, authorName, genreName);
        Book book = bookDaoJdbc.getByName(bookName);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getName(), bookName);
        Assertions.assertEquals(book.getAuthor().getName(), authorName);
        Assertions.assertEquals(book.getGenre().getName(), genreName);
    }

    @Test
    void updateBookTest() {
        String bookName2 = "bookName2";
        String authorName = "authorName2";
        String genreName = "genreName2";

        libraryService.addBook("bookName", "authorName", "genreName");
        Book book = bookDaoJdbc.getByName("bookName");
        Long bookID = book.getId();
        libraryService.updateBookInfo(bookID, bookName2, authorName, genreName);
        book = bookDaoJdbc.getById(bookID);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getName(), bookName2);
        Assertions.assertEquals(book.getAuthor().getName(), authorName);
        Assertions.assertEquals(book.getGenre().getName(), genreName);
    }

    @Test
    void deleteBookTest() {
        String bookName = "bookName";
        String authorName = "authorName";
        String genreName = "genreName";

        libraryService.addBook(bookName, authorName, genreName);
        Book book = bookDaoJdbc.getByName(bookName);
        libraryService.deleteBook(book.getId());
        book = bookDaoJdbc.getById(book.getId());
        Assertions.assertNull(book);
    }

    @Test
    void getBookById() {
        Book  book = libraryService.getBookById(1L);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getName(), "The Call of the Wild");
        Assertions.assertEquals(book.getGenre().getName(), "Adventure");
        Assertions.assertEquals(book.getAuthor().getName(), "Jack London");
    }

    @Test
    void getByName() {
        Book  book = libraryService.getBookByName("The Call of the Wild");
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getName(), "The Call of the Wild");
        Assertions.assertEquals(book.getGenre().getName(), "Adventure");
        Assertions.assertEquals(book.getAuthor().getName(), "Jack London");
    }

    @Test
    void count() {
        int count = libraryService.countBook();
        Assertions.assertEquals(count, 1);
    }

    @Test
    void getAll() {
        List<Book> books = libraryService.getAllBooks();
        Assertions.assertNotNull(books);
        Assertions.assertEquals(books.size(),1);
    }

    @Test
    void deleteAuthor() {
        Author author = authorDaoJdbc.insert(new Author("author"));
        libraryService.deleteAuthor(author.getId());
        author =  authorDaoJdbc.getById(author.getId());
        Assertions.assertNull(author);

    }

    @Test
    void deleteGenre() {
        Genre genre = genreDaoJdbc.insert(new Genre("genre"));
        libraryService.deleteGenre(genre.getId());
        genre =  genreDaoJdbc.getById(genre.getId());
        Assertions.assertNull(genre);
    }
}