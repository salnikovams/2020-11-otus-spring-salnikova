package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Book;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(value = SpringExtension.class)
@DataMongoTest
class BookRepositoryMongoImplTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void init() {
        bookRepository.deleteAll();
    }


   @Test
    void shouldFindExpectedBookByName() {
        String bookName = "First Book";
       Book book1 = new Book("First Book", "author" , "genre");
       book1 = bookRepository.save(book1);
       Book book2 = new Book("Second Book", "author" , "genre");
       book2 = bookRepository.save(book2);

        List<Book> findBookList = bookRepository.findByName(bookName);
        assertThat(findBookList.size()).isEqualTo(1);
       assertThat(findBookList.get(0).getName()).isEqualTo(bookName);

    }


    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {

        Book book1 = new Book("First Book", "author" , "genre");
        book1 = bookRepository.save(book1);
        Book book2 = new Book("Second Book", "author" , "genre");
        book2 = bookRepository.save(book2);

        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(2);
    }

    @Test
    void testDeleteByID() {

        Book book1 = new Book("First Book", "author" , "genre");
        book1 = bookRepository.save(book1);
        String id1  = book1.getId();
        Book book2 = new Book("Second Book", "author" , "genre");
        book2 = bookRepository.save(book2);

        bookRepository.deleteBookById(id1);
        Book bookFind = bookRepository.findBookById(id1);
        assertThat(bookFind).isNull();
    }




}

