package ru.otus.spring.service.userTestService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.UserTest;

import static ru.otus.spring.QuestionTestData.USER;

@SpringBootTest
class UserTestServiceImplTest {
    @Autowired
    private UserTestService service;

    @Test
    void addUserTest() {
        int requiredCorrectAnswers = 4;
        UserTest expected = new UserTest(USER, 0, requiredCorrectAnswers);
        Assertions.assertEquals(expected, service.addUserTest(USER));
    }

    @Test
    void testResult() {
        UserTest userTest = service.addUserTest(USER);
        userTest.setUserCorrectAnswers(4);
        Assertions.assertTrue(service.testResult(userTest));
    }
}