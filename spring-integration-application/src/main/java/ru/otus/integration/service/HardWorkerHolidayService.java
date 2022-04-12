package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.HardWorker;

import java.util.List;

@Service
public class HardWorkerHolidayService {

    public List<Bar> tryingFindFreeBar(HardWorker hardWorker) throws Exception {
        System.out.printf("%s trying find free bar%n", hardWorker.getName());

        return List.of(new Bar("Mollys"), new Bar("SPB"), new Bar("KILLFISH"));
    }
}
