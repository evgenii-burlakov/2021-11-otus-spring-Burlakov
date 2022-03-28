package ru.otus.migration.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.mongoModel.BookMongo;
import ru.otus.migration.service.MongoToJpaModelTransformer;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 10;

    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate template;

    @Autowired
    EntityManagerFactory emf;

    @Bean
    public MongoItemReader<BookMongo> reader() {
        return new MongoItemReaderBuilder<BookMongo>()
                .name("mongoItemBookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<BookMongo, BookJpa> processor() {
        return MongoToJpaModelTransformer::transformBookToJpaModel;
    }

    @Bean
    public JpaItemWriter<BookJpa> writer() {
        return new JpaItemWriterBuilder<BookJpa>()
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    public Job importUserJob(Step migrateBook) {
        return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(migrateBook)
                .end()
                .build();
    }

    @Bean
    public Step migrateBook(ItemReader<BookMongo> reader, JpaItemWriter<BookJpa> writer,
                            ItemProcessor<BookMongo, BookJpa> itemProcessor) {
        return stepBuilderFactory.get("step1")
                .<BookMongo, BookJpa>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}
