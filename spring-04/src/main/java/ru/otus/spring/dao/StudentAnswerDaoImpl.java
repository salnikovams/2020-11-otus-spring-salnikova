package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.StudentAnswer;

import java.util.*;

@Repository
public class StudentAnswerDaoImpl implements StudentAnswerDao {

    Map<String, StudentAnswer> allResults = new HashMap<String, StudentAnswer>();
    @Override
    public void save(StudentAnswer studentAnswer ) {
        allResults.put(studentAnswer.getName(), studentAnswer);
    }

    @Override
    public StudentAnswer getByName(String name) {
        return allResults.get(name);
    }

    @Override
    public Collection<StudentAnswer> getAll() {
        return allResults.values();
    }
}
