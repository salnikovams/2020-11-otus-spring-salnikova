package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repositories.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;


    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void addBook(String bookName, String authorName, String genreName) {
        Book book = new Book(bookName, authorName, genreName);
        bookRepository.save(book);
    }

    @Override
    public void updateBookInfo(String id, String bookName) {
        Book book = getBookById(id);
        book.setName(bookName);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> optionalBook = bookRepository.findById(id) ;
        return optionalBook.orElse(null);
    }

    @Override
    public Book getBookByName(String name) {
        List<Book> books = bookRepository.findByName(name);
        return (books!= null && books.size()!= 0)?books.get(0):null;
    }


    @Override
    public void addComment(String comment, String bookName) {
        Book book = getBookByName(bookName);
        if (book == null)
            return;
        Set<Comment> comments = book.getComments();
        if (comments == null){
            comments = new HashSet<Comment>();
        }
        Comment commentEntity = new Comment( comment) ;
        comments.add(commentEntity);
        book.setComments(comments);
        bookRepository.save(book);
    }

    @Override
    public Set<Comment> getCommentsByBook(String bookId){
        Book book = getBookById(bookId);
        return book.getComments();
    }
}
