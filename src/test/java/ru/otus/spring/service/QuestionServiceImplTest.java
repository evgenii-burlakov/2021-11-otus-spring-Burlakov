package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.QuestionTestData;
import ru.otus.spring.domain.Question;

import java.util.List;

class QuestionServiceImplTest {

    @Test
    void getAll() {
        QuestionService questionServiceMock = Mockito.mock(QuestionService.class);
        Mockito.when(questionServiceMock.getAll()).thenReturn(QuestionTestData.QUESTION_LIST);
        Assertions.assertIterableEquals(QuestionTestData.QUESTION_LIST, questionServiceMock.getAll());
    }
}