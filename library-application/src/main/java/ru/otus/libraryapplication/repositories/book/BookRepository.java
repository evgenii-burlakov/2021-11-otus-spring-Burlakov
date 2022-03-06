package ru.otus.libraryapplication.repositories.book;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Book;

public interface BookRepository  extends ReactiveMongoRepository<Book, Long>, BookRepositoryCustom {
    Flux<Book> findAll();

    Mono<Book> findById(String id);

    Mono<Book> save(Book book);
}
