package ru.otus.libraryapplication.handler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.service.string.StringService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
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
                .flatMap(id -> {
                    authorRepository.deleteWithBooksByAuthorId(id);
                    return ok().build();
                });
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return Mono.just(request.pathVariable("name"))
                .map(stringService::beautifyStringName)
                .flatMap(name -> {
                    if (stringService.verifyNotBlank(name)) {
                        return authorRepository.findById(id)
                                .flatMap(author -> {
                                    author.setName(name);
                                    authorRepository.save(author);
                                    return ok().build();
                                })
                                .switchIfEmpty(notFound().build());
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
//                        Author author = new Author(null, name);
                        return authorRepository.save(author.toBean())
                                .map(AuthorDto::toDto)
                                .flatMap(authorDto -> ok().body(fromValue(authorDto)));
                    } else {
                        return badRequest().build();
                    }
                });
    }
}
