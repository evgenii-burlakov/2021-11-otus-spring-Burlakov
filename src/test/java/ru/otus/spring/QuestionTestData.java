package ru.otus.spring;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswers;
import ru.otus.spring.domain.QuestionType;
import ru.otus.spring.domain.User;

import java.util.List;

public final class QuestionTestData {
    public static final QuestionAnswers QUESTION_ANSWER1 = new QuestionAnswers(1, "Nuts (chocolate)", false);
    public static final QuestionAnswers QUESTION_ANSWER2 = new QuestionAnswers(2, "Nuts", true);
    public static final QuestionAnswers QUESTION_ANSWER3 = new QuestionAnswers(3, "Meat", false);
    public static final QuestionAnswers QUESTION_ANSWER4 = new QuestionAnswers(1, "London", true);
    public static final QuestionAnswers QUESTION_ANSWER5 = new QuestionAnswers(1, "SpongeBob", true);
    public static final QuestionAnswers QUESTION_ANSWER6 = new QuestionAnswers(2, "Patrick", false);
    public static final QuestionAnswers QUESTION_ANSWER7 = new QuestionAnswers(1, "Earth", true);
    public static final QuestionAnswers QUESTION_ANSWER8 = new QuestionAnswers(1, "Java", true);

    public static final Question QUESTION1 = new Question(1, QuestionType.ANSWER_OPTIONS, "What does the squirrel eat?", List.of(QUESTION_ANSWER1, QUESTION_ANSWER2, QUESTION_ANSWER3));
    public static final Question QUESTION2 = new Question(2, QuestionType.FREE_ANSWER, "The capital of England?", List.of(QUESTION_ANSWER4));
    public static final Question QUESTION3 = new Question(3, QuestionType.ANSWER_OPTIONS, "Who lives in a pineapple under the sea?", List.of(QUESTION_ANSWER5, QUESTION_ANSWER6));
    public static final Question QUESTION4 = new Question(4, QuestionType.FREE_ANSWER, "The name of our planet?", List.of(QUESTION_ANSWER7));
    public static final Question QUESTION5 = new Question(5, QuestionType.FREE_ANSWER, "What programming language was this test written in?", List.of(QUESTION_ANSWER8));
    public static final List<Question> QUESTION_LIST = List.of(QUESTION1, QUESTION2, QUESTION3, QUESTION4, QUESTION5);
    public static final User USER = new User("TEST", "TESTOVICH", "TESTOV");
}
