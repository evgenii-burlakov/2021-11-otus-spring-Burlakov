package ru.otus.spring.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ru.otus.spring.QuestionTestData.USER;

class UserServiceImplTest {

    @Test
    void addUser() {
        UserService service = new UserServiceImpl();
        Assertions.assertEquals(USER, service.addUser("TEST", "TESTOVICH", "TESTOV"));
    }
}