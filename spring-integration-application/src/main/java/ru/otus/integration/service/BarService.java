package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Bar;

import java.util.List;

@Service
public class BarService {

    public Bar isBarFree(Bar bar) throws Exception {
        boolean free = Math.random() > 0.5;
        if (free) {
            System.out.printf("%s is free!!!%n", bar.getName());
            bar.setFree(true);
        } else {
            System.out.printf("%s is not free%n", bar.getName());
            bar.setFree(false);
        }
        return bar;
    }

    public Bar barDayResult(List<Bar> bar) throws Exception {
        for (int i = 0; i < bar.size(); i++) {
            if (i == 0) {
                System.out.printf("Hard worker drinking in pub %s%n", bar.get(i).getName());
                System.out.printf("%s WAS DESTROYED!%n", bar.get(i).getName());
            } else {
                System.out.printf("%s was close to destruction%n", bar.get(i).getName());
            }
        }

        return bar.get(0);
    }
}
