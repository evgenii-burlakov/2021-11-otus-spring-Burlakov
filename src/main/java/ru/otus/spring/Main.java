package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ConsoleService;
import ru.otus.spring.service.QuestionService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-context.xml");
        ConsoleService service = context.getBean(ConsoleService.class);
        service.printAllQuestions();
    }
}
