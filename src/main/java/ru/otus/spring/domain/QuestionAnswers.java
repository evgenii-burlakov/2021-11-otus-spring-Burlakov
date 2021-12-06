package ru.otus.spring.domain;

public class QuestionAnswers {
    private int sequenceNumber;
    private String questionAnswer;
    private boolean rightAnswer;

    public QuestionAnswers(int sequenceNumber, String answerOption, boolean rightOption) {
        this.sequenceNumber = sequenceNumber;
        this.questionAnswer = answerOption;
        this.rightAnswer = rightOption;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public boolean isRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
