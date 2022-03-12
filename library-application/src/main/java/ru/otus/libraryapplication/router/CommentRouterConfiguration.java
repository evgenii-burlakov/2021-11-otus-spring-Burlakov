package ru.otus.libraryapplication.router;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.handler.CommentHandler;
import ru.otus.libraryapplication.repositories.comment.CommentRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class CommentRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> commentRouter(CommentRepository commentRepository, CommentHandler commentHandler) {
        return route()
                .GET("/api/comments", queryParam("bookId", StringUtils::isNotEmpty),
                        request -> commentRepository.getAllByBookId(request.queryParam("bookId").orElse(null))
                                .map(CommentDto::toDto)
                                .collectList()
                                .flatMap(comment -> ok().contentType(APPLICATION_JSON).bodyValue(comment))
                )
                .DELETE("/api/comments/{id}", accept(APPLICATION_JSON), commentHandler::delete)
                .GET("/api/comments/{id}", accept(APPLICATION_JSON),
                        request -> commentRepository.findById(request.pathVariable("id"))
                                .map(CommentDto::toDto)
                                .flatMap(comment -> ok().contentType(APPLICATION_JSON).body(fromValue(comment)))
                )
                .PATCH("/api/comments/{id}", contentType(APPLICATION_JSON), commentHandler::update)
                .POST("/api/comments", contentType(APPLICATION_JSON), commentHandler::create)
                .build();
    }
}
