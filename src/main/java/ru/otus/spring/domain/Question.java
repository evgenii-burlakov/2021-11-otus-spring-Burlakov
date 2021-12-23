package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Question {
    private final String locale;
    private final int number;
    private final QuestionType questionType;
    private final String question;
    private List<QuestionAnswers> questionAnswers;
}
