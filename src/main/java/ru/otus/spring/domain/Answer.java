package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Answer {
    private final User user;
    private final Question question;
    private final boolean trueAnswer;
}
