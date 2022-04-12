package ru.otus.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.HardWorker;
import ru.otus.integration.integration.TypicalHoliday;

import java.util.concurrent.ForkJoinPool;

@IntegrationComponentScan
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);

        TypicalHoliday typicalHoliday = ctx.getBean(TypicalHoliday.class);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(3000);

            pool.execute(() -> {

                HardWorker hardWorker = new HardWorker("Ivan");
                System.out.printf("%s wake up%n", hardWorker.getName());
                Bar bar = typicalHoliday.process(hardWorker);
                if (bar == null) {
                    System.out.printf("Sad %s goes to sleep%n", hardWorker.getName());
                } else {
                    System.out.printf("%s SATISFIED and FULL OF STRENGTH returns home!!!%n", hardWorker.getName());
                }
            });
        }
    }
}
