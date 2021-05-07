package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 5;
    private static final long FIRST_COMMENT_ID = 1L;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @Transactional(readOnly = true)
    void shouldFindExpectedGenreById() {
        val optionalActualGenre = genreRepository.findById(FIRST_COMMENT_ID);
        val expectedGenre = em.find(Genre.class, FIRST_COMMENT_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @Transactional(readOnly = true)
    void shouldReturnCorrectBooksListWithAllInfo() {

        val students = genreRepository.findAll();
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getName().equals(""));
    }

    @Test
    @Transactional
    void testSave() {
        val genre = new Genre(0L, "genre 2");

        Genre savedGenre = em.persist(genre);

        Optional<Genre> optionalActualGenre = genreRepository.findById(savedGenre.getId());

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(savedGenre);
    }

}