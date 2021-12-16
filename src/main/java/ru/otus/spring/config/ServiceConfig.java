package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.clientData.ClientDataService;
import ru.otus.spring.service.clientData.ConsoleServiceImpl;
import ru.otus.spring.service.string.StringService;

@Configuration
public class ServiceConfig {

    @Bean
    public ClientDataService consoleServiceImpl(StringService stringServiceImpl) {
        return new ConsoleServiceImpl(System.out, System.in, stringServiceImpl);
    }
}