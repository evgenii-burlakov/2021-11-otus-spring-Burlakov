package ru.otus.libraryapplication.repositories.comment;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, Long> {
    @Query(fields="{ 'id' : 1, 'comment' : 1}")
    Flux<Comment> getAllByBook(Book book);

    void deleteById(String id);

    Mono<Comment> save(Comment comment);

    @Query(fields="{ 'id' : 1, 'comment' : 1}")
    Mono<Comment> findById(String id);
}
