package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface ConsoleService {
    void printAllQuestions();

    String toStringQuestion(Question question);
}
