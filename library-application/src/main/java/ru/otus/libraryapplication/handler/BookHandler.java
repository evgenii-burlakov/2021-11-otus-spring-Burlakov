package ru.otus.libraryapplication.handler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.repositories.book.BookRepository;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class BookHandler {
    private final BookRepository bookRepository;
    private final StringService stringService;

    public Mono<ServerResponse> delete(ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .filter(StringUtils::isNotEmpty)
                .flatMap(id -> bookRepository.deleteWithCommentsByBookId(id)
                        .flatMap(result -> ok().build()));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(BookDto.class)
                .flatMap(book -> {
                    String bookName = stringService.beautifyStringName(book.getName());

                    if (!stringService.verifyNotBlank(bookName)) {
                        throw new ApplicationException("Invalid book name");
                    }

                    book.setId(id);
                    book.setName(bookName);

                    return bookRepository.save(book.toBean())
                            .map(BookDto::toDto)
                            .flatMap(bookDto -> ok().body(fromValue(bookDto)));
                });
    }

    public Mono<ServerResponse> create(ServerRequest request) {

        return request.bodyToMono(BookDto.class)
                .flatMap(book -> {
                    String bookName = stringService.beautifyStringName(book.getName());

                    if (!stringService.verifyNotBlank(bookName)) {
                        throw new ApplicationException("Invalid book name");
                    }

                    return bookRepository.existByBookAuthorAndGenre(bookName, book.getAuthor().toBean(), book.getGenre().toBean())
                            .flatMap(bool -> {
                                if (bool) {
                                    throw new ApplicationException("Book already exist");
                                } else {
                                    book.setName(bookName);

                                    return bookRepository.save(book.toBean())
                                            .map(BookDto::toDto)
                                            .flatMap(bookDto -> ok().body(fromValue(bookDto)));
                                }
                            });
                });
    }
}
