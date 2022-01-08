package ru.otus.spring.service.answer;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;

public interface AnswerService {
    Answer addAnswer(String answer, User user, Question question);

    boolean validateAnswer(String answer, Question question);
}
