package ru.otus.spring.service.string;

import ru.otus.spring.domain.Question;

public interface StringService {
    String toStringQuestion(Question question);

    String getMessage(String message);

    String getMessage(String message, String... args);
}
