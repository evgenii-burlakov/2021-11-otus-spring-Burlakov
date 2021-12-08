package ru.otus.spring.service.test;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserTest;
import ru.otus.spring.service.answer.AnswerService;
import ru.otus.spring.service.clientData.ClientDataService;
import ru.otus.spring.service.question.QuestionService;
import ru.otus.spring.service.user.UserService;
import ru.otus.spring.service.userTestService.UserTestService;

@Service
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;
    private final UserTestService userTestService;
    private final ClientDataService clientDataService;

    public TestingServiceImpl(QuestionService questionService, UserService userService, AnswerService answerService, UserTestService userTestService, ClientDataService clientDataService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
        this.userTestService = userTestService;
        this.clientDataService = clientDataService;
    }

    @Override
    public void printTest() {
        User user = requestUser();
        UserTest userTest = userTestService.addUserTest(user);
        for (Question question : questionService.getAll()) {
            Answer answer = requestAnswer(user, question);
            userTestService.registerAnswer(userTest, answer);
        }
        getResult(userTestService.testResult(userTest));

        clientDataService.close();
    }

    private void getResult(boolean result) {
        if (result) {
            clientDataService.printString("Test passed. You are genius!");
        } else {
            clientDataService.printString("Test failed. You can try again.");
        }
    }

    private User requestUser() {
        clientDataService.printString("Enter your first name:");
        String firstName = clientDataService.getString();
        clientDataService.printString("Enter your second name:");
        String secondName = clientDataService.getString();
        clientDataService.printString("Enter your surname:");
        String surname = clientDataService.getString();

        return userService.addUser(firstName, secondName, surname);
    }

    private Answer requestAnswer(User user, Question question) {
        clientDataService.printString(clientDataService.toStringQuestion(question));
        while (true) {
            String answer = clientDataService.getString();
            if (answerService.validateAnswer(answer, question)) {
                clientDataService.printString("-----------------------------------------------------------------");
                return answerService.addAnswer(answer, user, question);
            } else {
                clientDataService.printString("Please, write correct answer");
            }
        }
    }
}
