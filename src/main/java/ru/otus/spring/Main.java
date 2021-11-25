package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        for (Question question : service.getAll()) {
            System.out.println(question.toString());
            System.out.println("-----------------------------------------------------------------");
        }
    }
}
