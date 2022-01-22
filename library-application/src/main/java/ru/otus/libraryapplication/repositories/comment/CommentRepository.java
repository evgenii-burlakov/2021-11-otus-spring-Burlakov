package ru.otus.libraryapplication.repositories.comment;

import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getAllByBook(Book book);

    void deleteById(long id);

    Comment create(Comment comment);

    void update(Comment comment);

}
