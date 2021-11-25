package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.QuestionTestData;
import ru.otus.spring.domain.Question;

import java.util.List;

class QuestionServiceImplTest {
    private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-context.xml");
    private final QuestionService service = context.getBean(QuestionService.class);

    @Test
    void getAll() {
        List<Question> providedAllQuestions = service.getAll();
        Assertions.assertIterableEquals(QuestionTestData.QUESTION_LIST, providedAllQuestions);
    }
}