package ru.otus.spring.batch;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ToMongoMapper {

    public BookForMongo mapBookToMongo(Book book) {
        BookForMongo mongoBook = new BookForMongo();
        mongoBook.setAuthor(book.getAuthor().getName());
        mongoBook.setName(book.getName());
        Genre genre = book.getGenre();
        String genreName = " ";
        if (genre != null && genre.getName() != null) {
            genreName = genre.getName();
        }
        mongoBook.setGenre(genreName);

        List<Comment> comments = book.getComments();
        if (comments != null && comments.size() > 0) {
            Set<CommentForMongo> mongoComments = new HashSet<>();

            for (Comment comment : comments) {
                CommentForMongo mongoComment = new CommentForMongo();
                mongoComment.setComment(comment.getComment());
                mongoComments.add(mongoComment);
            }
            mongoBook.setComments(mongoComments);
        }

        return mongoBook;
    }
}
