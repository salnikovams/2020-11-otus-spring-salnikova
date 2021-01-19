package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;


    @org.junit.jupiter.api.Test
    void countTest() {
        authorDaoJdbc.insert(new Author("authorName"));
        int count = authorDaoJdbc.count();
        Assertions.assertEquals(count, 2);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        String authorName = "authorName";
        Author author = authorDaoJdbc.insert(new Author(authorName));
        Author getAuthor = authorDaoJdbc.getById(author.getId());
        Assertions.assertNotNull(getAuthor);
        Assertions.assertEquals(getAuthor.getName(), authorName);
    }

    @org.junit.jupiter.api.Test
    void update() {
        String authorName = "authorName";
        String newName = "newName";
        Author author = authorDaoJdbc.insert(new Author(authorName));
         authorDaoJdbc.update(new Author(author.getId(), newName));
        Author getAuthor = authorDaoJdbc.getById(author.getId());
        Assertions.assertNotNull(getAuthor);
        Assertions.assertEquals(getAuthor.getName(), newName);
    }

    @org.junit.jupiter.api.Test
    void getById() {
        String authorName = "Jack London";
        Author getAuthor = authorDaoJdbc.getById(1L);
        Assertions.assertNotNull(getAuthor);
        Assertions.assertEquals(getAuthor.getName(), authorName);
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        authorDaoJdbc.insert(new Author("authorName"));
        authorDaoJdbc.insert(new Author("authorName2"));
        List<Author> authors = authorDaoJdbc.getAll();
        Assertions.assertNotNull(authors);
        Assertions.assertEquals(authors.size(), 3);
    }

    @org.junit.jupiter.api.Test
    void getByName() {
        String authorName = "authorName";
        authorDaoJdbc.insert(new Author(authorName));
        authorDaoJdbc.insert(new Author("authorName2"));
        Author author = authorDaoJdbc.getByName(authorName);
        Assertions.assertNotNull(author);
        Assertions.assertEquals(author.getName(), authorName);
    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        String authorName = "authorName";
        Author author = authorDaoJdbc.insert(new Author(authorName));
        authorDaoJdbc.deleteById(author.getId());
        Author authorDeleted = authorDaoJdbc.getById(author.getId());
        Assertions.assertNull(authorDeleted);
    }
}