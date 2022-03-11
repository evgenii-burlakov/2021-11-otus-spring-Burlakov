package ru.otus.libraryapplication.handler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.service.string.StringService;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class AuthorHandler {
    private final AuthorRepository authorRepository;
    private final StringService stringService;

    public Mono<ServerResponse> delete(ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .filter(StringUtils::isNotEmpty)
                .flatMap(id -> authorRepository.deleteWithBooksByAuthorId(id)
                        .flatMap(result -> ok().build()));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(AuthorDto.class)
                .map(author -> {
                    String name = stringService.beautifyStringName(author.getName());
                    author.setName(name);
                    author.setId(id);
                    return author;
                })
                .flatMap(author -> {
                    if (stringService.verifyNotBlank(author.getName())) {

                        return authorRepository.save(author.toBean())
                                .map(AuthorDto::toDto)
                                .flatMap(authorDto -> ok().body(fromValue(authorDto)));
                    } else {
                        return badRequest().build();
                    }
                });
    }

    public Mono<ServerResponse> create(ServerRequest request) {

        return request.bodyToMono(AuthorDto.class)
                .map(author -> {
                    String name = stringService.beautifyStringName(author.getName());
                    author.setName(stringService.beautifyStringName(name));
                    return author;
                })
                .flatMap(author -> {
                    if (stringService.verifyNotBlank(author.getName())) {
                        return authorRepository.save(author.toBean())
                                .map(AuthorDto::toDto)
                                .flatMap(authorDto -> ok().body(fromValue(authorDto)));
                    } else {
                        return badRequest().build();
                    }
                });
    }
}
