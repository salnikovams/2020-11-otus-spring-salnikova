package ru.otus.spring.dao;

import ru.otus.spring.domain.StudentAnswer;

public interface StudentAnswerDao {

    public void save(StudentAnswer studentAnswer);

    public StudentAnswer getByName(String name);
}
