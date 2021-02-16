package ru.otus.spring.repositories;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.LibraryService;
import ru.otus.spring.service.LibraryServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(value = SpringExtension.class)
@DataMongoTest
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    private LibraryService libraryService;

    @BeforeEach
    public void init() {
        bookRepository.deleteAll();
        libraryService = new LibraryServiceImpl(bookRepository);
    }


   @Test
    void testGetBookByName() {
        String bookName = "bookName";
        given(bookRepository.findByName(bookName)).willReturn(Collections.singletonList(new Book(bookName, "author", "genre")));
        Book findBook = libraryService.getBookByName(bookName);
        assertThat(findBook.getName()).isEqualTo(bookName);
    }


    @Test
    void testGetAllBooks() {
        given(bookRepository.findAll()).willReturn(Arrays.asList(new Book("First Book", "author" , "genre"),  new Book("Second Book", "author" , "genre")));
        assertThat(libraryService.getAllBooks()).isNotNull().size().isEqualTo(2);
    }

    @Test
    void testGetBookByID() {
        String bookID = "1234";
        String bookName = "name";
        given(bookRepository.findById(bookID)).willReturn(Optional.of(new Book(bookID,bookName, "author", "genre", null)));
        Book findBook = libraryService.getBookById(bookID);
        assertThat(findBook).isNotNull();
        assertThat(findBook.getId()).isEqualTo(bookID);
        assertThat(findBook.getName()).isEqualTo(bookName);
    }


    @Test
    void testDeleteByID() {
        given(bookRepository.findById(any())).willReturn(Optional.empty());

        String bookId = "123456";
        libraryService.deleteBook(bookId);
        Book bookFind = libraryService.getBookById(bookId);
        assertThat(bookFind).isNull();
    }

    @Test
    public void testGetCommentsByBook(){
        String bookId = "123456";

        given(bookRepository.findById(any())).willReturn(Optional.of(new Book(bookId,"name", "author", "genre", Sets.newLinkedHashSet(new Comment("comment1"), new Comment("comment2")))));

        Set<Comment> commnts = libraryService.getCommentsByBook(bookId);
        assertThat(commnts).isNotNull();
        assertThat(commnts).isNotEmpty();
        assertThat(commnts).size().isEqualTo(2);
    }
}


