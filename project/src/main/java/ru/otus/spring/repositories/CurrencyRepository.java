package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Currency;
import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    List<Currency> findAll();
    Optional<Currency> findByIsoCode(String isoCode);
    Optional<Currency> findByAlphaCode(String alphaCode);
}
