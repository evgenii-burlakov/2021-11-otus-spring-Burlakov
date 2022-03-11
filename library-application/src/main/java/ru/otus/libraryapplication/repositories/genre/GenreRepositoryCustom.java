package ru.otus.libraryapplication.repositories.genre;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;

public interface GenreRepositoryCustom {
    Mono<DeleteResult> deleteWithBooksByGenreId(String id);
}
