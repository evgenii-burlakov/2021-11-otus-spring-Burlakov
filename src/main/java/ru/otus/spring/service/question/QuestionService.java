package ru.otus.spring.service.question;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswers;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();

    QuestionAnswers getRightAnswer(Question question);

    String toStringQuestion(Question question);
}