package ru.otus.spring.repositories;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    List<Comment> findbyBookId(Long bookId);

    void update(Long id, String name);

    void deleteById(Long id);
}
