package ru.otus.migration.service;

import org.springframework.stereotype.Component;
import ru.otus.migration.dto.BookJpaMapKeyData;
import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.GenreJpa;
import ru.otus.migration.mongoModel.AuthorMongo;
import ru.otus.migration.mongoModel.BookMongo;
import ru.otus.migration.mongoModel.GenreMongo;

import java.util.HashMap;
import java.util.Map;

@Component
public class MongoToJpaModelCache {

    private final Map<String, AuthorJpa> authorJpaMap = new HashMap<>();
    private final Map<String, GenreJpa> genreJpaMap = new HashMap<>();
    private final Map<BookJpaMapKeyData, BookJpa> bookJpaMap = new HashMap<>();

    public void addAuthorJpaMapElement(AuthorJpa authorJpa) {
        authorJpaMap.put(authorJpa.getName(), authorJpa);
    }

    public void addGenreJpaMapElement(GenreJpa genreJpa) {
        genreJpaMap.put(genreJpa.getName(), genreJpa);
    }

    public void addBookJpaMapElement(BookJpa bookJpa) {
        BookJpaMapKeyData key = BookJpaMapKeyData.fromBookJpa(bookJpa);

        bookJpaMap.put(key, bookJpa);
    }

    public AuthorJpa getAuthorJpa(AuthorMongo authorMongo) {
        return authorJpaMap.get(authorMongo.getName());
    }

    public GenreJpa getGenreJpa(GenreMongo genreMongo) {
        return genreJpaMap.get(genreMongo.getName());
    }

    public BookJpa getBookJpa(BookMongo bookMongo) {
        return bookJpaMap.get(new BookJpaMapKeyData(bookMongo.getName(),
                bookMongo.getAuthorMongo().getName(),
                bookMongo.getGenreMongo().getName()));
    }
}
