package ru.otus.spring.service.clientData;

import ru.otus.spring.domain.Question;

public interface ClientDataService {
    String getString();

    void printString(String string);

    String toStringQuestion(Question question);

    default void close() {}
}
