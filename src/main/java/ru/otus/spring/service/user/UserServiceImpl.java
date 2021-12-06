package ru.otus.spring.service.user;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User addUser(String firstName, String secondName, String surname) {
        return new User(firstName, secondName, surname);
    }
}
