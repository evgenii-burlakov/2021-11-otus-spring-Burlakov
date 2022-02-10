package ru.otus.libraryapplication.service.comment;

import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllByBookId(Long bookId);

    Comment getById(Long id);

    void deleteById(long id);

    void update(long id, String comment, long bookId);

    CommentDto create(CommentDto commentDto);
}
