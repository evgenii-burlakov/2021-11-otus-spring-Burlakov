package ru.otus.libraryapplication.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.handler.GenreHandler;
import ru.otus.libraryapplication.repositories.genre.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class GenreRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> genreRouter(GenreRepository genreRepository, GenreHandler genreHandler) {
        return route()
                .GET("/api/genres", accept(APPLICATION_JSON),
                        request -> genreRepository.findAll()
                                .map(GenreDto::toDto)
                                .collectList()
                                .flatMap(genres -> ok().contentType(APPLICATION_JSON).bodyValue(genres))
                )
                .DELETE("/api/genres/{id}", accept(APPLICATION_JSON), genreHandler::delete)
                .GET("/api/genres/{id}", accept(APPLICATION_JSON),
                        request -> genreRepository.findById(request.pathVariable("id"))
                                .map(GenreDto::toDto)
                                .flatMap(genre -> ok().contentType(APPLICATION_JSON).body(fromValue(genre)))
                )
                .PATCH("/api/genres/{id}", contentType(APPLICATION_JSON), genreHandler::update)
                .POST("/api/genres", contentType(APPLICATION_JSON), genreHandler::create)
                .build();
    }
}
