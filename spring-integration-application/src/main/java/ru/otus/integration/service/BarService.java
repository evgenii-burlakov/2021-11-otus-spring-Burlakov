package ru.otus.integration.service;

import org.springframework.integration.annotation.Filter;
import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.Student;

import java.util.List;

@Service
public class BarService {

    public Bar isBarFree(Bar bar) throws Exception {
        boolean free = Math.random() > 0.5;
        if (free) {
            System.out.println(bar.getName() + " is free");
            bar.setFree(true);
            return bar;
        } else {
            System.out.println(bar.getName() + " is not free");
            bar.setFree(false);
            return bar;
        }
    }
}
