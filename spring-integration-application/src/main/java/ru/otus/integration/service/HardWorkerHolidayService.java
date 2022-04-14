package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.HardWorker;
import ru.otus.integration.integration.TypicalHoliday;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
public class HardWorkerHolidayService {
    private final TypicalHoliday typicalHoliday;

    public HardWorkerHolidayService(TypicalHoliday typicalHoliday) {
        this.typicalHoliday = typicalHoliday;
    }

    public List<Bar> tryingFindFreeBar(HardWorker hardWorker) throws Exception {
        System.out.printf("%s trying find free bar%n", hardWorker.getName());

        return List.of(new Bar("Mollys"), new Bar("SPB"), new Bar("KILLFISH"));
    }

    public void startHoliday() throws InterruptedException {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(6000);

            pool.execute(() -> {
                HardWorker hardWorker = new HardWorker("Ivan");
                System.out.printf("%s wake up%n", hardWorker.getName());
                typicalHoliday.process(hardWorker);
                System.out.printf("%s SATISFIED and FULL OF STRENGTH returns home!!!%n", hardWorker.getName());
            });
        }
    }
}
