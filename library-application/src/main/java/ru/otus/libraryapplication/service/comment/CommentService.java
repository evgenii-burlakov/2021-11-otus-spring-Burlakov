package ru.otus.libraryapplication.service.comment;

import ru.otus.libraryapplication.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllByBookId(String bookId);

    void deleteById(String id);

    void update(String id, String comment, String bookId);

    Comment create(String comment, String bookId);
}
