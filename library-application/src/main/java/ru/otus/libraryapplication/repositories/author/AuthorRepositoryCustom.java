package ru.otus.libraryapplication.repositories.author;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Author;

public interface AuthorRepositoryCustom {
    Mono<DeleteResult> deleteWithBooksByAuthorId(String id);

    Mono<Author> updateWithBooks(Author author);
}
