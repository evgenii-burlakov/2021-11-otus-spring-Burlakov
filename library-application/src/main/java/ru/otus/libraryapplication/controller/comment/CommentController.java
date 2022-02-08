package ru.otus.libraryapplication.controller.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.libraryapplication.service.comment.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
}
