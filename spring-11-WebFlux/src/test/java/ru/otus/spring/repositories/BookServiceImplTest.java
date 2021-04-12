package ru.otus.spring.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.rest.LibraryAjaxController;

import java.util.Collections;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(LibraryAjaxController.class)
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;


    @Autowired
    private WebTestClient testClient;


    @Before
    public void init() {
        bookRepository.deleteAll();    }

    @Test
    public void testComments() {
      when(bookRepository.findBookById(Mockito.any(String.class))).thenReturn(Mono.just(new Book("1", "book", "author", "genre", Collections.singleton(new Comment("comment")))));
      String uri = "/1/comments";
      testClient.get()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testBooks() {

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(Mono.just(new Book("1", "book", "author", "genre", Collections.singleton(new Comment("comment")))));
        bookRepository.save( new Book()).block();
        testClient.get()
                .uri("/books")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus()
                .isOk();
    }
}


