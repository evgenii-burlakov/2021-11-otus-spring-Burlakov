package ru.otus.spring.service.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoInFile;

import static ru.otus.spring.QuestionTestData.*;

class QuestionServiceImplTest {
    @Test
    void getAll() {
        QuestionDao questionDaoInFile = Mockito.mock(QuestionDaoInFile.class);
        Mockito.when(questionDaoInFile.getAll()).thenReturn(QUESTION_LIST);
        QuestionService service = new QuestionServiceImpl(questionDaoInFile);
        Assertions.assertIterableEquals(QUESTION_LIST, service.getAll());
    }

    @Test
    void getRightAnswer() {
        QuestionDao questionDaoInFile = Mockito.mock(QuestionDaoInFile.class);
        Mockito.when(questionDaoInFile.getAll()).thenReturn(QUESTION_LIST);
        QuestionService service = new QuestionServiceImpl(questionDaoInFile);
        Assertions.assertEquals(QUESTION_ANSWER2, service.getRightAnswer(QUESTION1));
    }
}