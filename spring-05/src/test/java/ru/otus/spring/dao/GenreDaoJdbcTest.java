package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;

import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;


    @org.junit.jupiter.api.Test
    void countTest() {
        Genre genre = new Genre("genreName");
        genre = genreDaoJdbc.insert(genre);
        int count = genreDaoJdbc.count();
        Assertions.assertEquals(count, 2);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        String genreName = "genreName";
        Genre genre = genreDaoJdbc.insert(new Genre(genreName));
        Genre getGenre = genreDaoJdbc.getById(genre.getId());
        Assertions.assertNotNull(getGenre);
        Assertions.assertEquals(getGenre.getName(), genreName);
    }

    @org.junit.jupiter.api.Test
    void update() {
        String genreName = "genreName";
        String newName = "newName";
        Genre genre = genreDaoJdbc.insert(new Genre(genreName));
         genreDaoJdbc.update(new Genre(genre.getId(), newName));
        Genre getGenre = genreDaoJdbc.getById(genre.getId());
        Assertions.assertNotNull(getGenre);
        Assertions.assertEquals(getGenre.getName(), newName);
    }

    @org.junit.jupiter.api.Test
    void getById() {
        String genreName = "Adventure";
        Genre getGenre = genreDaoJdbc.getById(1L);
        Assertions.assertNotNull(getGenre);
        Assertions.assertEquals(getGenre.getName(), genreName);
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Genre genre1 = genreDaoJdbc.insert(new Genre("genreName"));
        Genre genre2 = genreDaoJdbc.insert(new Genre("genreName2"));
        List<Genre> genres = genreDaoJdbc.getAll();
        Assertions.assertNotNull(genres);
        Assertions.assertEquals(genres.size(), 3);
    }

    @org.junit.jupiter.api.Test
    void getByName() {
        String genreName = "genreName";
        Genre genre1 = genreDaoJdbc.insert(new Genre(genreName));
        Genre genre2 = genreDaoJdbc.insert(new Genre("genreName2"));
        Genre genre = genreDaoJdbc.getByName(genreName);
        Assertions.assertNotNull(genre);
        Assertions.assertEquals(genre.getName(), genreName);
    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        String genreName = "genreName";
        Genre genre = genreDaoJdbc.insert(new Genre(genreName));
        genreDaoJdbc.deleteById(genre.getId());
        Genre genreDeleted = genreDaoJdbc.getById(genre.getId());
        Assertions.assertNull(genreDeleted);
    }
}