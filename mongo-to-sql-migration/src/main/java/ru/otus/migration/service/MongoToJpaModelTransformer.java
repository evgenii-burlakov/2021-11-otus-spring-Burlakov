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
import ru.otus.migration.repositories.AuthorRepositoryJpa;
import ru.otus.migration.repositories.BookRepositoryJpa;
import ru.otus.migration.repositories.GenreRepositoryJpa;

@Component
@RequiredArgsConstructor
public class MongoToJpaModelTransformer {

    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    public AuthorJpa transformAuthorToJpaModel(AuthorMongo authorMongo) {
        return new AuthorJpa(null, authorMongo.getName());
    }

    public GenreJpa transformGenreToJpaModel(GenreMongo genreMongo) {
        return new GenreJpa(null, genreMongo.getName());
    }

    public BookJpa transformBookToJpaModel(BookMongo bookMongo) {
        AuthorJpa author = authorRepositoryJpa.getByName(bookMongo.getAuthorMongo().getName());
        GenreJpa genre = genreRepositoryJpa.getByName(bookMongo.getGenreMongo().getName());
        return new BookJpa(null, bookMongo.getName(), author, genre);
    }

    public CommentJpa transformCommentToJpaModel(CommentMongo commentMongo) {
        BookJpa book = bookRepositoryJpa.getByBookAuthorAndGenreNames(commentMongo.getBookMongo().getName(),
                commentMongo.getBookMongo().getAuthorMongo().getName(),
                commentMongo.getBookMongo().getGenreMongo().getName());

        return new CommentJpa(null, commentMongo.getComment(), book);
    }
}
