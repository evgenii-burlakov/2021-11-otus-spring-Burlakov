package ru.otus.migration.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.CommentJpa;
import ru.otus.migration.jpaModel.GenreJpa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.migration.MigrationUnitTestData.*;
import static ru.otus.migration.config.JobConfig.MIGRATE_JOB_NAME;

@SpringBootTest
@SpringBatchTest
public class MigrateBookJobTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MIGRATE_JOB_NAME);

        jobLauncherTestUtils.launchJob();

        TypedQuery<AuthorJpa> getAllAuthorQuery = em.createQuery("select a " +
                        "from AuthorJpa a",
                AuthorJpa.class);
        List<AuthorJpa> actualAuthors = getAllAuthorQuery.getResultList();

        TypedQuery<GenreJpa> getAllGenreQuery = em.createQuery("select a " +
                        "from GenreJpa a",
                GenreJpa.class);
        List<GenreJpa> actualGenres = getAllGenreQuery.getResultList();

        TypedQuery<BookJpa> getAllBookQuery = em.createQuery("select a " +
                        "from BookJpa a",
                BookJpa.class);
        List<BookJpa> actualBooks = getAllBookQuery.getResultList();

        TypedQuery<CommentJpa> getAllCommentQuery = em.createQuery("select a " +
                        "from CommentJpa a",
                CommentJpa.class);
        List<CommentJpa> actualComment = getAllCommentQuery.getResultList();

        List<AuthorJpa> expectedAuthors = List.of(AUTHOR1, AUTHOR2);
        List<GenreJpa> expectedGenres = List.of(GENRE1, GENRE2);
        List<BookJpa> expectedBooks = List.of(BOOK1, BOOK2);
        List<CommentJpa> expectedComments = List.of(COMMENT1, COMMENT2);

        assertThat(actualAuthors).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(expectedAuthors);
        assertThat(actualGenres).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(expectedGenres);
        assertThat(actualBooks).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "author.id", "genre.id").isEqualTo(expectedBooks);
        assertThat(actualComment).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "book.id", "book.author.id", "book.genre.id")
                .isEqualTo(expectedComments);
    }
}
