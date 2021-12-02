package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.QuestionDaoInFile;

@Configuration
public class DaoConfig {

    @Bean
    public QuestionDao questionDao(){
        return new QuestionDaoInFile("/questions.csv");
    }
}
