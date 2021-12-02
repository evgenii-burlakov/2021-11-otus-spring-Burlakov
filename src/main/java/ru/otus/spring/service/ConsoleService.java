package ru.otus.spring.service;

import ru.otus.spring.domain.User;

public interface ConsoleService {
    void printAllQuestions();

    void printQuestion(int number);

    User addPerson();

    void doTest();
}
