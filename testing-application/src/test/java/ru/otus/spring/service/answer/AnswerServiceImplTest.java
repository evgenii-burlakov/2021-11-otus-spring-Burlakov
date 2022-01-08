package ru.otus.spring.service.answer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.service.questionAnswer.QuestionAnswerService;

import static ru.otus.spring.QuestionTestData.*;

@SpringBootTest
class AnswerServiceImplTest {

    @Autowired
    private AnswerServiceImpl answerService;

    @MockBean
    private QuestionAnswerService questionAnswerService;


    @Test
    void addAnswer() {
        Answer expected = new Answer(USER, QUESTION2, true);
        Mockito.when(questionAnswerService.getRightAnswer(QUESTION2)).thenReturn(QUESTION_ANSWER4);
        Assertions.assertEquals(expected, answerService.addAnswer("London", USER, QUESTION2));
    }

    @Test
    void validateAnswer() {
        Assertions.assertFalse(answerService.validateAnswer("One", QUESTION1));
    }
}