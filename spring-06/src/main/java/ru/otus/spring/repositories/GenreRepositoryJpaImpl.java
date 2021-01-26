package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select b from Genre b", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre findByName(String name) {
        try {
            TypedQuery<Genre> query = em.createQuery("select b from Genre b where b.name = :name", Genre.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Genre b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
