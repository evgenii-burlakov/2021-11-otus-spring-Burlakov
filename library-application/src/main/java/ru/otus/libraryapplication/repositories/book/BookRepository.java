package ru.otus.libraryapplication.repositories.book;

import ru.otus.libraryapplication.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAll();

    Book getById(long id);

    void deleteById(long id);

    Book create(Book book);

    void update(Book book);

    boolean existByBookAuthorAndGenreNames(String bookName, String author, String genre);
}
