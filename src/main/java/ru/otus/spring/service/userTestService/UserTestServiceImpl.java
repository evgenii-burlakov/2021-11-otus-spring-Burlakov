package ru.otus.spring.service.userTestService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserTest;

@Service
public class UserTestServiceImpl implements UserTestService {
    private final int requiredCorrectAnswers;

    public UserTestServiceImpl(@Value("${passedTest.rightAnswers}") int requiredCorrectAnswers) {
        this.requiredCorrectAnswers = requiredCorrectAnswers;
    }

    @Override
    public UserTest addUserTest(User user) {
        return new UserTest(user, 0, requiredCorrectAnswers);
    }

    @Override
    public void registerAnswer(UserTest userTest, Answer answer) {
        if (answer.isResult()) {
            userTest.setUserCorrectAnswers(userTest.getUserCorrectAnswers() + 1);
        }
    }

    @Override
    public boolean testResult(UserTest userTest) {
        return userTest.getRequiredCorrectAnswers() <= userTest.getUserCorrectAnswers();
    }
}
