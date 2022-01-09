package ru.otus.libraryapplication.service.book;

import ru.otus.libraryapplication.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(long id);

    void deleteById(long id);

    void updateBookName(long id, String name);
}
