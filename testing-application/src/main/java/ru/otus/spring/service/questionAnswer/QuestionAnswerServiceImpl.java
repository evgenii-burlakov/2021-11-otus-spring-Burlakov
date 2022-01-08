package ru.otus.spring.service.questionAnswer;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswers;
import ru.otus.spring.domain.QuestionType;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Override
    public QuestionAnswers getRightAnswer(Question question) {
        if (question.getQuestionType().equals(QuestionType.FREE_ANSWER)) {
            return question.getQuestionAnswers().get(0);
        } else if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            return question.getQuestionAnswers().stream()
                    .filter(QuestionAnswers::isRightAnswer)
                    .findFirst().orElse(null);
        }
        return null;
    }
}
