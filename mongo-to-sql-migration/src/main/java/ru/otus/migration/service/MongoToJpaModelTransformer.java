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

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MongoToJpaModelTransformer {

    private final Set<AuthorJpa> authorJpaSet = new HashSet<>();
    private final Set<GenreJpa> genreJpaSet = new HashSet<>();
    private final Set<BookJpa> bookJpaSet = new HashSet<>();

    public void addAuthorJpaListElement(AuthorJpa authorJpa) {
        authorJpaSet.add(authorJpa);
    }

    public void addGenreJpaListElement(GenreJpa genreJpa) {
        genreJpaSet.add(genreJpa);
    }

    public void addBookJpaListElement(BookJpa bookJpa) {
        bookJpaSet.add(bookJpa);
    }

    public AuthorJpa transformAuthorToJpaModel(AuthorMongo authorMongo) {
        return new AuthorJpa(null, authorMongo.getName());
    }

    public GenreJpa transformGenreToJpaModel(GenreMongo genreMongo) {
        return new GenreJpa(null, genreMongo.getName());
    }

    public BookJpa transformBookToJpaModel(BookMongo bookMongo) {
        AuthorJpa author = authorJpaSet.stream()
                .filter(a -> a.getName().equals(bookMongo.getAuthorMongo().getName()))
                .findFirst().orElse(null);

        GenreJpa genre = genreJpaSet.stream()
                .filter(g -> g.getName().equals(bookMongo.getGenreMongo().getName()))
                .findFirst().orElse(null);

        return new BookJpa(null, bookMongo.getName(), author, genre);
    }

    public CommentJpa transformCommentToJpaModel(CommentMongo commentMongo) {
        BookJpa book = bookJpaSet.stream()
                .filter(b -> b.getName().equals(commentMongo.getBookMongo().getName())
                        && b.getAuthor().getName().equals(commentMongo.getBookMongo().getAuthorMongo().getName())
                        && b.getGenre().getName().equals(commentMongo.getBookMongo().getGenreMongo().getName()))
                .findFirst().orElse(null);

        return new CommentJpa(null, commentMongo.getComment(), book);
    }
}
