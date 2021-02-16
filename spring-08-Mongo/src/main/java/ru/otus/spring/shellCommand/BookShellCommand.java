package ru.otus.spring.shellCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.LibraryService;

import java.util.List;
import java.util.Set;

@ShellComponent
public class BookShellCommand {

    private final LibraryService libraryService;

    @Autowired
    public BookShellCommand(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Show all books", key = "all-books")
    public String getAllBooks() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Book> bookList = libraryService.getAllBooks();
        for (Book book : bookList){
            stringBuilder.append(String.format("Book id = %s, name = %s, author = %s, genre = %s",
                    book.getId(), book.getName(), book.getAuthor(), book.getGenre()));
            Set<Comment> comments = book.getComments();
            stringBuilder.append("comments=[");
            if (comments != null) {
                for (Comment comment : comments) {
                    stringBuilder.append(String.format("comment =\"%s\"", comment.getComment()));
                }
            }
            stringBuilder.append("]");
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
    public void updateBook(@ShellOption(help = "id") String id,
                           @ShellOption(help = "name") String bookName) {
       libraryService.updateBookInfo(id,bookName);
    }

    @ShellMethod(value = "Delete book", key = "delete-book")
    public void deleteBook(@ShellOption(help = "id") String id) {
        libraryService.deleteBook(id);
    }

    @ShellMethod(value = "Add comment", key = "add-comment")
    public void addComment(@ShellOption(help = "bookName") String bookName, @ShellOption(help = "comment") String comment) {
        libraryService.addComment(comment, bookName);
    }

    @ShellMethod(value = "All comments", key = "all-comments")
    public String getAllComments(@ShellOption(help = "bookId") String bookId) {
        Set<Comment> comments = libraryService.getCommentsByBook(bookId);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("comments=[");
        for (Comment comment: comments){
            stringBuilder.append(String.format("comment = \"%s\"",comment.getComment()));
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
