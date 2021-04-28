package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Frog;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FrogRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final long FIRST_AUTHOR_ID = 1L;

    private static final int EXPECTED_QUERIES_COUNT = 3;

    @Autowired
    private FrogRepository frogRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @Transactional(readOnly = true)
    void shouldFindExpectedBookById() {
        val optionalActualBook = frogRepository.findById(FIRST_AUTHOR_ID);
        val expectedBook = em.find(Frog.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @Transactional(readOnly = true)
    void shouldReturnCorrectBooksListWithAllInfo() {

        val students = frogRepository.findAll();
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(s -> !s.getName().equals(""));
    }

    @Test
    @Transactional
    void testSave() {

        val sidorov = new Frog( "Frog The ONE");
        Frog book =
                em.persist(sidorov);
        Long bookId = book.getId();

        Optional<Frog> optionalActualBook = frogRepository.findById(bookId);

        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @Transactional
    void testSaveExisting() {
        val sidorov = new Frog("Third Frog");
        Frog book =
                em.persist(sidorov);
        Long bookId = book.getId();

        Optional<Frog> optionalActualBook = frogRepository.findById(bookId);

        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

}