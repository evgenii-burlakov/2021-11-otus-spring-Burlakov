package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.Student;

import java.util.List;

@Service
public class DayService {

    public Student dinner(Student student) throws Exception {
        System.out.println(student.getName() + " drink coffee");
        Thread.sleep(3000);
        System.out.println(student.getName() + " eat cakes");
        return student;
    }

    public List<Bar> tryingFindFreeBar(Student student) throws Exception {
        System.out.println(student.getName() + "  trying find free bar ");

        return List.of(new Bar("Mollys"), new Bar("SPB"), new Bar("KILLFISH"));
    }
}
