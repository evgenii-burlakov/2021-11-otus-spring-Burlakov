package ru.otus.spring.domain;

import java.util.Objects;

public class Answer {
    private User user;
    private Question question;
    private boolean trueAnswer;

    public Answer(User user, Question question, boolean trueAnswer) {
        this.user = user;
        this.question = question;
        this.trueAnswer = trueAnswer;
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

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return trueAnswer == answer.trueAnswer && Objects.equals(user, answer.user) && Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, question, trueAnswer);
    }
}
