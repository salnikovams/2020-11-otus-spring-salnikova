package ru.otus.spring.shellCommand;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@ShellComponent
public class BookShellCommand {

    private final LibraryService libraryService;

    private JobLauncher jobLauncher;
    private Job mongoMigrationJob;

    @Autowired
    public BookShellCommand(LibraryService bookService, JobLauncher jobLauncher, Job mongoMigrationJob) {
        this.libraryService = bookService;
        this.jobLauncher = jobLauncher;
        this.mongoMigrationJob = mongoMigrationJob;
    }

    @ShellMethod(value = "Start data transfer to MongoDB", key = "to-mongo")
    public void transferToMongo() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(mongoMigrationJob, new JobParametersBuilder().toJobParameters());
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
                           @ShellOption(help = "name") String bookName) {
       libraryService.updateBookInfo(id,bookName);
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

    @ShellMethod(value = "Add comment", key = "add-comment")
    public void addComment(@ShellOption(help = "bookName") String bookName, @ShellOption(help = "comment") String comment) {
        libraryService.addComment(comment, bookName);
    }

    @ShellMethod(value = "All comments", key = "all-comments")
    public String getAllComments(@ShellOption(help = "bookId") Long bookId) {
        List<Comment> comments = libraryService.getCommentsByBook(bookId);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("comments=[");
        for (Comment comment: comments){
            stringBuilder.append(String.format("id= %s, comment = \"%s\"",comment.getId(),comment.getComment()));
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
