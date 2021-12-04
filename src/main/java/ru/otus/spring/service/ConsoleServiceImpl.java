package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;
import ru.otus.spring.util.QuestionUtil;

import static ru.otus.spring.util.ConsoleUtil.*;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final QuestionDao dao;
    private final int requiredCorrectAnswers;

    public ConsoleServiceImpl(QuestionDao dao, @Value("${passedTest.rightAnswers}") int requiredCorrectAnswers) {
        this.dao = dao;
        this.requiredCorrectAnswers = requiredCorrectAnswers;
    }

    @Override
    public void printAllQuestions() {
        for (Question question : dao.getAll()) {
            print(QuestionUtil.toStringQuestion(question));
            print("-----------------------------------------------------------------");
        }
    }

    @Override
    public void printQuestion(int number) {
        print(QuestionUtil.toStringQuestion(dao.get(number)));
        print("-----------------------------------------------------------------");
    }

    @Override
    public void doTest() {
        int rightAnswersCount = 0;
        User user = addUser();
        for (Question question : dao.getAll()) {
            Answer answer = addAnswer(user, question);
            if (answer.isResult()) {
                rightAnswersCount++;
            }
        }
        printResult(rightAnswersCount);
        closeScanner();
    }

    private void printResult(int rightAnswersCount) {
        if (rightAnswersCount >= requiredCorrectAnswers) {
            print("Test passed. You are genius!");
        } else if (rightAnswersCount == 0) {
            print("Test failed. Get out. I don't want to see you again.");
        } else {
            print("Test failed. You can try again.");
        }
    }

    private User addUser() {
        print("Enter your first name:");
        String firstName = readNonEmptyString();
        print("Enter your second name:");
        String secondName = readNonEmptyString();
        print("Enter your surname:");
        String surname = readNonEmptyString();

        return new User(firstName, secondName, surname);
    }

    private Answer addAnswer(User user, Question question) {
        print(QuestionUtil.toStringQuestion(question));
        String answer;
        if (question.getAnswerOptions() != null) {
            answer = readAnswerFromOptions(question.getAnswerOptions().size());
        } else {
            answer = readNonEmptyString();
        }
        print("-----------------------------------------------------------------");
        boolean result = question.getRightAnswer().equals(answer);
        return new Answer(user, question.getNumber(), result);
    }
}
