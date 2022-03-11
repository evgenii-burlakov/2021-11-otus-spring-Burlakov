package ru.otus.libraryapplication.repositories.author;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;

public interface AuthorRepositoryCustom {
    Mono<DeleteResult> deleteWithBooksByAuthorId(String id);
}
