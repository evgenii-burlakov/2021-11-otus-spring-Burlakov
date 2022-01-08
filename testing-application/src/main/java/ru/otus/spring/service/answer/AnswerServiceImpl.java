package ru.otus.spring.service.answer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;
import ru.otus.spring.domain.User;
import ru.otus.spring.service.questionAnswer.QuestionAnswerService;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final QuestionAnswerService questionAnswerService;

    @Override
    public Answer addAnswer(String answer, User user, Question question) {
        boolean result = false;
        if (question.getQuestionType().equals(QuestionType.FREE_ANSWER)) {
            result = answer.equalsIgnoreCase(questionAnswerService.getRightAnswer(question).getQuestionAnswer());
        } else if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            result = answer.equals(String.valueOf(questionAnswerService.getRightAnswer(question).getSequenceNumber()));
        }
        return new Answer(user, question, result);
    }

    @Override
    public boolean validateAnswer(String answer, Question question) {
        if (question.getQuestionType().equals(QuestionType.FREE_ANSWER)) {
            return answer != null && !answer.isBlank();
        } else if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            try {
                int answerOptionNumber = Integer.parseInt(answer);
                if (answerOptionNumber > 0 && answerOptionNumber <= question.getQuestionAnswers().size()) {
                    return true;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
