package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String comment;

    @Override
    public String toString() {
        return "Comment{" +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return getComment() != null ? getComment().equals(comment.getComment()) : comment.getComment() == null;
    }

    @Override
    public int hashCode() {
        return comment != null ? comment.hashCode() : 0;
    }
}
