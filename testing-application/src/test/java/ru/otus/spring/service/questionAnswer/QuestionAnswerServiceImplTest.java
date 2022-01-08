package ru.otus.spring.service.questionAnswer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static ru.otus.spring.QuestionTestData.QUESTION1;
import static ru.otus.spring.QuestionTestData.QUESTION_ANSWER2;

@SpringBootTest
class QuestionAnswerServiceImplTest {
    @Autowired
    private QuestionAnswerService service;


    @Test
    void getRightAnswer() {
        Assertions.assertEquals(QUESTION_ANSWER2, service.getRightAnswer(QUESTION1));
    }
}