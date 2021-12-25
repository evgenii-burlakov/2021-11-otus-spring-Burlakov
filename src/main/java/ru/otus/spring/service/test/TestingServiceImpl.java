package ru.otus.spring.service.test;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserTest;
import ru.otus.spring.service.answer.AnswerService;
import ru.otus.spring.service.clientData.ClientDataService;
import ru.otus.spring.service.locale.LocaleService;
import ru.otus.spring.service.question.QuestionService;
import ru.otus.spring.service.string.StringService;
import ru.otus.spring.service.user.UserService;
import ru.otus.spring.service.userTestService.UserTestService;

import java.util.List;

@Service
@AllArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final UserService userService;
    private final AnswerService answerService;
    private final UserTestService userTestService;
    private final ClientDataService clientDataService;
    private final StringService stringService;
    private final LocaleService localeService;
    private final QuestionService questionService;

    @Override
    public void printTest() {
        List<Question> questions = questionService.getAll();
        User user = requestUser();
        UserTest userTest = userTestService.addUserTest(user);
        for (Question question : questions) {
            Answer answer = requestAnswer(user, question);
            userTest.applyAnswer(answer);
        }
        printResult(userTestService.testResult(userTest));
    }

    private void printResult(boolean result) {
        if (result) {
            clientDataService.printString(localeService.getMessage("strings.passedTest"));
        } else {
            clientDataService.printString(localeService.getMessage("strings.failedTest"));
        }
    }


    private User requestUser() {
        String errorMessage = localeService.getMessage("strings.notEmptyStringError");
        clientDataService.printString(localeService.getMessage("strings.getFirstName"));
        String firstName = clientDataService.getNotEmptyString(errorMessage);
        clientDataService.printString(localeService.getMessage("strings.getSecondName"));
        String secondName = clientDataService.getNotEmptyString(errorMessage);
        clientDataService.printString(localeService.getMessage("strings.getSurname"));
        String surname = clientDataService.getNotEmptyString(errorMessage);

        return userService.addUser(firstName, secondName, surname);
    }

    private Answer requestAnswer(User user, Question question) {
        clientDataService.printString(stringService.toStringQuestion(question));
        while (true) {
            String answer = clientDataService.getNotEmptyString(localeService.getMessage("strings.notEmptyStringError"));
            if (answerService.validateAnswer(answer, question)) {
                clientDataService.printString("-----------------------------------------------------------------");
                return answerService.addAnswer(answer, user, question);
            } else {
                clientDataService.printString(localeService.getMessage("strings.getCorrectAnswer"));
            }
        }
    }
}
