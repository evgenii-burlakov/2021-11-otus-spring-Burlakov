package ru.otus.migration.service;

import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.GenreJpa;
import ru.otus.migration.mongoModel.BookMongo;

public class MongoToJpaModelTransformer {

    public static BookJpa transformBookToJpaModel(BookMongo bookMongo) {
        AuthorJpa authorJpa = new AuthorJpa(null, bookMongo.getAuthorMongo().getName());
        GenreJpa genreJpa = new GenreJpa(null, bookMongo.getGenreMongo().getName());
        return new BookJpa(null, bookMongo.getName(), authorJpa, genreJpa);
    }
}
