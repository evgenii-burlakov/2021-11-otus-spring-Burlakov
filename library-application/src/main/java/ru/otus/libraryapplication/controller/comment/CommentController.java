package ru.otus.libraryapplication.controller.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.service.comment.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/api/comments/{id}")
    public CommentDto getCommentById(@PathVariable("id") Long id) {
        return CommentDto.toDto(commentService.getById(id));
    }

    @GetMapping("/api/comments")
    public List<CommentDto> getCommentByBookId(@RequestParam("bookId") Long bookId) {
        return commentService.getAllByBookId(bookId).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/comments")
    public ResponseEntity<Comment> createComment(CommentDto comment) {
        commentService.create(comment.getComment(), comment.getBook().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<Comment> updateComment(CommentDto comment) {
        commentService.update(comment.getId(), comment.getComment(), comment.getBook().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
