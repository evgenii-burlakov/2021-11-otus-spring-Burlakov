package ru.otus.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.integration.service.HardWorkerHolidayService;

@IntegrationComponentScan
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        HardWorkerHolidayService hardWorkerHolidayService = context.getBean(HardWorkerHolidayService.class);
        hardWorkerHolidayService.startHoliday();
    }
}
