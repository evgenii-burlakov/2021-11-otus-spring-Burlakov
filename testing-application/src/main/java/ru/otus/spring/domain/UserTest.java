package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTest {
    private final User user;
    private int userCorrectAnswers;
    private final int requiredCorrectAnswers;

    public void applyAnswer(Answer answer) {
        if (answer.isTrueAnswer()) {
            userCorrectAnswers++;
        }
    }
}
