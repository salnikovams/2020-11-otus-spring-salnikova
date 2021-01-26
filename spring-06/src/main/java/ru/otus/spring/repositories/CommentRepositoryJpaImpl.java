package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findbyBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select b from Comment b where b.book.id=:bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }


      @Override
          public List<Comment> findAll() {
              TypedQuery<Comment> query = em.createQuery("select b from Comment b", Comment.class);
              return query.getResultList();
          }


    @Override
    public void update(Long id, String comment) {
        Query query = em.createQuery("update Comment b set b.comment = :comment where b.id = :id");
        query.setParameter("comment", comment);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Comment b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
