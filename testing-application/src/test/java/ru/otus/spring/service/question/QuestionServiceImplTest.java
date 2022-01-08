package ru.otus.spring.service.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.question.QuestionDao;

import static ru.otus.spring.QuestionTestData.QUESTION_LIST;

@SpringBootTest
class QuestionServiceImplTest {
    @Autowired
    private QuestionService service;

    @MockBean
    private QuestionDao questionDaoInFile;

    @Test
    void getAll() {
        Mockito.when(questionDaoInFile.getAll()).thenReturn(QUESTION_LIST);
        Assertions.assertIterableEquals(QUESTION_LIST, service.getAll());
    }
}