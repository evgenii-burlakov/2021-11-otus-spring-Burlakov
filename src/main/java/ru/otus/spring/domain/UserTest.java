package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTest {
    private final User user;
    private final int requiredCorrectAnswers;
    private int userCorrectAnswers;

    public void applyAnswer(Answer answer) {
        if (answer.isTrueAnswer()) {
            userCorrectAnswers++;
        }
    }
}
