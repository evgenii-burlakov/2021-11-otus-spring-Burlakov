package ru.otus.libraryapplication.controller.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.libraryapplication.service.book.BookService;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
}
