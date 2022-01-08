package ru.otus.spring.controller.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.question.QuestionService;
import ru.otus.spring.service.test.TestingService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final TestingService testingService;
    private final QuestionService questionService;

    @ShellMethod(value = "Testing command", key = {"t", "test"})
    public void test() {
        testingService.printTest();
    }

    @ShellMethod(value = "All questions command", key = {"q", "questions"})
    public List<String> questions() {
        return questionService.getAllQuestionsTexts();
    }
}
