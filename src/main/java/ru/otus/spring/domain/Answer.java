package ru.otus.spring.domain;

public class Answer {
    private User user;
    private int questionNumber;
    private boolean result;

    public Answer(User user, int questionNumber, boolean result) {
        this.user = user;
        this.questionNumber = questionNumber;
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
