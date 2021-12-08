package ru.otus.spring.service.questionAnswer;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswers;

public interface QuestionAnswerService {
    QuestionAnswers getRightAnswer(Question question);
}
