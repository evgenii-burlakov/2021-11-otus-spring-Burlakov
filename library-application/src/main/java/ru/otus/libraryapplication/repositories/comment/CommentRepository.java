package ru.otus.libraryapplication.repositories.comment;

import ru.otus.libraryapplication.domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getAllByBookId(Long bookId);

    Comment getById(long id);

    void deleteById(long id);

    Comment create(Comment comment);

    void update(Comment comment);

}
