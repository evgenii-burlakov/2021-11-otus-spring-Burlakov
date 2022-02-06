package ru.otus.libraryapplication.repositories.book;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.domain.Genre;

import java.util.Optional;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Data
    private class ArraySizeProjection {
        private int size;
    }

    @Override
    public boolean existByBookAuthorAndGenre(String bookName, Author author, Genre genre) {
        Query query = new Query();

        return mongoTemplate.exists(query.addCriteria(Criteria.where("name").is(bookName)
                .and("author.id").is(Optional.ofNullable(author).map(Author::getId).orElse(null))
                .and("genre.id").is(Optional.ofNullable(genre).map(Genre::getId).orElse(null))),
                Book.class);
    }

    @Override
    public void deleteWithCommentsByBookId(String id) {
        Query query = Query.query(Criteria.where("book.id").is(id));
        mongoTemplate.remove(query, Comment.class);

        mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Book.class);
    }
}
