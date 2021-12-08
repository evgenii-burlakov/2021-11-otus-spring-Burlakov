package ru.otus.spring.service.questionAnswer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ru.otus.spring.QuestionTestData.QUESTION1;
import static ru.otus.spring.QuestionTestData.QUESTION_ANSWER2;

class QuestionAnswerServiceImplTest {

    @Test
    void getRightAnswer() {
        QuestionAnswerService service = new QuestionAnswerServiceImpl();
        Assertions.assertEquals(QUESTION_ANSWER2, service.getRightAnswer(QUESTION1));
    }
}