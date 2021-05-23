package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

import java.util.*;

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
    @HystrixCommand(groupKey = "Library", commandKey = "addBook", fallbackMethod = "getDefaultBook")
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
    @HystrixCommand(groupKey = "Library", commandKey = "getAllBooks", fallbackMethod = "getDefaultBooks")
    public List<Book> getAllBooks() {
        sleepRandomly();
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
    @HystrixCommand(groupKey = "Library", commandKey = "getAuthorByName", fallbackMethod = "getDefaultAuthorByName")
    public Author getAuthorByName(String name) {
        sleepRandomly();
        return authorRepository.findByName(name);
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    @HystrixCommand(groupKey = "Library", commandKey = "getAllGenres", fallbackMethod = "getDefaultGenres")
    public List<Genre> getAllGenres() {
        sleepRandomly();
        return genreRepository.findAll();
    }

    @Override
    public void addComment(Long bookId,  String comment){
        Book book = getBookById(bookId);
        Comment commentEntity = new Comment(0L, comment, book) ;
        commentRepository.save(commentEntity);
    }

    @Override
    @HystrixCommand(groupKey = "Library", commandKey = "getCommentsByBook", fallbackMethod = "getDefaultCommentsByBook")
    public List<Comment> getCommentsByBook(Long bookId){
        sleepRandomly();
        return commentRepository.findByBookId(bookId);
    }

    public List<Comment> getDefaultCommentsByBook(Long bookId){
        System.out.println("get Default comments by book");
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment(0L, "defaultComemntary");
        comment.setBook(getDefaultBook("defaultBook", "defaultAuthor", "defaultGenre"));
        comments.add(comment);

        return comments;
    }

    public List<Book> getDefaultBooks() {
        System.out.println("get Default Books");
        List<Book> books = new ArrayList<>();
        books.add(getDefaultBook("defaultBook","defaultAuthor","defaultGenre"));
        return books;
    }

    public Book getDefaultBook(String bookName, String authorName, String genreName){
        Genre defaultGenre = new Genre(0L, genreName);
        Author defaultAuthor = new Author(0L, authorName);
        return new Book(0L, bookName, defaultAuthor, defaultGenre);
    }

    public List<Genre> getDefaultGenres(){
        System.out.println("get Default Genres");
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(0L, "defaultGenre"));
        return genres;
    }

    public Author getDefaultAuthorByName(String name) {
        System.out.println("get Default Author By Name");
        return new Author(0L, name);
    }

    private void sleepRandomly(){
        Random random = new Random();
        int randomInt = random.nextInt(3)+1;

        if (randomInt == 3){
            try{
                System.out.println("Start sleeping");
                Thread.sleep(5000);
            }catch(InterruptedException ex){
                System.out.println("Interrupted");
                ex.printStackTrace();
            }
        }
    }
}
