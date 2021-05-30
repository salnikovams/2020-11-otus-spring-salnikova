package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Limit;

import java.util.Date;
import java.util.List;

public interface LimitRepository extends CrudRepository<Limit, Long> {
    List<Limit> findAll();
    List<Limit> findByIdNotAndName(Long id, String name);
    boolean existsByName(String name);
    @Query("select l from Limit l where (l.startDate is NULL or l.startDate<=:operationDate)AND (l.endDate is NULL or l.endDate>= :operationDate)")
    List<Limit> findByStartDateBeforeAndEndDate(@Param("operationDate") Date operationDate);
}
