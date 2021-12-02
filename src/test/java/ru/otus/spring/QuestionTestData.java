package ru.otus.spring;

import ru.otus.spring.domain.Question;

import java.util.List;

public final class QuestionTestData {
    public static final Question QUESTION1 = new Question(1, "Are ya ready kids?","2",List.of("Aye, Aye captain!", "AYE, AYE CAPTAIN!"));
    public static final Question QUESTION2 = new Question(2, "Who lives in a pineapple under the sea?", "SpongeBob",null);
    public static final Question QUESTION3 = new Question(3, "Absorbent and yellow and porous is he", "SpongeBob",null);
    public static final Question QUESTION4 = new Question(4, "If nautical nonsense be somethin' ya wish","SpongeBob", null);
    public static final Question QUESTION5 = new Question(5, "Then drop on the deck and flop like a fish","SpongeBob", null);

    public static final List<Question> QUESTION_LIST = List.of(QUESTION1, QUESTION2, QUESTION3, QUESTION4, QUESTION5);
}
