package ru.otus.spring.service.userTestService;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserTest;

public interface UserTestService {
    UserTest addUserTest(User user);

    void registerAnswer(UserTest userTest, Answer answer);

    boolean testResult(UserTest userTest);
}