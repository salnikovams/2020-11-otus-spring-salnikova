package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Frog;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repositories.FrogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FrogServiceImpl implements FrogService {

    private final FrogRepository frogRepository;

    @Autowired
    public FrogServiceImpl(FrogRepository frogRepository) {
        this.frogRepository = frogRepository;
    }

    @Override
    public Frog getFrogById(Long id) {
        Optional<Frog> optionalFrog = frogRepository.findById(id) ;
        return optionalFrog.orElseThrow(NotFoundException::new);
    }



    @Override
    public Frog addFrog(Frog frog) {
        return frogRepository.save(frog);

    }

    @Override
    public Frog updateFrogInfo(Frog frog) {
        return frogRepository.save(frog);
    }

    @Override
    public void deleteFrog(Long id) {
        frogRepository.deleteById(id);
    }


    @Override
    public List<Frog> getAllFrogs() {
        return frogRepository.findAll();
    }
}
