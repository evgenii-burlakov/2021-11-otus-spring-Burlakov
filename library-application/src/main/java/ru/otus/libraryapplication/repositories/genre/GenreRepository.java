package ru.otus.libraryapplication.repositories.genre;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String>, GenreRepositoryCustom {
    Flux<Genre> findAll();

    Mono<Genre> findById(String id);

    Mono<Genre> findByName(String name);

    Mono<Genre> save(Genre genre);
}
