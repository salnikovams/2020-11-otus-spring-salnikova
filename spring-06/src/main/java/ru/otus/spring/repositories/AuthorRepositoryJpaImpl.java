package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select b from Author b", Author.class);
        return query.getResultList();
    }

    @Override
    public Author findByName(String name) {
        try {
            TypedQuery<Author> query = em.createQuery("select b from Author b where b.name = :name", Author.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }catch (Exception e) {
                return null;
        }
    }

    @Override
    public void update(Long id, String name) {
        Query query = em.createQuery("update Author b set b.name = :name where b.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Author b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
