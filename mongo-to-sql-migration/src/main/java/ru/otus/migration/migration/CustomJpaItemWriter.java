package ru.otus.migration.migration;

import org.springframework.batch.item.database.JpaItemWriter;
import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.GenreJpa;
import ru.otus.migration.service.MongoToJpaModelTransformer;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomJpaItemWriter<T> extends JpaItemWriter<T> {

    private MongoToJpaModelTransformer transformer;


    public void setTransformer(MongoToJpaModelTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    protected void doWrite(EntityManager entityManager, List<? extends T> items) {
        if (logger.isDebugEnabled()) {
            logger.debug("Writing to JPA with " + items.size() + " items.");
        }

        if (!items.isEmpty()) {
            long addedToContextCount = 0L;

            for (T item : items) {
                if (!entityManager.contains(item)) {

                    T entity = entityManager.merge(item);

                    if (entity instanceof AuthorJpa) {
                        transformer.addAuthorJpaListElement((AuthorJpa) entity);
                    } else if (entity instanceof GenreJpa) {
                        transformer.addGenreJpaListElement((GenreJpa) entity);
                    } else if (entity instanceof BookJpa) {
                        transformer.addBookJpaListElement((BookJpa) entity);
                    }

                    ++addedToContextCount;
                }
            }

            if (logger.isDebugEnabled()) {
                logger.debug(addedToContextCount + " entities merged.");
                logger.debug((long) items.size() - addedToContextCount + " entities found in persistence context.");
            }
        }
    }
}
