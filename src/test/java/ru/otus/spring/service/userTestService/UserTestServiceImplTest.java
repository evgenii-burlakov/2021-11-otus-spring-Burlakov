package ru.otus.spring.service.userTestService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.UserTest;

import static ru.otus.spring.QuestionTestData.ANSWER1;
import static ru.otus.spring.QuestionTestData.USER;

class UserTestServiceImplTest {

    @Test
    void addUserTest() {
        int requiredCorrectAnswers = 4;
        UserTestService service = new UserTestServiceImpl(requiredCorrectAnswers);
        UserTest expected = new UserTest(USER, 0, requiredCorrectAnswers);
        Assertions.assertEquals(expected, service.addUserTest(USER));
    }

    @Test
    void testResult() {
        int requiredCorrectAnswers = 4;
        UserTestService service = new UserTestServiceImpl(requiredCorrectAnswers);
        UserTest userTest = service.addUserTest(USER);
        userTest.setUserCorrectAnswers(4);
        Assertions.assertTrue(service.testResult(userTest));
    }
}