package ru.otus.libraryapplication.controller.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.service.comment.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/create")
    public String createPage(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "createComment";
    }

    @PostMapping("/comments/create")
    public String createComment(CommentDto comment) {
        commentService.create(comment);
        return "redirect:/books";
    }
}
