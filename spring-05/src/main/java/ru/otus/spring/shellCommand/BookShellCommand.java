package ru.otus.spring.shellCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@ShellComponent
public class BookShellCommand {

    private final LibraryService libraryService;

    @Autowired
    public BookShellCommand(LibraryService bookService) {
        this.libraryService = bookService;
    }

    @ShellMethod(value = "Show all books", key = "all-books")
    public String getAllBooks() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Book> bookList = libraryService.getAllBooks();
        for (Book book : bookList){
            stringBuilder.append(String.format("Book id = %s, name = %s, author = %s, genre = %s",
                    book.getId(), book.getName(), book.getAuthor(), book.getGenre()));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    @ShellMethod(value = "Add new book", key = "add-book")
    public void addBook(@ShellOption(help = "name") String bookName,
                             @ShellOption(help = "author ") String authorName,
                            @ShellOption(help = "genre") String genreName) {
        libraryService.addBook(bookName, authorName, genreName);
    }

   @ShellMethod(value = "Update book info", key = "update-book")
    public void updateBook(@ShellOption(help = "id") Long id,
                           @ShellOption(help = "name") String bookName,
                           @ShellOption(help = "author ") String authorName,
                           @ShellOption(help = "genre") String genreName) {
       libraryService.updateBookInfo(id,bookName, authorName, genreName);
    }

    @ShellMethod(value = "Delete book", key = "delete-book")
    public void deleteBook(@ShellOption(help = "id") Long id) {
        libraryService.deleteBook(id);
    }

    @ShellMethod(value = "Delete author", key = "delete-author")
    public void deleteAuthor(@ShellOption(help = "id") Long id) {
        libraryService.deleteAuthor(id);
    }

    @ShellMethod(value = "Delete genre", key = "delete-genre")
    public void deleteGenre(@ShellOption(help = "id") Long id) {
        libraryService.deleteGenre(id);
    }
}
