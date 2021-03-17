package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    private String bookName;
    private String comment;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static CommentDTO toDto(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getBook().getName(), comment.getComment());
    }
}
