package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.repositories.GenreRepository;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final CommentRepository commentRepository;

    private final GenreRepository genreRepository;

    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository=commentRepository;
    }


    @Override
    public Book addBook(String bookName, String authorName, String genreName) {
       Author author = getAuthorByName(authorName);
       if (author == null){
           author = new Author(0L, authorName);
           authorRepository.save(author);
       }

        Genre genre = getGenreByName(genreName);
        if (genre == null){
            genre = new Genre(0L, genreName);
            genreRepository.save(genre);
        }

        Book book = new Book(0L, bookName, author, genre);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookInfo(Long id, String bookName, String authorName, String genreName) {
        Book book = getBookById(id);
        book.setName(bookName);
        Author author = getAuthorByName(authorName);
        if (author == null){
            author = new Author(0L, authorName);
            authorRepository.save(author);
        }
        book.setAuthor(author);
        Genre genre = getGenreByName(genreName);
        if (genre == null){
            genre = new Genre(0L, genreName);
            genreRepository.save(genre);
        }
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id) ;
        return optionalBook.orElseThrow(NotFoundException::new);
    }

    @Override
    public Book getBookByName(String name) {
        List<Book> books = bookRepository.findByName(name);
        return (books!= null && books.size()!= 0)?books.get(0):null;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void addComment(Long bookId,  String comment){
        Book book = getBookById(bookId);
        Comment commentEntity = new Comment(0L, comment, book) ;
        commentRepository.save(commentEntity);
    }

    @Override
    public List<Comment> getCommentsByBook(Long bookId){
        return commentRepository.findByBookId(bookId);
    }
}
