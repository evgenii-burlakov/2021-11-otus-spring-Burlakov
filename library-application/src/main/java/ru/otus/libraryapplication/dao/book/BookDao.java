package ru.otus.libraryapplication.dao.book;

import ru.otus.libraryapplication.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();

    Book getById(long id);

    void deleteById(long id);

    long create(Book book);

    void update(Book book);

    boolean existByBookAuthorAndGenreNames(String bookName, String author, String genre);
}
