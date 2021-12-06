package ru.otus.spring.domain;

import java.util.List;
import java.util.Objects;

public class Question {
    private int number;
    private QuestionType questionType;
    private String question;
    private List<QuestionAnswers> questionAnswers;

    public Question(Integer number, QuestionType questionType, String question, List<QuestionAnswers> questionAnswers) {
        this.number = number;
        this.questionType = questionType;
        this.question = question;
        this.questionAnswers = questionAnswers;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
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

    public List<QuestionAnswers> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setAnswerOptions(List<QuestionAnswers> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(number, question1.number) && Objects.equals(question, question1.question) && Objects.equals(questionAnswers, question1.questionAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, question, questionAnswers);
    }
}
