package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.QuestionTestData;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoInFile;

class QuestionServiceImplTest {
    @Test
    void getAll() {
        QuestionDao questionDaoInFile = Mockito.mock(QuestionDaoInFile.class);
        Mockito.when(questionDaoInFile.getAll()).thenReturn(QuestionTestData.QUESTION_LIST);
        QuestionService service = new QuestionServiceImpl(questionDaoInFile);
        Assertions.assertIterableEquals(QuestionTestData.QUESTION_LIST, service.getAll());
    }
}