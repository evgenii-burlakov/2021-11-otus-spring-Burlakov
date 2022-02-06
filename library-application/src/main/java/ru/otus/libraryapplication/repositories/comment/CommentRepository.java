package ru.otus.libraryapplication.repositories.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {
    @Query(fields="{ 'id' : 1, 'comment' : 1}")
    List<Comment> getAllByBook(Book book);

    void deleteById(String id);

    Comment save(Comment comment);

    @Query(fields="{ 'id' : 1, 'comment' : 1}")
    Optional<Comment> findById(String id);
}
