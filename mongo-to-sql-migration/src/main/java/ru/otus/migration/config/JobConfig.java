package ru.otus.migration.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.CommentJpa;
import ru.otus.migration.jpaModel.GenreJpa;
import ru.otus.migration.migration.CustomJpaItemWriter;
import ru.otus.migration.mongoModel.AuthorMongo;
import ru.otus.migration.mongoModel.BookMongo;
import ru.otus.migration.mongoModel.CommentMongo;
import ru.otus.migration.mongoModel.GenreMongo;
import ru.otus.migration.service.MongoToJpaModelTransformer;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 10;

    public static final String MIGRATE_JOB_NAME = "migrateJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoToJpaModelTransformer mongoToJpaModelTransformer;

    @Autowired
    private MongoTemplate template;

    @Autowired
    EntityManagerFactory emf;

    @Bean
    public Job importUserJob(Step migrateAuthor, Step migrateGenre, Step migrateBook, Step migrateComment) {
        return jobBuilderFactory.get(MIGRATE_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(migrateAuthor)
                .next(migrateGenre)
                .next(migrateBook)
                .next(migrateComment)
                .end()
                .build();
    }

    //<editor-fold defaultstate="collapsed" desc="authorStep">
    @Bean
    public MongoItemReader<AuthorMongo> authorReader() {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("mongoItemAuthorReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<AuthorMongo, AuthorJpa> authorProcessor() {
        return mongoToJpaModelTransformer::transformAuthorToJpaModel;
    }

    @Bean
    public CustomJpaItemWriter<AuthorJpa> authorWriter() {
        CustomJpaItemWriter<AuthorJpa> customJpaItemWriter = new CustomJpaItemWriter<>();
        customJpaItemWriter.setTransformer(mongoToJpaModelTransformer);
        customJpaItemWriter.setEntityManagerFactory(emf);

        return customJpaItemWriter;
    }

    @Bean
    public Step migrateAuthor(ItemReader<AuthorMongo> authorReader, JpaItemWriter<AuthorJpa> authorWriter,
                              ItemProcessor<AuthorMongo, AuthorJpa> authorProcessor) {
        return stepBuilderFactory.get("migrateAuthorStep")
                .<AuthorMongo, AuthorJpa>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="genreStep">
    @Bean
    public MongoItemReader<GenreMongo> genreReader() {
        return new MongoItemReaderBuilder<GenreMongo>()
                .name("mongoItemAuthorReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<GenreMongo, GenreJpa> genreProcessor() {
        return mongoToJpaModelTransformer::transformGenreToJpaModel;
    }

    @Bean
    public CustomJpaItemWriter<GenreJpa> genreWriter() {
        CustomJpaItemWriter<GenreJpa> customJpaItemWriter = new CustomJpaItemWriter<>();
        customJpaItemWriter.setTransformer(mongoToJpaModelTransformer);
        customJpaItemWriter.setEntityManagerFactory(emf);

        return customJpaItemWriter;
    }

    @Bean
    public Step migrateGenre(ItemReader<GenreMongo> genreReader, JpaItemWriter<GenreJpa> genreWriter,
                             ItemProcessor<GenreMongo, GenreJpa> genreProcessor) {
        return stepBuilderFactory.get("migrateGenreStep")
                .<GenreMongo, GenreJpa>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="bookStep">
    @Bean
    public MongoItemReader<BookMongo> bookReader() {
        return new MongoItemReaderBuilder<BookMongo>()
                .name("mongoItemBookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<BookMongo, BookJpa> bookProcessor() {
        return mongoToJpaModelTransformer::transformBookToJpaModel;
    }

    @Bean
    public CustomJpaItemWriter<BookJpa> bookWriter() {
        CustomJpaItemWriter<BookJpa> customJpaItemWriter = new CustomJpaItemWriter<>();
        customJpaItemWriter.setTransformer(mongoToJpaModelTransformer);
        customJpaItemWriter.setEntityManagerFactory(emf);

        return customJpaItemWriter;
    }

    @Bean
    public Step migrateBook(ItemReader<BookMongo> bookReader, JpaItemWriter<BookJpa> bookWriter,
                            ItemProcessor<BookMongo, BookJpa> bookProcessor) {
        return stepBuilderFactory.get("migrateBookStep")
                .<BookMongo, BookJpa>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="commentStep">
    @Bean
    public MongoItemReader<CommentMongo> commentReader() {
        return new MongoItemReaderBuilder<CommentMongo>()
                .name("mongoItemCommentReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(CommentMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<CommentMongo, CommentJpa> commentProcessor() {
        return mongoToJpaModelTransformer::transformCommentToJpaModel;
    }

    @Bean
    public JpaItemWriter<CommentJpa> commentWriter() {
        return new JpaItemWriterBuilder<CommentJpa>()
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    public Step migrateComment(ItemReader<CommentMongo> commentReader, JpaItemWriter<CommentJpa> commentWriter,
                               ItemProcessor<CommentMongo, CommentJpa> commentProcessor) {
        return stepBuilderFactory.get("migrateCommentStep")
                .<CommentMongo, CommentJpa>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .build();
    }
    //</editor-fold>
}
