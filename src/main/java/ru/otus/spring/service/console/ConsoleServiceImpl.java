package ru.otus.spring.service.console;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserTest;
import ru.otus.spring.service.answer.AnswerService;
import ru.otus.spring.service.question.QuestionService;
import ru.otus.spring.service.user.UserService;
import ru.otus.spring.service.userTestService.UserTestService;

import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;
    private final UserTestService userTestService;

    public ConsoleServiceImpl(QuestionService questionService, UserService userService, AnswerService answerService, UserTestService userTestService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
        this.userTestService = userTestService;
    }

    @Override
    public void printTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            User user = requestUser(scanner);
            UserTest userTest = userTestService.addUserTest(user);
            for (Question question : questionService.getAll()) {
                Answer answer = requestAnswer(user, question, scanner);
                userTestService.registerAnswer(userTest, answer);
            }
            printResult(userTestService.testResult(userTest));
        }
    }

    private void printResult(boolean result) {
        if (result) {
            System.out.println("Test passed. You are genius!");
        } else {
            System.out.println("Test failed. You can try again.");
        }
    }

    private User requestUser(Scanner scanner) {
        System.out.println("Enter your first name:");
        String firstName = readNonEmptyString(scanner);
        System.out.println("Enter your second name:");
        String secondName = readNonEmptyString(scanner);
        System.out.println("Enter your surname:");
        String surname = readNonEmptyString(scanner);

        return userService.addUser(firstName, secondName, surname);
    }

    private Answer requestAnswer(User user, Question question, Scanner scanner) {
        System.out.println(questionService.toStringQuestion(question));
        while (true) {
            String answer = readNonEmptyString(scanner);
            if (answerService.validateAnswer(answer, question)) {
                System.out.println("-----------------------------------------------------------------");
                return answerService.addAnswer(answer, user, question);
            } else {
                System.out.println("Please, write correct answer");
            }
        }
    }

    public static String readNonEmptyString(Scanner scanner) {
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isBlank()) {
                System.out.println("Empty response is not allowed");
            } else {
                return result;
            }
        }
    }
}
