package ru.otus.spring.dao;

import ru.otus.spring.domain.StudentAnswer;

import java.util.Collection;

public interface StudentAnswerDao {

    public void save(StudentAnswer studentAnswer);

    public StudentAnswer getByName(String name);

    public Collection<StudentAnswer> getAll();
}
