package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.AuthorRepositoryJpa;
import ru.otus.spring.repositories.BookRepositoryJpa;
import ru.otus.spring.repositories.CommentRepositoryJpa;
import ru.otus.spring.repositories.GenreRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepositoryJpa bookRepository;

    private final AuthorRepositoryJpa authorRepository;

    private final CommentRepositoryJpa commentRepository;

    private final GenreRepositoryJpa genreRepository;

    @Autowired
    public LibraryServiceImpl(BookRepositoryJpa bookDaoJdbc, AuthorRepositoryJpa authorDaoJdbc, GenreRepositoryJpa genreRepository, CommentRepositoryJpa commentRepository) {
        this.bookRepository = bookDaoJdbc;
        this.authorRepository = authorDaoJdbc;
        this.genreRepository = genreRepository;
        this.commentRepository=commentRepository;
    }


    @Override
    public void addBook(String bookName, String authorName, String genreName) {
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
        bookRepository.save(book);
    }

    @Override
    public void updateBookInfo(Long id, String bookName) {
        bookRepository.update(id, bookName);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id) ;
        return optionalBook.orElse(null);
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
    public void addComment(String comment, String bookName) {
        Book book = getBookByName(bookName);
        Comment commentEntity = new Comment(0L, comment, book) ;
        commentRepository.save(commentEntity);
    }

    @Override
    public List<Comment> getCommentsByBook(Long bookId){
        return commentRepository.findbyBookId(bookId);
    }
}
