package ru.otus.libraryapplication.service.book;

import ru.otus.libraryapplication.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getById(long id);

    void deleteById(long id);

    void update(BookDto bookDto);

    BookDto create(BookDto bookDto);
}
