package ru.otus.spring.service;

import ru.otus.spring.domain.Frog;

import java.util.List;

public interface FrogService {

    public Frog addFrog(Frog frog);
    public Frog updateFrogInfo(Frog frog);
    public void deleteFrog(Long id);
    public Frog getFrogById(Long id);
    public List<Frog> getAllFrogs();

}
