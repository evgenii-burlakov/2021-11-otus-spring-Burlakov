package ru.otus.libraryapplication.service.book;

import ru.otus.libraryapplication.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(String id);

    void deleteById(String id);

    void update(String id, String name, String authorName, String genreName);

    Book create(String name, String authorName, String genreName);
}
