package ru.otus.spring.service.answer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.service.questionAnswer.QuestionAnswerService;

import static ru.otus.spring.QuestionTestData.*;

class AnswerServiceImplTest {

    @Test
    void addAnswer() {
        Answer expected = new Answer(USER, QUESTION2, true);
        QuestionAnswerService questionAnswerService = Mockito.mock(QuestionAnswerService.class);
        Mockito.when(questionAnswerService.getRightAnswer(QUESTION2)).thenReturn(QUESTION_ANSWER4);
        AnswerServiceImpl answerService = new AnswerServiceImpl(questionAnswerService);
        Assertions.assertEquals(expected, answerService.addAnswer("London", USER, QUESTION2));
    }

    @Test
    void validateAnswer() {
        QuestionAnswerService questionAnswerService = Mockito.mock(QuestionAnswerService.class);
        AnswerServiceImpl answerService = new AnswerServiceImpl(questionAnswerService);
        Assertions.assertFalse(answerService.validateAnswer("One", QUESTION1));
    }
}