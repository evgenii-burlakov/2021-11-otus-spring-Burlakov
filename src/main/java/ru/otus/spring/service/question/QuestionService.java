package ru.otus.spring.service.question;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();
}