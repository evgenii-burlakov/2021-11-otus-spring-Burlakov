package ru.otus.spring.domain;

import java.util.List;
import java.util.Objects;

public class Question {
    private Integer number;
    private String question;
    private List<String> answerOptions;

    public Question(Integer number, String question, List<String> answerOptions) {
        this.number = number;
        this.question = question;
        this.answerOptions = answerOptions;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Question №%d:\n%s\n", number, question));
        if (answerOptions != null && answerOptions.size() != 0) {
            sb.append("Choose from the proposed answer options (enter the number): \n");
            for (int i = 0; i < answerOptions.size(); i++) {
                sb.append(String.format("%d) %s \n", i + 1, answerOptions.get(i)));
            }
        } else {
            sb.append("Enter your answer: \n");
        }

        return sb.toString();
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
