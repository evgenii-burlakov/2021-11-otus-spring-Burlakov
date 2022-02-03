package ru.otus.libraryapplication.repositories.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteWithBooksByGenreId(String id) {
        Query query = Query.query(Criteria.where("genre.id").is(id));
        mongoTemplate.findAllAndRemove(query, Book.class);

        mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Genre.class);
    }
}
