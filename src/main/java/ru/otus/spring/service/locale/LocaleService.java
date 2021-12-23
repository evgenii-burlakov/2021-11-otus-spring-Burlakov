package ru.otus.spring.service.locale;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface LocaleService {
    List<Question> getLocalizedQuestions();

    String getMessage(String message);

    String getMessage(String message, String... args);
}
