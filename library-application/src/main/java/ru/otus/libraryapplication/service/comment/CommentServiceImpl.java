package ru.otus.libraryapplication.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.repositories.comment.CommentRepository;
import ru.otus.libraryapplication.service.book.BookService;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final StringService stringService;

    @Override
    public List<Comment> getAllByBookId(Long bookId) {
        return commentRepository.getAllByBookId(bookId);
    }

    @Override
    public Comment getById(long id) {
        return commentRepository.getById(id);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.getById(id);
    }

    @Override
    public void update(long id, String comment, long bookId) {
        if (stringService.verifyNotBlank(comment)) {
            Book book = bookService.getById(bookId);
            if (book != null) {
                Comment newComment = new Comment(id, comment, book);
                commentRepository.update(newComment);
            } else {
                throw new ApplicationException("Invalid book id");
            }
        } else {
            throw new ApplicationException("Invalid string comment");
        }
    }

    @Override
    public Comment create(String comment, Long bookId) {
        if (stringService.verifyNotBlank(comment)) {
            Book book = bookService.getById(bookId);
            if (book != null) {
                Comment newComment = new Comment(null, comment, book);
                return commentRepository.create(newComment);
            } else {
                throw new ApplicationException("Invalid book id");
            }
        } else {
            throw new ApplicationException("Invalid string comment");
        }
    }
}
