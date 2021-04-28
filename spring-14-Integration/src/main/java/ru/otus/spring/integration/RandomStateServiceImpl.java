package ru.otus.spring.integration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Frog;
import ru.otus.spring.service.FrogServiceImpl;


@Service
public class RandomStateServiceImpl implements RandomStateService {

    private FrogServiceImpl frogService;
    private String[] states = {"Икринка", "Головастик", "Лягушка", "Принцесса", "Не, не принцесса, королевна!",
            "Упс, не принцесса"};

    @Autowired
    public RandomStateServiceImpl(FrogServiceImpl frogService) {
        this.frogService = frogService;
    }

    @Override
    public Frog addRandomState(Frog frog) {
        frog.setState(states[RandomUtils.nextInt(0, states.length)]);
        frog = frogService.updateFrogInfo(frog);
        return frog;
    }
}
