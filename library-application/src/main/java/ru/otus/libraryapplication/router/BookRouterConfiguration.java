package ru.otus.libraryapplication.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.handler.BookHandler;
import ru.otus.libraryapplication.repositories.book.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class BookRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> bookRouter(BookRepository bookRepository, BookHandler bookHandler) {
        return route()
                .GET("/api/books", accept(APPLICATION_JSON),
                        request -> bookRepository.findAll()
                                .map(BookDto::toDto)
                                .collectList()
                                .flatMap(books -> ok().contentType(APPLICATION_JSON).bodyValue(books))
                )
                .DELETE("/api/books/{id}", accept(APPLICATION_JSON), bookHandler::delete)
                .GET("/api/books/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("id"))
                                .map(BookDto::toDto)
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                )
                .PATCH("/api/books/{id}", contentType(APPLICATION_JSON), bookHandler::update)
                .POST("/api/books", contentType(APPLICATION_JSON), bookHandler::create)
                .build();
    }
}
