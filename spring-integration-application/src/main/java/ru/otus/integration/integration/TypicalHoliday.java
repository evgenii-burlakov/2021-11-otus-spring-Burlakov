package ru.otus.integration.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.HardWorker;

@MessagingGateway
public interface TypicalHoliday {

    @Gateway(requestChannel = "morningChannel", replyChannel = "barChannel")
    Bar process(HardWorker hardWorker);
}
