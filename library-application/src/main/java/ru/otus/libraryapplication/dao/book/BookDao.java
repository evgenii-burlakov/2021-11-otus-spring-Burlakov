package ru.otus.libraryapplication.dao.book;

import ru.otus.libraryapplication.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();

    Book getById(long id);

    void deleteById(long id);

    void updateBookName(long id, String name);
}
