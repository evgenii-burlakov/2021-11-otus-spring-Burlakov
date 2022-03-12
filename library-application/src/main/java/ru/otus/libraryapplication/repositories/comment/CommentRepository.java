package ru.otus.libraryapplication.repositories.comment;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> getAllByBookId(String bookId);

    Mono<Void> deleteById(String id);

    Mono<Comment> save(Comment comment);

    Mono<Comment> findById(String id);
}
