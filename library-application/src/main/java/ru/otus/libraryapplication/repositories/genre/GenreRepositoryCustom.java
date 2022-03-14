package ru.otus.libraryapplication.repositories.genre;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Genre;

public interface GenreRepositoryCustom {
    Mono<DeleteResult> deleteWithBooksByGenreId(String id);

    Mono<Genre> updateWithBooks(Genre genre);
}
