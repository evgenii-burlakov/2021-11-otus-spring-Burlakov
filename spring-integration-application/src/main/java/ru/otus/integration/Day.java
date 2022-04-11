package ru.otus.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.Student;

import java.util.List;

@MessagingGateway
public interface Day {

    @Gateway(requestChannel = "morningChannel", replyChannel = "barChannel")
    List<Bar> process(Student student);
}
