package ru.otus.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.integration.domain.Bar;
import ru.otus.integration.domain.Student;
import ru.otus.integration.service.BarService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@IntegrationComponentScan
@SpringBootApplication
public class Main {
    @Bean
    public QueueChannel morningChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel barChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public GenericSelector<Bar> freeBars() {
        return new GenericSelector<Bar>() {

            @Override
            public boolean accept(Bar bar) {
                if (!bar.isFree()) {
                    System.out.println(bar.getName() + " successfully completed the working day");
                }

                return bar.isFree();
            }
        };
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow dayFlow() {
        return IntegrationFlows.from( "morningChannel" )
                .handle( "dayService", "dinner" )
                .handle( "dayService", "tryingFindFreeBar" )
                .split()
                .handle( "barService", "isBarFree" )
                .aggregate()

                .filter(freeBars())
                .get();
    }

//    @Bean
//    public IntegrationFlow aggregatorFlow(GyroAggregator agg) {
//        return IntegrationFlows.from("filterOutputChannel")
//                .aggregate(a -> a
//                        .processor(agg)
//                        .groupTimeout(500L))
//                .channel("aggregatorOutputChannel")
//                .get();
//    }

    public static void main( String[] args ) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext( Main.class );

        Day day = ctx.getBean( Day.class );

        ForkJoinPool pool = ForkJoinPool.commonPool();

        pool.execute( () -> {
            Student student = new Student("Ivan", List.of(new Student("Yan"), new Student("Igor"), new Student("Evgenii")));
            System.out.println( student.getName() + " wake up");
            List<Bar> process = day.process(student);
            System.out.println(process.toArray().toString() + " go sleep");
        } );
    }


}
