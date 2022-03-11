package ru.otus.libraryapplication.repositories.genre;

import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<DeleteResult> deleteWithBooksByGenreId(String id) {
        Query query = Query.query(Criteria.where("genre.id").is(id));
        mongoTemplate.remove(query, Book.class);

        return mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Genre.class);
    }
}
