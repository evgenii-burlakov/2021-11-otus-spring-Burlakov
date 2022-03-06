package ru.otus.libraryapplication.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.service.string.StringService;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class AuthorHandler {
    private final AuthorRepository authorRepository;
    private final StringService stringService;

    public Mono<ServerResponse> update(long id, String name) {
        Mono.just(name)
                .map(stringService::beautifyStringName)
                .flatMap(n -> {
                    if (stringService.verifyNotBlank(n)) {
                        return authorRepository.findById(id)
                                .flatMap(author -> {
                                    author.setName(n);
                                    authorRepository.save(author);
                                    return ok().build();
                                })
                                .switchIfEmpty(notFound().build());
                    } else {
                        return badRequest().build();
                    }
                });

        return badRequest().build();
    }
}
