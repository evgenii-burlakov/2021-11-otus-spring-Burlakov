package ru.otus.libraryapplication.controller.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.service.book.BookService;
import ru.otus.libraryapplication.service.comment.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping("/books")
    public String getAllAuthors(Model model) {
        List<BookDto> books = bookService.getAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/delete")
    public String deleteBookById(@RequestParam("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/books/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/books/edit")
    public String updateBook(BookDto book) {
        bookService.update(book);
        return "redirect:/books";
    }

    @GetMapping("/books/get")
    public String getPage(@RequestParam("id") Long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        List<CommentDto> comments = commentService.getAllByBookId(id);
        model.addAttribute("comments", comments);
        return "bookPage";
    }

    @GetMapping("/books/create")
    public String createPage() {
        return "createBook";
    }

    @PostMapping("/books/create")
    public String createBook(BookDto book) {
        bookService.create(book);
        return "redirect:/books";
    }
}
