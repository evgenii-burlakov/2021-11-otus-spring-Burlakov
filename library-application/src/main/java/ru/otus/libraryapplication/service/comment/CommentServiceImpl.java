package ru.otus.libraryapplication.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.repositories.comment.CommentRepository;
import ru.otus.libraryapplication.service.book.BookService;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final StringService stringService;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllByBookId(Long bookId) {
        BookDto book = bookService.getById(bookId);
        if (book != null) {
            return commentRepository.getAllByBook(book.toBean()).stream()
                    .map(CommentDto::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new ApplicationException("Invalid book id");
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(long id, String stringComment, long bookId) {
        if (stringService.verifyNotBlank(stringComment)) {
            BookDto book = bookService.getById(bookId);
            if (book != null) {
                commentRepository.findById(id).ifPresentOrElse(comment -> {
                    comment.setComment(stringComment);
                    comment.setBook(book.toBean());
                    commentRepository.save(comment);
                }, () -> {
                    throw new ApplicationException("Invalid comment id");
                });
            } else {
                throw new ApplicationException("Invalid book id");
            }
        } else {
            throw new ApplicationException("Invalid comment");
        }
    }

    @Override
    @Transactional
    public CommentDto create(CommentDto commentDto) {
        if (stringService.verifyNotBlank(commentDto.getComment())) {
            BookDto book = bookService.getById(commentDto.getBook().getId());
            if (book != null) {
                Comment newComment = new Comment(null, commentDto.getComment(), book.toBean());
                return CommentDto.toDto(commentRepository.save(newComment));
            } else {
                throw new ApplicationException("Invalid book id");
            }
        } else {
            throw new ApplicationException("Invalid comment");
        }
    }
}
