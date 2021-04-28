package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Frog;
import java.util.List;

public interface FrogRepository extends CrudRepository<Frog, Long> {
    List<Frog> findAll();
}
