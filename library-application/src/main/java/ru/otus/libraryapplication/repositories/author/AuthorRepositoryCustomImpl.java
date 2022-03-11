package ru.otus.libraryapplication.repositories.author;

import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<DeleteResult> deleteWithBooksByAuthorId(String id) {
        Query query = Query.query(Criteria.where("author.id").is(id));
        mongoTemplate.remove(query, Book.class);

        return mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Author.class);
    }
}
