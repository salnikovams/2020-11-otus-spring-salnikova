package ru.otus.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUserName(String name);
}
