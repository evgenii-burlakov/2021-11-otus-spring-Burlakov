package ru.otus.libraryapplication.handler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.repositories.genre.GenreRepository;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class GenreHandler {
    private final GenreRepository genreRepository;
    private final StringService stringService;

    public Mono<ServerResponse> delete(ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .filter(StringUtils::isNotEmpty)
                .flatMap(id -> genreRepository.deleteWithBooksByGenreId(id)
                        .flatMap(result -> ok().build()));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(Genre.class)
                .map(genre -> {
                    String name = stringService.beautifyStringName(genre.getName());
                    genre.setName(name);
                    genre.setId(id);
                    return genre;
                })
                .flatMap(genre -> {
                    if (stringService.verifyNotBlank(genre.getName())) {

                        return genreRepository.updateWithBooks(genre)
                                .map(GenreDto::toDto)
                                .flatMap(genreDto -> ok().body(fromValue(genreDto)));
                    } else {
                        throw new ApplicationException("Invalid genre name");
                    }
                });
    }

    public Mono<ServerResponse> create(ServerRequest request) {

        return request.bodyToMono(Genre.class)
                .map(genre -> {
                    String name = stringService.beautifyStringName(genre.getName());
                    genre.setName(stringService.beautifyStringName(name));
                    return genre;
                })
                .flatMap(genre -> {
                    if (stringService.verifyNotBlank(genre.getName())) {
                        return genreRepository.save(genre)
                                .map(GenreDto::toDto)
                                .flatMap(genreDto -> ok().body(fromValue(genreDto)));
                    } else {
                        throw new ApplicationException("Invalid genre name");
                    }
                });
    }
}
