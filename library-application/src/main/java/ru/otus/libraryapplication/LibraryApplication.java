package ru.otus.libraryapplication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.libraryapplication.router.AuthorsRouter;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryApplication {
    private final AuthorsRouter authorsRouter;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> composedRouters() {
        return route()
                .path("/api/authors", authorsRouter)
                .path("/api/books", booksRouter)
                .path("/api/comments", commentsRouter)
                .path("/api/genres", genresRouter);
    }
}
