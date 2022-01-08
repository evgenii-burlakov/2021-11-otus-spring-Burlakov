package ru.otus.spring.domain;

import lombok.Data;

@Data
public class QuestionAnswers {
    private final int sequenceNumber;
    private final String questionAnswer;
    private final boolean rightAnswer;
}
