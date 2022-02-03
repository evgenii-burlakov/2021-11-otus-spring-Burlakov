package ru.otus.libraryapplication.repositories.author;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteWithBooksByAuthorId(String id) {
        Query query = Query.query(Criteria.where("author.id").is(id));
        mongoTemplate.findAllAndRemove(query, Book.class);

        mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Author.class);
    }
}
