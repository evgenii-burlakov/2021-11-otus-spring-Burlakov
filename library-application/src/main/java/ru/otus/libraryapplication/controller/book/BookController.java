package ru.otus.libraryapplication.controller.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.service.book.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/books")
    public ResponseEntity deleteBookById(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity updateBook(BookDto book) {
        bookService.update(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) {
        return BookDto.toDto(bookService.getById(id));
    }

    @PostMapping("/books")
    public ResponseEntity createBook(BookDto book) {
        bookService.create(book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
