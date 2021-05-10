package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 8;
    private static final long FIRST_AUTHOR_ID = 1L;

    private static final int EXPECTED_QUERIES_COUNT = 3;

    @Autowired
    private BookRepository bookRepositoryJpa;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @Transactional(readOnly = true)
    void shouldFindExpectedBookById() {
        val optionalActualBook = bookRepositoryJpa.findById(FIRST_AUTHOR_ID);
        val expectedBook = em.find(Book.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @Transactional(readOnly = true)
    void shouldReturnCorrectBooksListWithAllInfo() {

        val students = bookRepositoryJpa.findAll();
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(s -> !s.getName().equals(""));
    }

    @Test
    @Transactional
    void testSave() {
        val author = new Author( 0L,"Sidorov");
        val genre = new Genre( 0L,"Sience Fiction");

        val sidorov = new Book(0L, "Third Book", author , genre);
        Book book =
                em.persist(sidorov);
        Long bookId = book.getId();

        Optional<Book> optionalActualBook = bookRepositoryJpa.findById(bookId);

        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @Transactional
    void testSaveExisting() {
        Author author = em.find(Author.class, 1L);
        Genre genre = em.find(Genre.class, 1L);

        val sidorov = new Book(0L, "Third Book", author , genre);
        Book book =
                em.persist(sidorov);
        Long bookId = book.getId();

        Optional<Book> optionalActualBook = bookRepositoryJpa.findById(bookId);

        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

}