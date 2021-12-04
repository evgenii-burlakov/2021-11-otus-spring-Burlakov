package ru.otus.spring.domain;

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
}
