package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.LimitCondition;
import java.util.List;

public interface LimitConditionRepository extends CrudRepository<LimitCondition, Long> {

    List<LimitCondition> findAll();
    List<LimitCondition> findByLimitId(Long limitId);

}
