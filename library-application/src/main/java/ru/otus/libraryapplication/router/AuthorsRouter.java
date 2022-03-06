package ru.otus.libraryapplication.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.handler.AuthorHandler;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class AuthorsRouter {
    private AuthorHandler authorHandler;

    @Bean
    public RouterFunction<ServerResponse> authorRouter(AuthorRepository authorRepository) {
        return route()
                .GET("/api/authors", accept(APPLICATION_JSON),
                        request -> authorRepository.findAll()
                                .map(AuthorDto::toDto)
                                .collectList()
                                .flatMap(authors -> ok().body(authors, AuthorDto.class))
                )
                .DELETE("/api/authors/{id}", accept(APPLICATION_JSON),
                        request -> authorRepository.deleteWithBooksByAuthorId(request.pathVariable("id"))
                                .flatMap(() -> ok())
                )
                .GET("/api/authors/{id}", accept(APPLICATION_JSON),
                        request -> authorRepository.findById(request.pathVariable("id"))
                                .map(AuthorDto::toDto)
                                .flatMap(author -> ok().contentType(APPLICATION_JSON).body(fromValue(author)))
                )
                .PATCH("/api/authors/{id}", accept(APPLICATION_JSON),
                        request -> authorHandler.update(request.pathVariable("id"), request.pathVariable("name")))
                .build();
    }
}
