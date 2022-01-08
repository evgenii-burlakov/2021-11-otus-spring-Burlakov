package ru.otus.spring.service.user;

import ru.otus.spring.domain.User;

public interface UserService {
    User addUser(String firstName, String secondName, String surname);
}
