package ru.otus.spring.service.answer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.service.question.QuestionService;

import static ru.otus.spring.QuestionTestData.*;

class AnswerServiceImplTest {

    @Test
    void addAnswer() {
        Answer expected = new Answer(USER, QUESTION2, true);
        QuestionService questionService = Mockito.mock(QuestionService.class);
        Mockito.when(questionService.getRightAnswer(QUESTION2)).thenReturn(QUESTION_ANSWER4);
        AnswerServiceImpl answerService = new AnswerServiceImpl(questionService);
        Assertions.assertEquals(expected, answerService.addAnswer("London", USER, QUESTION2));
    }

    @Test
    void validateAnswer() {
        QuestionService questionService = Mockito.mock(QuestionService.class);
        AnswerServiceImpl answerService = new AnswerServiceImpl(questionService);
        Assertions.assertFalse(answerService.validateAnswer("One", QUESTION1));
    }
}