package ru.otus.spring.domain;

import java.util.Objects;

public class UserTest {
    private User user;
    private int userCorrectAnswers;
    private int requiredCorrectAnswers;

    public UserTest(User user, int userCorrectAnswers, int requiredCorrectAnswers) {
        this.user = user;
        this.userCorrectAnswers = userCorrectAnswers;
        this.requiredCorrectAnswers = requiredCorrectAnswers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserCorrectAnswers() {
        return userCorrectAnswers;
    }

    public void setUserCorrectAnswers(int userCorrectAnswers) {
        this.userCorrectAnswers = userCorrectAnswers;
    }

    public int getRequiredCorrectAnswers() {
        return requiredCorrectAnswers;
    }

    public void setRequiredCorrectAnswers(int requiredCorrectAnswers) {
        this.requiredCorrectAnswers = requiredCorrectAnswers;
    }

    public void applyAnswer(Answer answer) {
        if (answer.isTrueAnswer()) {
            userCorrectAnswers++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTest userTest = (UserTest) o;
        return userCorrectAnswers == userTest.userCorrectAnswers && requiredCorrectAnswers == userTest.requiredCorrectAnswers && Objects.equals(user, userTest.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, userCorrectAnswers, requiredCorrectAnswers);
    }
}
