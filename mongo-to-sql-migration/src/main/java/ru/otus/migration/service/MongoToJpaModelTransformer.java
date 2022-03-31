package ru.otus.migration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.CommentJpa;
import ru.otus.migration.jpaModel.GenreJpa;
import ru.otus.migration.mongoModel.AuthorMongo;
import ru.otus.migration.mongoModel.BookMongo;
import ru.otus.migration.mongoModel.CommentMongo;
import ru.otus.migration.mongoModel.GenreMongo;

@Component
@RequiredArgsConstructor
public class MongoToJpaModelTransformer {

    private final MongoToJpaModelCache cache;

    public AuthorJpa transformAuthorToJpaModel(AuthorMongo authorMongo) {
        return new AuthorJpa(null, authorMongo.getName());
    }

    public GenreJpa transformGenreToJpaModel(GenreMongo genreMongo) {
        return new GenreJpa(null, genreMongo.getName());
    }

    public BookJpa transformBookToJpaModel(BookMongo bookMongo) {
        AuthorJpa author = cache.getAuthorJpa(bookMongo.getAuthorMongo());
        GenreJpa genre = cache.getGenreJpa(bookMongo.getGenreMongo());

        return new BookJpa(null, bookMongo.getName(), author, genre);
    }

    public CommentJpa transformCommentToJpaModel(CommentMongo commentMongo) {
        BookJpa book = cache.getBookJpa(commentMongo.getBookMongo());

        return new CommentJpa(null, commentMongo.getComment(), book);
    }
}
