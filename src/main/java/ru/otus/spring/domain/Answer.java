package ru.otus.spring.domain;

import java.util.Objects;

public class Answer {
    private User user;
    private Question question;
    private boolean result;

    public Answer(User user, Question question, boolean result) {
        this.user = user;
        this.question = question;
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return result == answer.result && Objects.equals(user, answer.user) && Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, question, result);
    }
}
