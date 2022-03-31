package ru.otus.migration.migration;

import org.springframework.batch.item.database.JpaItemWriter;
import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.GenreJpa;
import ru.otus.migration.service.MongoToJpaModelCache;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomJpaItemWriter<T> extends JpaItemWriter<T> {

    private MongoToJpaModelCache cache;

    public void setCache(MongoToJpaModelCache cache) {
        this.cache = cache;
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
                        cache.addAuthorJpaMapElement((AuthorJpa) entity);
                    } else if (entity instanceof GenreJpa) {
                        cache.addGenreJpaMapElement((GenreJpa) entity);
                    } else if (entity instanceof BookJpa) {
                        cache.addBookJpaMapElement((BookJpa) entity);
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
