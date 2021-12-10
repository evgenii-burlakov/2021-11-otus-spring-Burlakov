package ru.otus.spring.service.userTestService;

import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserTest;

public interface UserTestService {
    UserTest addUserTest(User user);

    boolean testResult(UserTest userTest);
}