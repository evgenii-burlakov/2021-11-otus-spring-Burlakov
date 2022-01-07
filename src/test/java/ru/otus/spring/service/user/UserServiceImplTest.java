package ru.otus.spring.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static ru.otus.spring.QuestionTestData.USER;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService service;


    @Test
    void addUser() {
        Assertions.assertEquals(USER, service.addUser("TEST", "TESTOVICH", "TESTOV"));
    }
}