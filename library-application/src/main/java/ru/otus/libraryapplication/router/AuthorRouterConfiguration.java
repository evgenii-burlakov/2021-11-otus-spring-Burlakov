package ru.otus.libraryapplication.router;

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
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AuthorRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> authorRouter(AuthorRepository authorRepository, AuthorHandler authorHandler) {
        return route()
                .GET("/api/authors", accept(APPLICATION_JSON),
                        request -> authorRepository.findAll()
                                .map(AuthorDto::toDto)
                                .collectList()
                                .flatMap(authors -> ok().contentType(APPLICATION_JSON).bodyValue(authors))
                )
                .DELETE("/api/authors/{id}", accept(APPLICATION_JSON), authorHandler::delete)
                .GET("/api/authors/{id}", accept(APPLICATION_JSON),
                        request -> authorRepository.findById(request.pathVariable("id"))
                                .map(AuthorDto::toDto)
                                .flatMap(author -> ok().contentType(APPLICATION_JSON).body(fromValue(author)))
                )
                .PATCH("/api/authors/{id}", contentType(APPLICATION_JSON), authorHandler::update)
                .POST("/api/authors", contentType(APPLICATION_JSON), authorHandler::create)
                .build();
    }
}
