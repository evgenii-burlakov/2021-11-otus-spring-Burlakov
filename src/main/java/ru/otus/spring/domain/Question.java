package ru.otus.spring.domain;

import java.util.List;
import java.util.Objects;

public class Question {
    private int number;
    private String question;
    private String rightAnswer;
    private List<String> answerOptions;

    public Question(Integer number, String question, String rightAnswer, List<String> answerOptions) {
        this.number = number;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answerOptions = answerOptions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(number, question1.number) && Objects.equals(question, question1.question) && Objects.equals(answerOptions, question1.answerOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, question, answerOptions);
    }
}
