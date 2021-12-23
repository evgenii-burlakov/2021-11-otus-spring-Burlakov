package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.service.test.TestingService;

import java.util.Locale;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("en_EN"));
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        TestingService service = context.getBean(TestingService.class);
        service.printTest();
    }
}
