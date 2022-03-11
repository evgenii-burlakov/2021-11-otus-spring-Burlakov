package ru.otus.libraryapplication.repositories.book;

import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.domain.Genre;

import java.util.Optional;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Boolean> existByBookAuthorAndGenre(String bookName, Author author, Genre genre) {
        Query query = new Query();

        return mongoTemplate.exists(query.addCriteria(Criteria.where("name").is(bookName)
                        .and("author.id").is(Optional.ofNullable(author).map(Author::getId).orElse(null))
                        .and("genre.id").is(Optional.ofNullable(genre).map(Genre::getId).orElse(null))),
                Book.class);
    }

    @Override
    public Mono<DeleteResult> deleteWithCommentsByBookId(String id) {
        Query query = Query.query(Criteria.where("book.id").is(id));
        mongoTemplate.remove(query, Comment.class);

        return mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Book.class);
    }
}
