package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AuthorRepositoryJpaImpl.class})

class AuthorRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final long FIRST_COMMENT_ID = 1L;

    @Autowired
    private AuthorRepositoryJpaImpl authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    @Transactional(readOnly = true)
    void shouldFindExpectedAuthorById() {
        val optionalActualAuthor = authorRepositoryJpa.findById(FIRST_COMMENT_ID);
        val expectedAuthor = em.find(Author.class, FIRST_COMMENT_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @Transactional(readOnly = true)
    void shouldReturnCorrectBooksListWithAllInfo() {

        val students = authorRepositoryJpa.findAll();
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getName().equals(""));
    }

    @Test
    @Transactional
    void testSave() {
        val author = new Author(0L, "author 2");

        Author savedAuthor = em.persist(author);

        Optional<Author> optionalActualAuthor = authorRepositoryJpa.findById(savedAuthor.getId());

        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(savedAuthor);
    }

}